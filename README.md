# dbping
A ping like database tester

## Description
`dbping` is a tool that permit to "ping" a database server. Scope of the tool is the same of the ping tool on Linux an Windows

## Requirements

`dbping` require Java 6. No other dependecies are required

## Usage

`java -jar dbping.jar <scheme>://[<user>[:<password>]]@<host>[:port]/<database>`

Where:
 - `scheme` is the RDBMS specified.
 - `user` and `password` are credentials to connect to the database. If not specified, `dbping` will ask you to enter them 
 - `host` is the hostname that the RDBMS runs.
 - `port` is the TCP port number where database listen. If not specified, it uses the default of the RDBMS specified.  
 - `database` is the database name where the program try to connect.

Databases supported are:

| Database | Version tested | Scheme |
| -------- | -------------: | -----: |
| Oracle | 11g | `oracle` |
| MySQL | 8.0 | `mysql` |
| PostgreSQL | 10 | `postgresql` |

## Building

For building, although is necessary the Oracle JDBC driver, you need to read this: https://blogs.oracle.com/dev2dev/get-oracle-jdbc-drivers-and-ucp-from-oracle-maven-repository-without-ides