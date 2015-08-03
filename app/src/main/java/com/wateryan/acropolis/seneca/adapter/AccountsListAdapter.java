package com.wateryan.acropolis.seneca.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wateryan.acropolis.seneca.R;
import com.wateryan.acropolis.seneca.model.Account;

import java.util.List;

/**
 * Created on 8/2/2015.
 */
public class AccountsListAdapter extends ArrayAdapter<Account> {
    public AccountsListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public AccountsListAdapter(Context context, int resource, List<Account> accountList) {
        super(context, resource, accountList);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        final Account account = getItem(position);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.fragment_accounts_row, null);
        }

        if (account != null) {
            TextView serviceName = (TextView) view.findViewById(R.id.account_service);
            if (serviceName != null) {
                serviceName.setText(account.getServiceName());
            }
        }
        return view;
    }
}
