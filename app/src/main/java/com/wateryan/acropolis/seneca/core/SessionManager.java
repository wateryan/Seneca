package com.wateryan.acropolis.seneca.core;

import android.os.AsyncTask;

import com.wateryan.acropolis.seneca.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 8/5/2015.
 * <p/>
 * SessionManager acts as the intermediary between Accounts and their associated sessions on a server
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
