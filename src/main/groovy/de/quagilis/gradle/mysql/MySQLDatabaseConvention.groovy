/*
 * Copyright 2011 Andreas Simon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Andreas Simon
 * Quagilis - Quality in Agile
 * http://www.quagilis.de
 *
 */

package de.quagilis.gradle.mysql

import org.gradle.api.*
import de.quagilis.gradle.mysql.tasks.*


class MySQLDatabaseConvention {
    final Project project;
    final NamedDomainObjectContainer<MySQLDatabase> databases

    MySQLDatabaseConvention(Project project, NamedDomainObjectContainer<MySQLDatabase> databases) {
        this.project   = project
        this.databases = databases
    }

    def databases(Closure closure) {
        databases.configure(closure)

        databases.each { database ->
            def createDatabaseTask = newCreateDatabaseTask(database)
            def dropDatabaseTask   = newDropDatabaseTask(database)
            def initDatabaseTask   = newInitDatabaseTask(database)
            def migrateTask        = newMigrateDatabaseTask(database)
        }
    }

    private Task newCreateDatabaseTask(MySQLDatabase database) {
        return project.task(type: CreateMySQLDatabase, description: "Creates ${ database.name } database", "create${ database.name.capitalize() }Database") {
            url          = database.url
            user         = database.username
            password     = database.password;
            databaseName = database.schema
        }
    }

    private Task newDropDatabaseTask(MySQLDatabase database) {
        return project.task(type: DropMySQLDatabase, description: "Drops ${ database.name } database", "drop${ database.name.capitalize() }Database") {
            url          = database.url
            user         = database.username
            password     = database.password;
            databaseName = database.schema
        }
    }

    private Task newInitDatabaseTask(MySQLDatabase database) {
        project.task(group: "Flyway", description: "Inits the Flyway schema table for ${ database.name } database", "init${ database.name.capitalize() }Database") << {
            ant.taskdef(
                    name: 'flywayInit',
                    classname: 'com.googlecode.flyway.ant.InitTask',
                    classpath: project.buildscript.configurations.classpath.asPath)

            ant.flywayInit(
                    driver: 'com.mysql.jdbc.Driver',
                    url: database.url,
                    user: database.username,
                    schemas: database.schema)
        }
    }

    private Task newMigrateDatabaseTask(MySQLDatabase database) {
        Task migrateTask = project.task(group: "Flyway", description: "Migrates the schema of the ${ database.name } database", "migrate${ database.name.capitalize() }") {
        }
        migrateTask << {
            antClasspath = project.buildscript.configurations.classpath + files(project.sourceSets.db.output.resourcesDir)
            ant.taskdef(
                    name: 'flywayMigrate',
                    classname: 'com.googlecode.flyway.ant.MigrateTask',
                    classpath: antClasspath.asPath)

            ant.flywayMigrate(
                    driver: 'com.mysql.jdbc.Driver',
                    url: "jdbc:mysql://localhost/${ database.schema }",
//                    schemas: database.schema,
                    user: database.username,
                    password: database.password,
                    baseDir: 'migrations',
                    sqlMigrationPrefix: '')
        }
        return migrateTask;
    }

}
