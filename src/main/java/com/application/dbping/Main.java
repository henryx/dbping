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

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
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
                result[1] =data[1];
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
            }catch (NullPointerException e) {
                // Fallback to clear input, if console does not work
                result[1] = br.readLine();
            }
        }

        return result;
    }

    private void go(String[] args) {
        Namespace arguments;
        String dburi;
        String[] auth;
        URI uri;

        arguments = this.initargs().parseArgsOrFail(args);

        dburi = arguments.getString("database");

        try {
            uri = new URI(dburi);
            auth = getauth(uri.getUserInfo());

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
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        Main m;

        m = new Main();
        m.go(args);
    }
}
