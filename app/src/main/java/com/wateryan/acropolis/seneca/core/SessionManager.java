package com.wateryan.acropolis.seneca.core;

import com.wateryan.acropolis.seneca.model.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 8/5/2015.
 */
public class SessionManager {

    private static SessionManager instance;
    private Map<Account, Session> sessions;

    private SessionManager() {
        this.sessions = new HashMap<>();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void initializeSessions(List<Account> accounts) {
        for (Account account : accounts) {
            initializeSession(account);
        }
    }

    public void initializeSession(Account account) {
        Session session = new Session(account);

        this.sessions.put(account, session);
    }


}
