package com.wateryan.acropolis.seneca.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.wateryan.acropolis.seneca.adapter.ListFragmentAccountsAdapter;
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
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Fragment fragment = new ListFragmentAccountsDetail();
        Bundle args = new Bundle();
        args.putSerializable("account", this.accountList.get(position));
        fragment.setArguments(args);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DbController controller = DbController.getInstance(this.getActivity());
        this.accountList = controller.getUsersAccounts();
        // TODO remove with addition of Add Account button
        if (this.accountList.isEmpty()) {
            this.accountList.add(
                    new Account(1, "DefaultUser", "password", "Service", "host", 5222));
        }
        ArrayAdapter<Account> adapter = new ListFragmentAccountsAdapter(inflater.getContext(),
                R.layout.fragment_accounts_row, this.accountList);
//        ArrayAdapter<Account> adapter = new ArrayAdapter<>(inflater.getContext(),
//                android.R.layout.simple_list_item_1, this.accountList);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_accounts, container, false);
    }

}
