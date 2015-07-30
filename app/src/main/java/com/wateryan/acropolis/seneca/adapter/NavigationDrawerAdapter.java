package com.wateryan.acropolis.seneca.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wateryan.acropolis.seneca.R;
import com.wateryan.acropolis.seneca.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created on 7/26/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NavigationViewHolder> {
    List<NavDrawerItem> items = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> items) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    public void delete(int position) {
        this.items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public NavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = this.inflater.inflate(R.layout.nav_drawer_row, parent, false);
        return new NavigationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NavigationViewHolder holder, int position) {
        NavDrawerItem item = this.items.get(position);
        holder.title.setText(item.getTitle());
    }

    public int getItemCount() {
        return this.items.size();
    }


    class NavigationViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public NavigationViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }


    }

}
