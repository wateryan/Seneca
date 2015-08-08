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
 * Created on 8/7/2015.
 */
public class ListFragmentAccountsAdapter extends ArrayAdapter<Account> {

    private Context context;
    private int layoutResourceId;
    private List<Account> accountList;

    public ListFragmentAccountsAdapter(Context context, int layoutResourceId, List<Account> accountList) {
        super(context, layoutResourceId, accountList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.accountList = accountList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        AccountHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(layoutResourceId, parent, false);

            holder = new AccountHolder();
            holder.username = (TextView) view.findViewById(R.id.username);

            view.setTag(holder);
        } else {
            holder = (AccountHolder) view.getTag();
        }

        Account account = this.accountList.get(position);
        holder.username.setText(account.getUsername());

        return view;
    }

    static class AccountHolder {
        TextView username;
    }

}
