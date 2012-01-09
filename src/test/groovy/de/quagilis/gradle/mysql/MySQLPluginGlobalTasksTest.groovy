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

import org.junit.*
import static org.junit.Assert.*

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder

import de.quagilis.gradle.mysql.tasks.*


class MySQLPluginGlobalTasksTest {
    Project project = ProjectBuilder.builder().build()

    @Before
    public void applyPlugin() {
        project.apply plugin: 'mysql'
        project.databases {
            test
            development
            production
        }
    }

    @Test
    public void shouldAddMigrateAllDatabasesTaskToProject() {
        def migrateAllDatabasesTask = project.tasks.migrateAllDatabases

        assertTrue(migrateAllDatabasesTask instanceof Composite)

        def expectedSubtasks =
            [project.tasks.migrateTestDatabase,
             project.tasks.migrateDevelopmentDatabase,
             project.tasks.migrateProductionDatabase]
        assertTrue(
            "${ migrateAllDatabasesTask } should contain ${ expectedSubtasks }, but was ${ migrateAllDatabasesTask.subtasks }",
            migrateAllDatabasesTask.subtasks.containsAll(expectedSubtasks))
    }

    @Test
    public void shouldAddDropAllDatabasesTaskToProject() {
        def dropAllDatabasesTask = project.tasks.dropAllDatabases

        assertTrue(dropAllDatabasesTask instanceof Composite)

        def expectedSubtasks =
            [project.tasks.dropTestDatabase,
             project.tasks.dropDevelopmentDatabase,
             project.tasks.dropProductionDatabase]
        assertTrue(
            "${ dropAllDatabasesTask } should contain ${ expectedSubtasks }, but was ${ dropAllDatabasesTask.subtasks }",
            dropAllDatabasesTask.subtasks.containsAll(expectedSubtasks))
    }

}
