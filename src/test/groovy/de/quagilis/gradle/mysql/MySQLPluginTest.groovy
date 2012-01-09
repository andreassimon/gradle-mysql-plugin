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

import org.junit.Test
import static org.junit.Assert.assertTrue

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder


public class MySQLPluginTest {
    static Project project = ProjectBuilder.builder().build()

    @Test
    public void shouldAddMySQLDatabaseConventionToProject() {
        project.apply plugin: 'mysql'

        assertTrue("The plugin should add the MySQLDatabaseConvention to the project", project.convention.plugins.mysql instanceof MySQLDatabaseConvention)
    }

}
