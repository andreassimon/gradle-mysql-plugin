gradle-mysql-plugin, version 0.1.4
==================================

This plugin adds some tasks for the creation and deletion of MySQL databases and the versioned
migration of the database schemas.


Usage
=====

    apply plugin: 'mysql'

    buildscript {
        repositories {
            mavenCentral()

            add(new org.apache.ivy.plugins.resolver.URLResolver()) {
                name = 'GitHub'
                addArtifactPattern 'http://cloud.github.com/downloads/[organisation]/[module]/[module]-[revision].[ext]'
            }
        }
        dependencies {
            classpath 'andreassimon:gradle-mysql-plugin:0.1.4'
            classpath 'com.googlecode.flyway:flyway-core:1.6.1'
        }
    }

    migrationsDir = file('src/main/db/migrations')

    databases {

        test {
            schema = "myapp_test"
        }
        development {
            schema = "myapp_development"
        }
        production {
            url      = "jdbc:mysql://db.myapp.com/"
            schema   = "myapp_production"
            username = "user0815"
            password = "pa$$w0rd"
        }

    }

Migrations have to conform to the format
    yyyymmddhhMMss__migration-description.sql
e.g.
    20120110170000__create-table-user.sql

Run "gradle tasks" and see under the "MySQL", "Database", and "Developer Machine Setup" groups
what tasks were added to your build.


Known Issues
============

In combination with the Java plugin, you cannot use "test" as a database name, because the Java
plugin adds a "test" task to the build. But there is a simple workaround: just write the database
name capitalized:

    databases {
        Test { // !!!
            schema = "myapp_test"
        }
    }
