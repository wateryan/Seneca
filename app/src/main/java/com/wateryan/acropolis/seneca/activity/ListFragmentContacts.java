package com.wateryan.acropolis.seneca.activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wateryan.acropolis.seneca.R;
import com.wateryan.acropolis.seneca.adapter.ListFragmentContactsAdapter;
import com.wateryan.acropolis.seneca.core.network.SessionManager;
import com.wateryan.acropolis.seneca.model.Contact;

import java.util.List;

/**
 * Created on 8/7/2015.
 */
public class ListFragmentContacts extends ListFragment {

    private SessionManager sessionManager;
    private List<Contact> contactList;

    public ListFragmentContacts() {
        this.sessionManager = SessionManager.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.contactList = this.sessionManager.getAllRosterEntriesAsContacts();
        ArrayAdapter<Contact> adapter = new ListFragmentContactsAdapter(inflater.getContext(),
                R.layout.fragment_contacts_row, this.contactList);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

}
