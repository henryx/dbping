/*
  Copyright (C) 2018 Enrico Bianchi (enrico.bianchi@gmail.com)
  Project       dbping
  Description   A ping like database tester
  License       GPL version 2 (see LICENSE for details)
 */
package com.application.dbping.database;

import java.net.URI;
import java.sql.SQLException;

public class PostgreSQL extends Database {
    public PostgreSQL(URI uri, String user, String password) throws SQLException {
        super(uri);

        this.url = generate();
        this.query = "SELECT now()";

        this.connect(user, password);
    }

    private String generate() {
        String result;
        int port;

        port = this.uri.getPort() == -1 ? 5432 : this.uri.getPort();

        result = "jdbc:" +
        this.uri.getScheme() +
                "://" +
                this.uri.getHost() +
                ":" +
                port +
                this.uri.getPath();

        return result;
    }
}
