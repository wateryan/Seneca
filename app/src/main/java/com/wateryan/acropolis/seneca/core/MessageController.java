package com.wateryan.acropolis.seneca.core;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/30/2015
 */
public class MessageController {

    private static MessageController instance;
    private DbController dbController;
    private SessionManager sessionManager;
    private List<ChatManager> chatManagers;
    private List<Chat> inboundMessageStore;

    private MessageController() {
        this.sessionManager = SessionManager.getInstance();
        this.chatManagers = new ArrayList<>();
        this.inboundMessageStore = new ArrayList<>();
        initializeChatManagers(this.sessionManager.getAllConnections());
    }

    public static MessageController getInstance() {
        if (instance == null) {
            instance = new MessageController();
        }
        return instance;
    }

    public List<Chat> getInboundMessageStore() {
        return this.inboundMessageStore;
    }

    private void populateMessageStoreFromDb() {
        // TODO
    }

    private void initializeChatManagers(List<XMPPConnection> connections) {
        for (XMPPConnection connection : connections) {
            ChatManager manager = ChatManager.getInstanceFor(connection);
            manager.addChatListener(new ChatManagerListener() {
                @Override
                public void chatCreated(Chat chat, boolean createdLocally) {
                    inboundMessageStore.add(chat);
                }
            });
            this.chatManagers.add(manager);
        }
    }
}
