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

import org.junit.*
import static org.junit.Assert.*

import org.gradle.api.*
import org.gradle.testfixtures.ProjectBuilder


public class CompositeTest {
    Project project = ProjectBuilder.builder().build()
    Task subtask

    @Before
    public void applyMySQLPlugin() {
        project.apply plugin: 'mysql'
    }

    @Before
    public void createSubtask() {
        subtask = project.task("subtask") {
            numberOfInvocations = 0
        }
        subtask << {
            numberOfInvocations++
        }
    }

    @Test
    public void shouldBelongToGroup_Utility() {
        def task = project.task(type: Composite, "compositeTask")

        assertEquals("Utility", task.group)
    }

    @Test
    public void shouldExecuteTheAssignedSubtasks() {
        Task compositeTask = project.task(type: Composite, "compositeTask") {
            subtasks = [subtask]
        }

        compositeTask.execute()

        assertEquals("Number of invocations of subtask", 1, subtask.numberOfInvocations)
    }
}
