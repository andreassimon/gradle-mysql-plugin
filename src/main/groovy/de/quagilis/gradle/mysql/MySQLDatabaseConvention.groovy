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
import de.quagilis.gradle.mysql.domain.*


class MySQLDatabaseConvention {
    final Project project;
    final NamedDomainObjectContainer<MySQLDatabase> databases
    File migrationsDir

    MySQLDatabaseConvention(Project project) {
        this.project   = project
        this.databases = project.container(MySQLDatabase)
    }

    def databases(Closure closure) {
        assert this.migrationsDir, "You must set the 'migrationsDir' property before configuring databases!";

        databases.configure(closure)

        def setupAllDatabasesSubtasks   = []
        def migrateAllDatabasesSubtasks = []
        def dropAllDependencies         = []

        databases.each { database ->
            def createDatabaseTask  = newCreateDatabaseTask(database)
            def dropDatabaseTask    = newDropDatabaseTask(database)
            def initDatabaseTask    = newInitDatabaseTask(database)
            def migrateDatabaseTask = newMigrateDatabaseTask(database)

            setupAllDatabasesSubtasks   << createDatabaseTask << initDatabaseTask << migrateDatabaseTask
            migrateAllDatabasesSubtasks << migrateDatabaseTask
            dropAllDependencies         << dropDatabaseTask

            project.task(type: Composite, group: "MySQL", description: "Resets the ${ database.name } database", "reset${ database.name.capitalize() }Database") {
                subtasks = [dropDatabaseTask, createDatabaseTask, initDatabaseTask, migrateDatabaseTask]
            }
        }

        project.task(type: Composite, group: "Developer Machine Setup", description: "Sets up the necessary databases on a new development machine", "setupAllDatabases") {
            subtasks = setupAllDatabasesSubtasks
        }

        project.task(type: Composite, group: "Flyway", description: "Migrates all databases", "migrateAllDatabases") {
            subtasks = migrateAllDatabasesSubtasks
        }

        project.task(type: Composite, group: "MySQL", description: "Drops all databases", "dropAllDatabases") {
            subtasks = dropAllDependencies
        }
    }

    private Task newCreateDatabaseTask(MySQLDatabase databaseParam) {
        return project.task(type: CreateMySQLDatabase, description: "Creates ${ databaseParam.name } database", "create${ databaseParam.name.capitalize() }Database") {
            database = databaseParam
        }
    }

    private Task newDropDatabaseTask(MySQLDatabase databaseParam) {
        return project.task(type: DropMySQLDatabase, description: "Drops ${ databaseParam.name } database", "drop${ databaseParam.name.capitalize() }Database") {
            database = databaseParam
        }
    }

    private Task newInitDatabaseTask(MySQLDatabase databaseParam) {
        project.task(type: FlywayInit, "init${ databaseParam.name.capitalize() }Database") {
            description = "Inits the Flyway schema table for ${ databaseParam.name } database"

            database = databaseParam
        }
    }

    private Task newMigrateDatabaseTask(MySQLDatabase database) {
        Task migrateTask = project.task(group: "Flyway", description: "Migrates the schema of the ${ database.name } database", "migrate${ database.name.capitalize() }Database")  << {
//            def antClasspath = project.configurations.gradleMysqlPlugin + project.files(_migrationsDir)
//            ant.taskdef(
//                    name: 'flywayMigrate',
//                    classname: 'com.googlecode.flyway.ant.MigrateTask',
//                    classpath: antClasspath.asPath)
//
//            ant.flywayMigrate(
//                    driver: 'com.mysql.jdbc.Driver',
//                    url: "jdbc:mysql://localhost/${ database.schema }",
//                    user: database.username,
//                    password: database.password,
//                    baseDir: '',
//                    sqlMigrationPrefix: '')
        }
        return migrateTask;
    }

}
