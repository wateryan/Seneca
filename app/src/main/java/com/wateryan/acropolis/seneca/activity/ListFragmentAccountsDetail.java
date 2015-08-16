package com.wateryan.acropolis.seneca.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wateryan.acropolis.seneca.R;
import com.wateryan.acropolis.seneca.core.database.DbController;
import com.wateryan.acropolis.seneca.core.network.SessionManager;
import com.wateryan.acropolis.seneca.core.validation.AbstractValidator;
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
        final View view = inflater.inflate(R.layout.fragment_accounts_detail, container, false);

        EditText username = ((EditText) view.findViewById(R.id.username));
        EditText password = ((EditText) view.findViewById(R.id.password));
        EditText serviceName = ((EditText) view.findViewById(R.id.service_name));
        EditText host = ((EditText) view.findViewById(R.id.host));
        EditText port = (EditText) view.findViewById(R.id.port);

        if (this.account != null) {
            getDialog().setTitle("Account Number: " + this.account.getId());

            username.setText(this.account.getUsername());
            password.setText(this.account.getPassword());
            serviceName.setText(this.account.getServiceName());
            host.setText(this.account.getHost());
            port.setText(Integer.toString(this.account.getPort()));
        }

        username.addTextChangedListener(new AbstractValidator(username) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    textView.setError("Please enter a username.");
                }
            }
        });


        password.addTextChangedListener(new AbstractValidator(password) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    textView.setError("Please enter a password.");
                }
            }
        });


        serviceName.addTextChangedListener(new AbstractValidator(serviceName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    textView.setError("Please enter a service name.");
                }
            }
        });


        host.addTextChangedListener(new AbstractValidator(host) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    textView.setError("Please enter a host.");
                }
            }
        });

        port.addTextChangedListener(new AbstractValidator(port) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    textView.setError("Please enter a port.");
                }
                try {
                    Integer.parseInt(text);
                } catch (NumberFormatException e) {
                    textView.setError("Please enter a valid port.");
                }
            }
        });

        // Save / Cancel Buttons
        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = null;
                try {
                    account = getUserAccount((View) v.getParent());
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Unable to save changes.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (DbController.getInstance(getActivity()).accountExists(account)) {
                    DbController.getInstance(getActivity()).updateUsersAccount(account);
                } else {
                    // TODO delete old user account?
                    DbController.getInstance(getActivity()).addUsersAccount(account);
                }
                Toast.makeText(getActivity(),
                        "Saved account " + (account != null ? account.getUsername() : null),
                        Toast.LENGTH_SHORT).show();
                if (!SessionManager.getInstance().hasInitializedSession(account)) {
                    SessionManager.getInstance().initializeSession(account);
                }
                getDialog().dismiss();
            }

            private Account getUserAccount(View v) {
                // TODO Input validation
                String username = ((EditText) v.findViewById(R.id.username)).getText().toString();
                String password = ((EditText) v.findViewById(R.id.password)).getText().toString();
                String service = ((EditText) v.findViewById(
                        R.id.service_name)).getText().toString();
                String host = ((EditText) v.findViewById(R.id.host)).getText().toString();
                int port = Integer.parseInt(
                        ((EditText) v.findViewById(R.id.port)).getText().toString());
                int id;
                if (getArguments() != null && getArguments().containsKey(ARG_ACCOUNT)) {
                    id = ((Account) getArguments().get(ARG_ACCOUNT)).getId();
                } else {
                    id = DbController.getInstance(getActivity()).getNextAccountId();
                }
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
