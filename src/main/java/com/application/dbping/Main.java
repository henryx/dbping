/*
  Copyright (C) 2018 Enrico Bianchi (enrico.bianchi@gmail.com)
  Project       dbping
  Description   A ping like database tester
  License       GPL version 2 (see LICENSE for details)
 */
package com.application.dbping;

import com.application.dbping.database.Database;
import com.application.dbping.database.MySQL;
import com.application.dbping.database.Oracle;
import com.application.dbping.database.PostgreSQL;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final String VERSION = "1.1.0";

    private ArgumentParser initargs() {
        ArgumentParser parser;

        parser = ArgumentParsers.newFor("dbping").build()
                .defaultHelp(true)
                .description("A database ping tester");

        parser.addArgument("-c")
                .metavar("<count>")
                .setDefault(0)
                .help("Set the number of queries to send to database (0 means infinite)");
        parser.addArgument("-V", "--version")
                .action(Arguments.storeTrue())
                .help("Show version and exit");

        parser.addArgument("database")
                .required(true)
                .help("URI of the database to test");

        return parser;
    }

    private String[] getauth(String auth) throws IOException {
        BufferedReader br;
        Console console;
        String[] data, result;

        br = new BufferedReader(new InputStreamReader(System.in));
        result = new String[2];
        if (auth == null) {

            System.out.print("Enter username: ");
            result[0] = br.readLine();
        } else {
            data = auth.split(":");

            result[0] = data[0];
            if (data.length > 1) {
                result[1] = data[1];
            }
        }

        if (result[0].equals("")) {
            throw new IOException("No user passed");
        }

        if (result[1] == null || result[1].equals("")) {
            console = System.console();
            System.out.print("Enter password: ");
            try {
                result[1] = String.valueOf(console.readPassword());
            } catch (NullPointerException e) {
                // Fallback to clear input, if console does not work
                result[1] = br.readLine();
            }
        }

        return result;
    }

    private Database getDb(URI uri, String[] auth) throws SQLException {
        Database result;
        String scheme;

        scheme = uri.getScheme();

        if (scheme.equals("mysql")) {
            result = new MySQL(uri, auth[0], auth[1]);
        } else if (scheme.equals("oracle")) {
            result = new Oracle(uri, auth[0], auth[1]);
        } else if (scheme.equals("postgresql")) {
            result = new PostgreSQL(uri, auth[0], auth[1]);
        } else {
            throw new UnsupportedOperationException("Scheme not supported");
        }

        return result;
    }

    private void ping(int iterations, Database db) {
        int counted;

        counted = 0;
        while (true) {
            try {
                if (iterations > 0 && counted == iterations) {
                    break;
                } else {
                    counted++;
                }
                System.out.print("Executed query " + counted + ": ");

                db.ping();
                System.out.println("... Ok!");
            } catch (SQLException e) {
                System.out.println("Cannot retrieve data from database: " + e.getMessage());
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void go(String[] args) {
        Database db;
        Namespace arguments;
        String dburi;
        String[] auth;
        URI uri;
        int iterations;

        arguments = this.initargs().parseArgsOrFail(args);

        if (arguments.getBoolean("version")) {
            System.out.println("A ping like database tester version " + Main.VERSION);
            System.exit(0);
        }

        dburi = arguments.getString("database");

        try {
            uri = new URI(dburi);
            auth = getauth(uri.getUserInfo());
            db = this.getDb(uri, auth);

            iterations = arguments.getInt("c");
            this.ping(iterations, db);

            db.close();
        } catch (URISyntaxException e) {
            System.err.println("Malformed URI");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            System.exit(1);
        } catch (IOException ignored) {
        }
    }

    public static void main(String[] args) {
        Main m;

        m = new Main();
        m.go(args);
    }
}
