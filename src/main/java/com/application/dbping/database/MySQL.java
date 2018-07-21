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

public class MySQL extends Database {
    private String url;

    public MySQL(URI uri, String user, String password) throws SQLException {
        super(uri);

        this.url = generate();

        this.connect(user, password);
    }

    private String generate() {
        String result = "jdbc:" +
                this.uri.getScheme() +
                "://" +
                this.uri.getHost() +
                ":" +
                this.uri.getPort() +
                "/mysql";

        return result;
    }

    private void connect(String user, String password) throws SQLException {
        this.conn = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void ping(int count) {

    }

    @Override
    public void ping() {

    }
}