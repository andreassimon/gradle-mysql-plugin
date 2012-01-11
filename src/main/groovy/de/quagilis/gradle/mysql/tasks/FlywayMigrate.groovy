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

package de.quagilis.gradle.mysql.tasks

import de.quagilis.gradle.mysql.domain.MySQLDatabase


class FlywayMigrate extends FlywayTask {
    MySQLDatabase database

    FlywayMigrate() {
        this << taskAction
    }

    def taskAction = {
        def antClasspath = project.configurations.gradleMysqlPlugin + project.files(project.migrationsDir)
        ant.taskdef(
            name: 'flywayMigrate',
            classname: 'com.googlecode.flyway.ant.MigrateTask',
            classpath: antClasspath.asPath)

        ant.flywayMigrate(
            driver: 'com.mysql.jdbc.Driver',
            url: "jdbc:mysql://localhost/${ database.schema }",
            user: database.username,
            password: database.password,
            baseDir: '',
            sqlMigrationPrefix: '')
    }

}