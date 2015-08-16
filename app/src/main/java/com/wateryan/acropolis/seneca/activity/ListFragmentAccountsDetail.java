package com.wateryan.acropolis.seneca.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.wateryan.acropolis.seneca.R;
import com.wateryan.acropolis.seneca.core.database.DbController;
import com.wateryan.acropolis.seneca.core.network.SessionManager;
import com.wateryan.acropolis.seneca.model.Account;

/**
 * Created on 8/4/2015.
 */
public class ListFragmentAccountsDetail extends DialogFragment {

    private static final String ARG_ACCOUNT = "account";
    private Account account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_ACCOUNT)) {
            account = (Account) getArguments().get(ARG_ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts_detail, container, false);
        if (this.account != null) {
            getDialog().setTitle("Account Number: " + this.account.getId());
            ((EditText) view.findViewById(R.id.username)).setText(this.account.getUsername());
            ((EditText) view.findViewById(R.id.password)).setText(this.account.getPassword());
            ((EditText) view.findViewById(R.id.service_name)).setText(
                    this.account.getServiceName());
            ((EditText) view.findViewById(R.id.host)).setText(this.account.getHost());
            ((EditText) view.findViewById(R.id.port)).setText(
                    Integer.toString(this.account.getPort()));
        }

        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = null;
                try {
                    account = getUserAccount((View) v.getParent());
                    if (DbController.getInstance(getActivity()).accountExists(account)) {
                        DbController.getInstance(getActivity()).updateUsersAccount(account);
                    } else {
                        // TODO delete old user account?
                        DbController.getInstance(getActivity()).addUsersAccount(account);
                    }
                } catch (NumberFormatException e) {
                    // TODO input should be validated and this shouldn't be here.
                    Toast.makeText(getActivity(), "Exception thrown.", Toast.LENGTH_SHORT);
                } finally {
                    Toast.makeText(getActivity(),
                            "Saved account " + (account != null ? account.getUsername() : null),
                            Toast.LENGTH_SHORT).show();
                    if (!SessionManager.getInstance().hasInitializedSession(account)) {
                        SessionManager.getInstance().initializeSession(account);
                    }
                    getDialog().dismiss();
                }

            }

            private Account getUserAccount(View v) {
                // TODO Input validation
                int id = ((Account) getArguments().get(ARG_ACCOUNT)).getId();
                String
                        username = ((EditText) v.findViewById(R.id.username)).getText().toString();
                String password = ((EditText) v.findViewById(R.id.password)).getText().toString();
                String service = ((EditText) v.findViewById(
                        R.id.service_name)).getText().toString();
                String host = ((EditText) v.findViewById(R.id.host)).getText().toString();
                int port = Integer.parseInt(
                        ((EditText) v.findViewById(R.id.port)).getText().toString());
                return new Account(id, username, password, service, host, port);
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        return view;
    }


}
