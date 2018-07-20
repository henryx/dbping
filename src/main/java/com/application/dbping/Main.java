/*
  Copyright (C) 2018 Enrico Bianchi (enrico.bianchi@gmail.com)
  Project       dbping
  Description   A ping like database tester
  License       GPL version 2 (see LICENSE for details)
 */
package com.application.dbping;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    private ArgumentParser initargs() {
        ArgumentParser parser;

        parser = ArgumentParsers.newFor("dbping").build()
                .defaultHelp(true)
                .description("A database ping tester");

        parser.addArgument("-c")
                .metavar("<count>")
                .setDefault(0)
                .help("Set the number of queries to send to database (0 means infinite)");
        parser.addArgument("database")
                .required(true)
                .help("URI of the database to test");

        return parser;
    }

    private void go(String[] args) {
        Namespace arguments;
        String dburi;
        URI uri;

        arguments = this.initargs().parseArgsOrFail(args);

        dburi = arguments.getString("database");

        try {
            uri = new URI(dburi);

            if (uri.getScheme().equals("mysql")) {
                //TODO: implement MySQL ping
            } else {
                throw new UnsupportedOperationException("Scheme not supported");
            }
        } catch (URISyntaxException e) {
            System.err.println("Malformed URI");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Main m;

        m = new Main();
        m.go(args);
    }
}
