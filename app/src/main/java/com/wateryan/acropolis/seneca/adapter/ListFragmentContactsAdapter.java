package com.wateryan.acropolis.seneca.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wateryan.acropolis.seneca.R;
import com.wateryan.acropolis.seneca.core.SessionManager;
import com.wateryan.acropolis.seneca.model.Contact;

import org.jivesoftware.smack.packet.Presence;

import java.util.List;

/**
 * Created on 8/8/2015.
 */
public class ListFragmentContactsAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int layoutResourceId;
    private List<Contact> contactList;

    public ListFragmentContactsAdapter(Context context, int layoutResourceId, List<Contact> contactList) {
        super(context, layoutResourceId, contactList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.contactList = contactList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ContactHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(layoutResourceId, parent, false);

            holder = new ContactHolder();
            holder.contact = (TextView) view.findViewById(R.id.contact);
            holder.status = (TextView) view.findViewById(R.id.status);
            holder.presence = (FrameLayout) view.findViewById(R.id.presence);

            view.setTag(holder);
        } else {
            holder = (ContactHolder) view.getTag();
        }

        Contact contact = this.contactList.get(position);
        String user = contact.getEntry().getUser();
        holder.contact.setText(user);
        Presence presence = SessionManager.getInstance().getPresenseOfUser(user);
        holder.status.setText(getPresenceStatus(presence));
        holder.presence.setBackgroundColor(getPresenceColor(presence));

        return view;
    }

    private String getPresenceStatus(Presence presence) {
        String status = "Unavailable";
        if (isAvailable(presence)) {
            status = presence.getStatus();
        }
        return status;
    }

    private boolean isAvailable(Presence presence) {
        return presence.getType() == Presence.Type.available;
    }

    private int getPresenceColor(Presence presence) {
        int color = Color.DKGRAY; // default
        if (isAvailable(presence)) {
            switch (presence.getMode()) {
                case available:
                    color = Color.GREEN;
                    break;
                case chat:
                    color = Color.GREEN;
                    break;
                case away:
                    color = Color.YELLOW;
                    break;
                case xa:
                    color = Color.YELLOW;
                    break;
                case dnd:
                    color = Color.RED;
                    break;
            }
        }
        return color;
    }

    static class ContactHolder {
        FrameLayout presence;
        TextView status;
        TextView contact;
    }

}
