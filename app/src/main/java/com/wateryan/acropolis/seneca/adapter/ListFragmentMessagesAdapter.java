package com.wateryan.acropolis.seneca.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.jivesoftware.smack.chat.Chat;

import java.util.List;

/**
 * Created on 8/8/2015.
 */
public class ListFragmentMessagesAdapter extends ArrayAdapter<Chat> {
    public ListFragmentMessagesAdapter(Context context, int resource, List<Chat> objects) {
        super(context, resource, objects);
    }
}
