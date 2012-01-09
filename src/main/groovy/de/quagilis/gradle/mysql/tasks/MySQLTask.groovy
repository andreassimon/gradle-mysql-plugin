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


abstract class MySQLTask extends DefaultTask {
//    def driver        = "com.mysql.jdbc.Driver"
//    def url           = "jdbc:mysql://localhost/"
//    def user          = "root"
//    def password      = "password"
//    def databaseName
//
//
//    public MySQLTask() {
//        group = "MySQL"
//        addBuildscriptDependenciesToGroovyClassloader()
//    }
//
//    private void addBuildscriptDependenciesToGroovyClassloader() {
//        URLClassLoader loader = GroovyObject.class.classLoader
//        project.configurations.gradleMysqlPlugin.each {File file ->
//            loader.addURL(file.toURL())
//        }
//    }
//
//    @TaskAction
//    public void createTestDatabase() {
//        def stmt = null;
//        def conn = null
//        try {
//            registerJdbcDriver()
//            conn = openConnection()
//            executeStatement(conn, databaseName)
//        } catch(SQLException e) {
//            logger.warn "\t" + e.getMessage()
//        } catch(Exception e) {
//            logger.error e.toString()
//        } finally {
//            closeResource(stmt)
//            closeResource(conn)
//        }
//    }
//
//    private void registerJdbcDriver() {
//        Class.forName(driver);
//    }
//
//    private Connection openConnection() {
//        logger.debug "Connecting to database...";
//        conn = DriverManager.getConnection(url, user, password);
//    }
//
//    private void executeStatement(connection, database) {
//        logger.debug "Creating database...";
//        stmt = conn.createStatement();
//        command = sql(database)
//        logger.info "Execute '${ command }'"
//        stmt.executeUpdate(command);
//        logger.debug "Database created successfully...";
//    }
//
//    def abstract sql(databasename)
//
//    private void closeResource(resource) {
//        try{
//            if(resource != null) {
//              resource.close()
//            }
//        } catch(SQLException se) {
//            logger.error se
//        }
//    }
}
