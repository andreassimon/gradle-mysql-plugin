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

package de.quagilis.gradle.mysql.domain

import org.junit.*
import static org.junit.Assert.*

import groovy.mock.interceptor.MockFor
import org.gradle.api.logging.Logger

import de.quagilis.gradle.mysql.tasks.MockDatabaseTest
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource


public class MySQLDatabaseTest extends MockDatabaseTest{
    def loggerMocker = new MockFor(Logger.class)

    @Before
    public void setTaskLogger() {
        loggerMocker.demand.asBoolean() { true }
        mySQLDatabase.logger = loggerMocker.proxyInstance()
    }

    @Before
    public void initDatabaseObject() {
        mySQLDatabase = new MySQLDatabase("database")
        mySQLDatabase.schema = "database"
    }

    @Test
    public void shouldConstructDataSource() {
        def mocker = new MockFor(MysqlDataSource.class);
        mocker.demand.with {
            setUrl()      { urlParam      -> assertEquals(mySQLDatabase.url + mySQLDatabase.schema, urlParam) }
            setUser()     { userParam     -> assertEquals(mySQLDatabase.username, userParam) }
            setPassword() { passwordParam -> assertEquals(mySQLDatabase.password, passwordParam) }
        }

        mocker.use {
            mySQLDatabase.getDataSource()
        }
    }

    @Test
    public void shouldExecuteCreateDatabaseOnDataSource() {
        assertSqlCommandIsExecutedOnDatabaseUrl("CREATE DATABASE ${ mySQLDatabase.schema }", mySQLDatabase.url) {
            mySQLDatabase.createDatabase()
        }
    }

    @Test
    public void shouldExecuteDropDatabaseOnDataSource() {
        assertSqlCommandIsExecutedOnDatabaseUrl("DROP DATABASE ${ mySQLDatabase.schema }", mySQLDatabase.url) {
            mySQLDatabase.dropDatabase()
        }
    }

}
