package com.wateryan.acropolis.seneca.core;

import com.wateryan.acropolis.seneca.model.Account;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.util.List;
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
    private RosterController rosterController;

    public Session(Account account) {
        this.account = account;
    }

    public void initialize() {
        this.configuration = XMPPTCPConnectionConfiguration.builder().setUsernameAndPassword(
                this.account.getUsername(), this.account.getPassword()).setServiceName(
                this.account.getServiceName()).setHost(this.account.getHost()).setPort(
                this.account.getPort()).setSecurityMode(
                ConnectionConfiguration.SecurityMode.disabled).setDebuggerEnabled(true).build();
        this.connection = new XMPPTCPConnection(this.configuration);
        this.connection.setPacketReplyTimeout(10000);
        try {
            this.connection.connect();
            this.connection.login();
        } catch (SmackException | IOException | XMPPException e) {
            logger.log(Level.WARNING, "Unable to establish session. ", e);
        }
        this.rosterController = new RosterController(this.connection);
    }

    public void close() {
        if (this.connection.isConnected()) {
            try {
                this.connection.disconnect(new Presence(Presence.Type.unavailable));
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    public XMPPConnection getConnection() {
        return this.connection;
    }

    public boolean isConnected() {
        return this.connection.isConnected();
    }

    public List<RosterEntry> getRosterEntries() {
        return this.rosterController.getRosterEntries();
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

    public Presence getPresenceOfUser(String user) {
        return Roster.getInstanceFor(this.connection).getPresence(user);
    }

    private void sentStanza(Stanza stanza) {
        try {
            this.connection.sendStanza(stanza);
        } catch (SmackException.NotConnectedException e) {
            logger.log(Level.WARNING, "Session not established.", e);
        }
    }

}
