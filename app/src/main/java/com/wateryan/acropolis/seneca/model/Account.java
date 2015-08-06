package com.wateryan.acropolis.seneca.model;

import java.io.Serializable;

/**
 * Created on 8/2/2015.
 */
public class Account implements Serializable {

    private int id;
    private String username;
    private String password;
    private String serviceName;
    private String host;
    private int port;

    public Account(int id, String username, String password, String serviceName, String host, int port) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.serviceName = serviceName;
        this.host = host;
        this.port = port;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }
}
