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

import java.sql.*
import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask
import de.quagilis.gradle.mysql.domain.MySQLDatabase


abstract class MySQLTask extends DefaultTask {
    MySQLDatabase database = null

    public MySQLTask() {
        group = "MySQL"
    }

    public void executeStatement() {
        def connection = null
        def statement = null;
        try {
            def dataSource = database.dataSource
            connection = dataSource.getConnection(database.username, database.password)
            statement = connection.createStatement()
            statement.executeUpdate(sql(database.schema))
        } catch(SQLException e) {
            logger.warn "\t" + e.getMessage()
        } finally {
            closeResource(statement)
            closeResource(connection)
        }
    }

    abstract String sql(databasename)


    private void closeResource(resource) {
        try{
            if(resource != null) {
                resource.close()
            }
        } catch(SQLException e) {
            logger.error("", e)
        }
    }

}
