package com.wateryan.acropolis.seneca.core.session;

import android.content.SharedPreferences;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * Created on 7/31/2015.
 */
public class SessionController {

    private final SharedPreferences preferences;
    private XMPPTCPConnectionConfiguration configuration;
    private AbstractXMPPConnection connection;

    public SessionController(SharedPreferences sharedPreferences) {
        this.preferences = sharedPreferences;
    }

    public void setUsername(String username) {
        this.preferences.edit().putString(Key.USERNAME.toString(), username).commit();
    }

    public String getUsername() {
        return this.preferences.getString(Key.USERNAME.toString(), null);
    }

    public void setPassword(String password) {
        this.preferences.edit().putString(Key.PASSWORD.toString(), password).commit();
    }

    private String getPassword() {
        return this.preferences.getString(Key.PASSWORD.toString(), null);
    }

    public void setServiceName(String serviceName) {
        this.preferences.edit().putString(Key.SERVICE_NAME.toString(), serviceName);
    }

    public String getServiceName() {
        return this.preferences.getString(Key.SERVICE_NAME.toString(), null);
    }

    public void setHost(String host) {
        this.preferences.edit().putString(Key.HOST.toString(), host).commit();
    }

    public String getHost() {
        return this.preferences.getString(Key.HOST.toString(), null);
    }

    // Why this isn't an integer, I don't know.
    public void setPort(int port) {
        this.preferences.edit().putInt(Key.PORT.toString(), port);
    }

    public int getPort() {
        return this.preferences.getInt(Key.PORT.toString(), -1);
    }

    /**
     * TODO make session builder
     */
    public void initializeSession() {
        this.configuration = XMPPTCPConnectionConfiguration.builder().setUsernameAndPassword(getUsername(), getPassword()).setServiceName(getServiceName()).setHost(getHost()).setPort(getPort()).build();
        this.connection = new XMPPTCPConnection(this.configuration);
    }

    public void close() {
        this.preferences.edit().clear().commit();
    }

    private enum Key {
        PREFERENCES("preferences"), USERNAME("username"), PASSWORD("password"), SERVICE_NAME("service"), HOST("host"), PORT("port");
        private final String key;

        Key(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return this.key;
        }
    }
}

