package com.wateryan.acropolis.seneca.core;

import com.wateryan.acropolis.seneca.model.Account;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 8/5/2015.
 */
public class Session {

    private static final Logger logger = Logger.getLogger(Session.class.getName());

    private Account account;
    private XMPPTCPConnectionConfiguration configuration;
    private AbstractXMPPConnection connection;
    private boolean isInitialized;

    public Session(Account account) {
        this.account = account;
        this.isInitialized = false;
    }

    public void initialize() {
        this.configuration = XMPPTCPConnectionConfiguration.builder().setUsernameAndPassword(
                this.account.getUsername(), this.account.getPassword()).setServiceName(
                this.account.getServiceName()).setHost(this.account.getHost()).setPort(
                this.account.getPort()).build();
        this.connection = new XMPPTCPConnection(this.configuration);
        try {
            this.connection.connect();
            this.isInitialized = true;
        } catch (SmackException | IOException | XMPPException e) {
            logger.log(Level.WARNING, "Unable to establish session. ", e);
        }
    }

    public boolean isInitialized() {
        return this.isInitialized;
    }

    public void setPresence(Presence.Type presenceType) {
        setPresence(presenceType, null);
    }

    public void setPresence(Presence.Type presenceType, String status) {
        Presence presence = new Presence(presenceType);
        if (status != null) {
            presence.setStatus(status);
        }
        sentStanza(presence);
    }

    private void sentStanza(Stanza stanza) {
        try {
            this.connection.sendStanza(stanza);
        } catch (SmackException.NotConnectedException e) {
            logger.log(Level.WARNING, "Session not established.", e);
        }
    }

}