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

import org.junit.Before
import static org.junit.Assert.*

import groovy.mock.interceptor.MockFor

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

import java.sql.*
import javax.sql.*

import de.quagilis.gradle.mysql.domain.MySQLDatabase


class MockDatabaseTest {
    Project project = ProjectBuilder.builder().build()

    def databaseMocker = new MockFor(MySQLDatabase.class)
    def dataSourceMocker = new MockFor(DataSource.class)
    def connectionMocker = new MockFor(Connection.class)
    def statementMocker = new MockFor(Statement.class)

    @Before
    public void applyMySQLPlugin() {
        project.apply plugin: 'mysql'
    }

    def mockDatabase() {
        return databaseMocker.proxyInstance()
    }

    void assertStatementIsExecuted(String expectedStatement, Closure closure) {
        statementMocker.demand.executeUpdate() { command ->  assertEquals(expectedStatement, command) }
        def mockStatement = statementMocker.proxyInstance()

        connectionMocker.demand.createStatement() { mockStatement }
        dataSourceMocker.demand.getConnection() { username, password -> connectionMocker.proxyInstance() }
        databaseMocker.demand.with {
            getDataSource() {
                dataSourceMocker.proxyInstance()
            }
            getUsername() { "username" }
            getPassword() { "password" }
            getSchema()   { "test_database" }
        }

        closure.call()

        statementMocker.verify(mockStatement)
    }

}
