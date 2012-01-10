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

import org.junit.Test
import static org.junit.Assert.*
import groovy.mock.interceptor.MockFor
import java.sql.Connection
import java.sql.Statement
import de.quagilis.gradle.mysql.domain.MySQLDatabase
import javax.sql.DataSource
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.gradle.api.Task;


public class CreateMySQLDatabaseTest {
    Project project = ProjectBuilder.builder().build()

    @Before
    public void applyMySQLPlugin() {
        project.apply plugin: 'mysql'
    }


    @Test
    public void shouldExecuteCreateDatabaseOnDataSource() {
        def databaseMocker = new MockFor(MySQLDatabase.class)
        def dataSourceMocker = new MockFor(DataSource.class)
        def connectionMocker = new MockFor(Connection.class)
        def statementMocker = new MockFor(Statement.class)

        statementMocker.demand.executeUpdate(1)    { command -> assertEquals("CREATE DATABASE test_database", command) }
        Statement statement = statementMocker.proxyInstance()

        connectionMocker.demand.createStatement(1) { statement }
        def connection = connectionMocker.proxyInstance()
        dataSourceMocker.demand.getConnection(1)   { username, password ->
            connection
        }
        def dataSource = dataSourceMocker.proxyInstance()
        databaseMocker.demand.with {
            getDataSource(1) {
                dataSource
            }
            getUsername() { "username" }
            getPassword() { "password" }
            getSchema()   { "test_database" }
        }

        def testDatabase = databaseMocker.proxyInstance()

        Task createDatabaseTask = project.task(type: CreateMySQLDatabase, "createDatabaseTask") {
            database = testDatabase
        }
        createDatabaseTask.execute()

        databaseMocker.verify(testDatabase)
        dataSourceMocker.verify(dataSource)
        connectionMocker.verify(connection)
        statementMocker.verify(statement)
    }

}
