package com.wateryan.acropolis.seneca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wateryan.acropolis.seneca.adapter.AccountsListAdapter;
import com.wateryan.acropolis.seneca.core.DbController;
import com.wateryan.acropolis.seneca.model.Account;

import java.util.List;

/**
 * Created on 8/2/2015.
 */
public class ListFragmentAccounts extends ListFragment {

    private List<Account> accountList;

    public ListFragmentAccounts() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DbController controller = DbController.getInstance(this.getActivity());
        this.accountList = controller.getUsersAccounts();
        // TODO remove once real accounts can be used
        if (this.accountList.isEmpty()) {
            this.accountList.add(new Account(1, "DefaultUser", "password", "Service", "host", 5222));
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        Intent intent = new Intent();
        intent.setClass(getActivity(), AccountEditFragment.class);
        // How to pass account to details view?
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<Account> adapter = new AccountsListAdapter(inflater.getContext(), android.R.layout.simple_list_item_1, this.accountList);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
