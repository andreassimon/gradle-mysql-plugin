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
import de.quagilis.gradle.mysql.tasks.CreateMySQLDatabase


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
}
