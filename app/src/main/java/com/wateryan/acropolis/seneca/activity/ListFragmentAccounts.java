package com.wateryan.acropolis.seneca.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wateryan.acropolis.seneca.R;
import com.wateryan.acropolis.seneca.adapter.ListFragmentAccountsAdapter;
import com.wateryan.acropolis.seneca.core.database.DbController;
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
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Fragment fragment = new ListFragmentAccountsDetail();
        Bundle args = new Bundle();
        args.putSerializable("account", this.accountList.get(position));
        fragment.setArguments(args);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ListFragmentAccountsDetail detail = new ListFragmentAccountsDetail();
        detail.setArguments(args);
        detail.show(fragmentManager, "fragment_accounts_detail");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DbController controller = DbController.getInstance(this.getActivity());
        // TODO execute with asynctask. Not a big deal though as query is simple
        this.accountList = controller.getUsersAccounts();
        // TODO remove with addition of Add Account button
        if (this.accountList.isEmpty()) {
            this.accountList.add(
                    new Account(1, "DefaultUser", "password", "Service", "host", 5222));
        }
        ArrayAdapter<Account> adapter = new ListFragmentAccountsAdapter(inflater.getContext(),
                R.layout.fragment_accounts_row, this.accountList);
        setListAdapter(adapter);
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        view.findViewById(R.id.add_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        ListFragmentAccountsDetail detail = new ListFragmentAccountsDetail();
                        detail.show(fragmentManager, "fragment_accounts_detail");
                    }
                });
        return view;
    }

}
