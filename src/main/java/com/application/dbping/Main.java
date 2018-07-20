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

        arguments = this.initargs().parseArgsOrFail(args);
    }

    public static void main(String[] args) {
        Main m;

        m = new Main();
        m.go(args);
    }
}
