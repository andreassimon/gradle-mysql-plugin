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

import org.junit.*
import static org.junit.Assert.*

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import de.quagilis.gradle.mysql.domain.MySQLDatabase


public class MySQLDatabaseConventionTest {
    Project project = ProjectBuilder.builder().build()

    @Before
    public void applyMySqlPlugin() {
        project.apply plugin: 'mysql'
        project.migrationsDir = project.file("migrations")
    }

    @Test
    public void shouldCreateADatabaseDomainObjectForEachElementInTheDatabasesClosure() {
        project.databases {
            development { schema = "schema" }
        }

        assertTrue(project.databases.development instanceof MySQLDatabase)
    }

    @Test
    public void shouldFeedDatabaseConnectionPropertiesFromTheDatabasesClosure() {
        project.databases {
            development {
                url      = "url"
                schema   = "schema"
                username = "username"
                password = "password"
            }
        }

        def developmentDatabase = project.databases.development
        assertEquals("url",      developmentDatabase.url)
        assertEquals("schema",   developmentDatabase.schema)
        assertEquals("username", developmentDatabase.username)
        assertEquals("password", developmentDatabase.password)
    }

    @Test(expected=AssertionError)
    public void shouldRequireMigrationsDirToBeSetWhenConfiguringDatabases() {
        project.migrationsDir = null // !!!

        project.databases {
            development { schema = "schema" }
        }
    }

    @Test(expected=AssertionError)
    public void shouldRequireSchemaNameOnEveryConfiguredDatabase() {
         project.databases {
             development {
                 schema = null // !!!
             }
         }
     }

}
