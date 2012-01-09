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
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder

import de.quagilis.gradle.mysql.tasks.*


class MySQLPluginGlobalTasksTest {
    Project project = ProjectBuilder.builder().build()
    List<Task> allCreateTasks, allTasksForTestDatabase

    @Before
    public void applyPlugin() {
        project.apply plugin: 'mysql'
        project.databases {
            test
            development
            production
        }

        allCreateTasks =
           [createTestDatabase,
            createDevelopmentDatabase,
            createProductionDatabase]
        allTasksForTestDatabase =
           [createTestDatabase,
            initTestDatabase,
            migrateTestDatabase]
    }

    @Test
    public void shouldAddMigrateAllDatabasesTaskToProject() {
        assertTrue(migrateAllDatabases instanceof Composite)
        assertCompositeContainsAllSubtasks(
            migrateAllDatabases,
                [migrateTestDatabase,
                 migrateDevelopmentDatabase,
                 migrateProductionDatabase])
    }

    @Test
    public void shouldAddDropAllDatabasesTaskToProject() {
        assertTrue(dropAllDatabases instanceof Composite)
        assertCompositeContainsAllSubtasks(
            dropAllDatabases,
                [dropTestDatabase,
                 dropDevelopmentDatabase,
                 dropProductionDatabase])
    }

    @Test
    public void shouldAddSetupAllDatabasesTaskToProject() {
        assertTrue(setupAllDatabases instanceof Composite)
        assertCompositeContainsAllSubtasks(
            setupAllDatabases, allCreateTasks)
        assertCompositeContainsAllSubtasks(
            setupAllDatabases, allTasksForTestDatabase)
    }

    private void assertCompositeContainsAllSubtasks(Composite compositeTask, List<Task> expectedSubtasks) {
        assertTrue(
            "${ compositeTask } should contain ${ expectedSubtasks }, but was ${ compositeTask.subtasks }",
            compositeTask.subtasks.containsAll(expectedSubtasks))
    }

    Composite getSetupAllDatabases() {
        project.tasks.setupAllDatabases
    }

    Composite getDropAllDatabases() {
        project.tasks.dropAllDatabases
    }

    Composite getMigrateAllDatabases() {
        project.tasks.migrateAllDatabases
    }

    Task getCreateTestDatabase() {
        project.tasks.createTestDatabase
    }

    Task getCreateDevelopmentDatabase() {
        project.tasks.createDevelopmentDatabase
    }

    Task getCreateProductionDatabase() {
        project.tasks.createProductionDatabase
    }

    Task getInitTestDatabase() {
        project.tasks.initTestDatabase
    }

    Task getMigrateTestDatabase() {
        project.tasks.migrateTestDatabase
    }

    Task getMigrateDevelopmentDatabase() {
        project.tasks.migrateDevelopmentDatabase
    }

    Task getMigrateProductionDatabase() {
        project.tasks.migrateProductionDatabase
    }

    Task getDropTestDatabase() {
        project.tasks.dropDevelopmentDatabase
    }

    Task getDropDevelopmentDatabase() {
        project.tasks.dropDevelopmentDatabase
    }

    Task getDropProductionDatabase() {
        project.tasks.dropProductionDatabase
    }

}
