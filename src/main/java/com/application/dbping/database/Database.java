package com.application.dbping.database;

import java.net.URI;

public abstract class Database {
    private URI uri;

    public Database(URI uri) {
        this.uri = uri;
    }

    public URI getURI() {
        return uri;
    }
    
    public abstract void ping(int count);
    public abstract void ping();
}
