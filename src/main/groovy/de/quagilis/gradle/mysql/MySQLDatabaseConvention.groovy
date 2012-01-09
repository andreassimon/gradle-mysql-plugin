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
//    final NamedDomainObjectContainer<MySQLDatabase> databases
//    File _migrationsDir

    MySQLDatabaseConvention(Project project) {
        this.project   = project
    }

//    def setMigrationsDir(File migrationsDir) {
//        _migrationsDir = migrationsDir
//    }
//
//    def databases(Closure closure) {
//        databases.configure(closure)
//
//        def setupAllDatabasesSubtasks   = []
//        def migrateAllDatabasesSubtasks = []
//        def dropAllDependencies         = []
//
//        databases.each { database ->
//            def createDatabaseTask = newCreateDatabaseTask(database)
//            def dropDatabaseTask   = newDropDatabaseTask(database)
//            def initDatabaseTask   = newInitDatabaseTask(database)
//            def migrateTask        = newMigrateDatabaseTask(database)
//
//            setupAllDatabasesSubtasks << createDatabaseTask << initDatabaseTask << migrateTask
//            migrateAllDatabasesSubtasks << migrateTask
//            dropAllDependencies         << dropDatabaseTask
//
//            project.task(type: Composite, group: "MySQL", description: "Resets the ${ database.name } database", "reset${ database.name.capitalize() }Database") {
//                subtasks = [dropDatabaseTask, createDatabaseTask, initDatabaseTask, migrateTask]
//            }
//        }
//
//        project.task(type: Composite, group: "Developer Machine Setup", description: "Sets up the necessary databases on a new development machine", "setupAllDatabases") {
//            subtasks = setupAllDatabasesSubtasks
//        }
//
//        project.task(type: Composite, group: "Flyway", description: "Migrates all databases", "migrateAllDatabases") {
//            subtasks = migrateAllDatabasesSubtasks
//        }
//
//        project.task(type: Composite, group: "MySQL", description: "Drops all databases", "dropAllDatabases") {
//            subtasks = dropAllDependencies
//        }
//    }
//
//    private Task newCreateDatabaseTask(MySQLDatabase database) {
//        return project.task(type: CreateMySQLDatabase, description: "Creates ${ database.name } database", "create${ database.name.capitalize() }Database") {
//            url          = database.url
//            user         = database.username
//            password     = database.password;
//            databaseName = database.schema
//        }
//    }
//
//    private Task newDropDatabaseTask(MySQLDatabase database) {
//        return project.task(type: DropMySQLDatabase, description: "Drops ${ database.name } database", "drop${ database.name.capitalize() }Database") {
//            url          = database.url
//            user         = database.username
//            password     = database.password;
//            databaseName = database.schema
//        }
//    }
//
//    private Task newInitDatabaseTask(MySQLDatabase database) {
//        project.task(group: "Flyway", description: "Inits the Flyway schema table for ${ database.name } database", "init${ database.name.capitalize() }Database") << {
//            ant.taskdef(
//                    name: 'flywayInit',
//                    classname: 'com.googlecode.flyway.ant.InitTask',
//                    classpath: project.configurations.gradleMysqlPlugin.asPath)
//
//            ant.flywayInit(
//                    driver: 'com.mysql.jdbc.Driver',
//                    url: database.url,
//                    user: database.username,
//                    password: database.password,
//                    schemas: database.schema)
//        }
//    }
//
//    private Task newMigrateDatabaseTask(MySQLDatabase database) {
//        assert _migrationsDir != null;
//        Task migrateTask = project.task(group: "Flyway", description: "Migrates the schema of the ${ database.name } database", "migrate${ database.name.capitalize() }Database")  << {
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
//        }
//        return migrateTask;
//    }

}
