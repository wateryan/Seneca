package com.wateryan.acropolis.seneca.core;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat.ChatManager;

import java.util.List;

/**
 * Created on 7/30/2015.
 */
public class MessageController {

    private static MessageController instance;
    private DbController dbController;
    private SessionManager sessionManager;
    private List<ChatManager> chatManagers;

    private MessageController() {
        this.sessionManager = SessionManager.getInstance();
        initializeChatManagers(this.sessionManager.getAllConnections());
    }

    public static MessageController getInstance() {
        if (instance == null) {
            instance = new MessageController();
        }
        return instance;
    }

    private void initializeChatManagers(List<XMPPConnection> connections) {
        for (XMPPConnection connection : connections) {
            this.chatManagers.add(ChatManager.getInstanceFor(connection));
        }
    }
}
