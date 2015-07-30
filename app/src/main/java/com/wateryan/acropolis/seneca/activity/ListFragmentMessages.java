package com.wateryan.acropolis.seneca.activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created on 7/29/2015.
 */
public class ListFragmentMessages extends ListFragment {

    private final String[] items = new String[]{"1", "2", "3"};

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Toast.makeText(getActivity().getApplicationContext(), items[(int) id], Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
