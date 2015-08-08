package com.wateryan.acropolis.seneca.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.jivesoftware.smack.chat.Chat;

/**
 * Created on 8/7/2015.
 */
public class ListFragmentMessagesDetail extends Fragment {

    private static final String ARG_CHAT = "chat";
    private Chat chat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_CHAT)) {
            this.chat = (Chat) getArguments().get(ARG_CHAT);
        }
    }


}
