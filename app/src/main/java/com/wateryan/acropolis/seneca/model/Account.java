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

    /**
     * Should be used whenever the account has already been assigned an ID by the database
     *
     * @param username    Account username
     * @param password    Account password
     * @param serviceName Account's servicename
     * @param host        Account's host
     * @param port        port on the host
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != account.id) return false;
        if (port != account.port) return false;
        if (username != null ? !username.equals(account.username) : account.username != null)
            return false;
        if (password != null ? !password.equals(account.password) : account.password != null)
            return false;
        if (serviceName != null ? !serviceName.equals(
                account.serviceName) : account.serviceName != null) return false;
        return !(host != null ? !host.equals(account.host) : account.host != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + port;
        return result;
    }
}
