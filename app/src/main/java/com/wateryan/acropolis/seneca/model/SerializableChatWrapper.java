package com.wateryan.acropolis.seneca.model;

import org.jivesoftware.smack.chat.Chat;

import java.io.Serializable;

/**
 * Created on 8/7/2015.
 */
public class SerializableChatWrapper implements Serializable {

    private Chat chat;

    public SerializableChatWrapper(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return this.chat;
    }
}
