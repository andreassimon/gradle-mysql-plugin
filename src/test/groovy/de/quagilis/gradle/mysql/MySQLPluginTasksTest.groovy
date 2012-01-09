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

import org.gradle.api.DefaultTask
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder

import de.quagilis.gradle.mysql.tasks.*


class MySQLPluginTasksTest {
    Project project = ProjectBuilder.builder().build()

    @Before
    public void applyPlugin() {
        project.apply plugin: 'mysql'
        project.databases { development }
    }

    @Test
    public void shouldAddCreateDatabaseTaskToProject() {
        assertTrue(project.tasks.createDevelopmentDatabase instanceof CreateMySQLDatabase)
    }

    @Test
    public void shouldAddDropDatabaseTaskToProject() {
        assertTrue(project.tasks.dropDevelopmentDatabase instanceof DropMySQLDatabase)
    }

    @Test
    public void shouldAddInitDatabaseTaskToProject() {
        assertTrue(project.tasks.initDevelopmentDatabase instanceof DefaultTask)
    }

    @Test
    public void shouldAddMigrateDatabaseTaskToProject() {
        assertTrue(project.tasks.migrateDevelopmentDatabase instanceof DefaultTask)
    }

    @Test
    public void shouldAddResetDatabaseTaskToProject() {
        def resetDatabaseTask = project.tasks.resetDevelopmentDatabase

        assertTrue(resetDatabaseTask instanceof Composite)
        assertEquals(
            [project.tasks.dropDevelopmentDatabase,
             project.tasks.createDevelopmentDatabase,
             project.tasks.initDevelopmentDatabase,
             project.tasks.migrateDevelopmentDatabase],
            resetDatabaseTask.subtasks
        )
    }

}
