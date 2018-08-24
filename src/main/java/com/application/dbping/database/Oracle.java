/*
  Copyright (C) 2018 Enrico Bianchi (enrico.bianchi@gmail.com)
  Project       dbping
  Description   A ping like database tester
  License       GPL version 2 (see LICENSE for details)
 */
package com.application.dbping.database;

import java.net.URI;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Oracle extends Database {

    private String url;

    public Oracle(URI uri, String user, String password) throws SQLException {
        super(uri);

        this.url = generate();

        this.connect(user, password);
    }

    private String generate() {
        String result;
        int port;

        port = this.uri.getPort() == -1 ? 1521 : this.uri.getPort();

        result = "jdbc:oracle:thin:@"
                + this.uri.getHost()
                + ":"
                + port
                + ":"
                + this.uri.getPath().substring(1);

        return result;
    }

    private void connect(String user, String password) throws SQLException {
        this.conn = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void ping() throws SQLException {
        Statement stmt;

        stmt = this.conn.createStatement();

        stmt.executeQuery("SELECT SYSDATE FROM dual");
        stmt.close();
    }
}
