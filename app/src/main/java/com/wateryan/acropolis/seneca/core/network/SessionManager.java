package com.wateryan.acropolis.seneca.core.network;

import android.os.AsyncTask;

import com.wateryan.acropolis.seneca.model.Account;
import com.wateryan.acropolis.seneca.model.Contact;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 8/5/2015.
 * <p/>
 * DAL for network IO
 */
public class SessionManager {

    private static SessionManager instance;
    private Map<Account, Session> sessionsMap;

    private SessionManager() {
        this.sessionsMap = new HashMap<>();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public List<AsyncTask> initializeSessions(List<Account> accounts) {
        List<AsyncTask> tasks = new ArrayList<>();
        for (Account account : accounts) {
            tasks.add(initializeSession(account));
        }
        return tasks;
    }

    public AsyncTask initializeSession(Account account) {
        Session session;
        if (!this.sessionsMap.containsKey(account)) {
            session = new Session(account);
            this.sessionsMap.put(account, session);
        } else {
            session = this.sessionsMap.get(account);
        }
        return new AsyncSessionInitializer().execute(session);
    }

    public List<XMPPConnection> getAllConnections() {
        List<XMPPConnection> connections = new ArrayList<>();
        for (Map.Entry<Account, Session> accountSessionEntry : this.sessionsMap.entrySet()) {
            connections.add(accountSessionEntry.getValue().getConnection());
        }
        return connections;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        for (Account account : this.sessionsMap.keySet()) {
            accounts.add(account);
        }
        return accounts;
    }

    public List<RosterEntry> getAllRosterEntries() {
        List<RosterEntry> entries = new ArrayList<>();
        for (Session session : this.sessionsMap.values()) {
            entries.addAll(session.getRosterEntries());
        }
        return entries;
    }

    /**
     * Contact is a serializable wrapper for RosterEntry
     * allowing it to be passed between fragments easily
     *
     * @return
     */
    public List<Contact> getAllRosterEntriesAsContacts() {
        List<Contact> contacts = new ArrayList<>();
        for (RosterEntry entry : getAllRosterEntries()) {
            contacts.add(new Contact(entry));
        }
        return contacts;
    }

    // Seach all connections for users status
    public Presence getPresenseOfUser(String user) {
        Presence presence = null;
        for (Session session : this.sessionsMap.values()) {
            if ((presence = session.getPresenceOfUser(user)) != null) {
                return presence;
            }
        }
        return presence;
    }

    public boolean hasInitializedSession(Account account) {
        return this.sessionsMap.containsKey(account) && this.sessionsMap.get(account).isConnected();
    }

    public void closeAllSessions() {
        for (Account account : this.sessionsMap.keySet()) {
            closeSession(account);
        }
    }

    public void closeSession(Account account) {
        this.sessionsMap.get(account).close();
    }

    public class AsyncSessionInitializer extends AsyncTask<Session, String, String> {

        @Override
        protected String doInBackground(Session... params) {
            StringBuilder results = new StringBuilder("Sessions: \n");
            for (Session session : params) {
                results.append("Initializing session for : ").append(session.toString());
                session.initialize();
            }
            return results.toString();
        }
    }


}
