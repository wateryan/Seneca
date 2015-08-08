package com.wateryan.acropolis.seneca.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wateryan.acropolis.seneca.model.Contact;

/**
 * Created on 8/7/2015.
 */
public class ListFragmentContactsDetail extends Fragment {

    protected static final String ARG_CONTACT = "contact";
    private Contact contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_CONTACT)) {
            this.contact = (Contact) getArguments().get(ARG_CONTACT);
        }
    }


}
