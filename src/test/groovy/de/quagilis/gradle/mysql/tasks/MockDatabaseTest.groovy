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

import org.gradle.api.*
import org.gradle.testfixtures.ProjectBuilder

import java.sql.*

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource
import de.quagilis.gradle.mysql.domain.MySQLDatabase


class MockDatabaseTest {
    Project project = ProjectBuilder.builder().build()

    def databaseMocker = new MockFor(MySQLDatabase.class)
    def mySQLDataSourceMocker = new MockFor(MysqlDataSource.class)
    def connectionMocker = new MockFor(Connection.class)
    def statementMocker = new MockFor(Statement.class)

    MySQLDatabase mySQLDatabase

    @Before
    public void applyMySQLPlugin() {
        project.apply plugin: 'mysql'
    }

    @Before
    public void setupMySQLDatabaseDomainObject() {
        mySQLDatabase = new MySQLDatabase("test")
        mySQLDatabase.schema = "test_database"
    }

    void assertStatementIsExecuted(String expectedStatement, Closure closure) {
        assertSqlCommandIsExecutedOnDatabaseUrl(expectedStatement, mySQLDatabase.url, closure)
    }

    def assertSqlCommandIsExecutedOnDatabaseUrl(String expectedSqlCommand, String expectedDatabaseUrl, Closure closure) {
        statementMocker.demand.with {
            execute() { command -> assertEquals(expectedSqlCommand.toString(), command.toString()) }
            close() { }
        }
        def mockStatement = statementMocker.proxyInstance()

        connectionMocker.demand.with {
            createStatement() { mockStatement }
            close() { }
        }
        def mockConnection = connectionMocker.proxyInstance()

        mySQLDataSourceMocker.demand.with {
            setUrl() { url -> assertEquals(expectedDatabaseUrl, url) }
            setUser() { user -> assertEquals(mySQLDatabase.username, user) }
            setPassword() { password -> assertEquals(mySQLDatabase.password, password) }
            getConnection() { mockConnection }
        }

        mySQLDataSourceMocker.use {
            closure.call()
        }

        connectionMocker.verify(mockConnection)
        statementMocker.verify(mockStatement)
    }

    void assertBelongsToGroupMySQL(Task task) {
        assertEquals("MySQL", task.group)
    }

}
