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

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource


class MySQLDatabase {
    String name
    String url      = "jdbc:mysql://localhost/"
    String schema
    String username = "root"
    String password = ""

    MySQLDatabase(String name) {
        this.name = name
    }

    def getDataSource() {
        def dataSource = new MysqlDataSource()
        dataSource.url      = url // + schema
        dataSource.user     = username
        dataSource.password = password
        return dataSource
    }
}
