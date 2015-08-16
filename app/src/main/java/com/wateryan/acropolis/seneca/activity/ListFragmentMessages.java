package com.wateryan.acropolis.seneca.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wateryan.acropolis.seneca.R;
import com.wateryan.acropolis.seneca.adapter.ListFragmentMessagesAdapter;
import com.wateryan.acropolis.seneca.core.network.MessageController;
import com.wateryan.acropolis.seneca.model.SerializableChatWrapper;

import org.jivesoftware.smack.chat.Chat;

import java.util.List;

/**
 * Created on 7/29/2015.
 */
public class ListFragmentMessages extends ListFragment {

    private MessageController controller;

    private List<Chat> chats;

    public ListFragmentMessages() {
        this.controller = MessageController.getInstance();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Fragment fragment = new ListFragmentMessagesDetail();
        Bundle args = new Bundle();
        args.putSerializable("chat", new SerializableChatWrapper(this.chats.get(position)));
        fragment.setArguments(args);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Chat> chatList = this.controller.getInboundMessageStore();
        ArrayAdapter<Chat> adapter = new ListFragmentMessagesAdapter(inflater.getContext(),
                android.R.layout.simple_list_item_1, chatList);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
