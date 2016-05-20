package com.fivefire.app.gdutcontacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.User;

import java.util.List;

/**
 * 能动态刷新数据的Adapter
 * Created by MicroStudent on 2016/5/20.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>{
    private List<User> mData;
    private LayoutInflater mInflater;

    public ContactsAdapter(Context context, List<User> mData) {
        mInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_contacts, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
        holder.phone.setText(mData.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void animateTo(List<User> data) {
        if (data != null) {
            applyAndAnimateRemovals(data);
            applyAndAnimateAdditions(data);
            applyAndAnimateMovedItems(data);
        }
    }


    public User removeItem(int position) {
        final User user = mData.remove(position);
        notifyItemRemoved(position);
        return user;
    }

    public void addItem(int position, User user) {
        mData.add(position, user);
        notifyItemInserted(position);
    }

    public void moveItem(int from, int to) {
        final User user = mData.remove(from);
        mData.add(to, user);
        notifyItemMoved(from, to);
    }


    protected void applyAndAnimateMovedItems(List<User> data) {
        for (int toPosition = data.size() - 1; toPosition >= 0; toPosition--) {
            final User user = data.get(toPosition);
            final int fromPosition = mData.indexOf(user);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    protected void applyAndAnimateAdditions(List<User> data) {
        for (int i = 0, count = data.size(); i < count; i++) {
            final User user = data.get(i);
            if (!mData.contains(user)) {
                addItem(i, user);
            }
        }
    }

    protected void applyAndAnimateRemovals(List<User> data) {
        for (int i = mData.size() - 1; i >= 0; i--) {
            final User user = mData.get(i);
            if (!data.contains(user)) {
                removeItem(i);
            }
        }
    }


    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone;
        public ContactsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            phone = (TextView) itemView.findViewById(R.id.tv_phone);
        }
    }
}
