/*
  Copyright (C) 2018 Enrico Bianchi (enrico.bianchi@gmail.com)
  Project       dbping
  Description   A ping like database tester
  License       GPL version 2 (see LICENSE for details)
 */
package com.application.dbping.database;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Database {
    protected Connection conn;
    protected URI uri;
    protected String url;
    protected String query;

    public Database(URI uri) {
        this.uri = uri;
    }

    protected void connect(String user, String password) throws SQLException {
        this.conn = DriverManager.getConnection(this.url, user, password);
    }

    public void ping() throws SQLException {
        Statement stmt;

        stmt = this.conn.createStatement();

        stmt.executeQuery(this.query);
        stmt.close();
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
        }
    }
}
