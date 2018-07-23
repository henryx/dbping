/*
  Copyright (C) 2018 Enrico Bianchi (enrico.bianchi@gmail.com)
  Project       dbping
  Description   A ping like database tester
  License       GPL version 2 (see LICENSE for details)
 */
package com.application.dbping.database;

import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class Database {
    protected URI uri;
    protected Connection conn;

    public Database(URI uri) {
        this.uri = uri;
    }

    public abstract void ping() throws SQLException;

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
        }
    }
}
