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

package de.quagilis.gradle.mysql;

import org.gradle.api.Project
import org.gradle.api.Plugin
import de.quagilis.gradle.mysql.tasks.CreateMySQLDatabase


class MySQLPlugin implements Plugin<Project> {


    void apply(Project project) {
        applyConvention(project)
        applyTasks(project)
    }


    void applyConvention(Project project) {
        def databases = project.container(MySQLDatabase) { name ->
            new MySQLDatabase(name)
        }
        project.convention.plugins.mysql =
            new MySQLDatabaseConvention(databases)
    }


    void applyTasks(Project project) {
        def database = new MySQLDatabase("Test")
        database.schema = "web2print_test"
        project.task(type: CreateMySQLDatabase, description: "Creates ${ database.schema } database", "create${ database.name }Database")
    }

}

