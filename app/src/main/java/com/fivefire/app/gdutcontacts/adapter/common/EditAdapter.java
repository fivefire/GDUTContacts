package com.fivefire.app.gdutcontacts.adapter.common;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by MicroStudent on 2016/5/19.
 */
public abstract class EditAdapter<E> extends RecyclerView.Adapter {

    protected List<E> mData;

    public void animateTo(List<E> data) {
        if (data != null) {
            applyAndAnimateRemovals(data);
            applyAndAnimateAdditions(data);
            applyAndAnimateMovedItems(data);
        }
    }


    public E removeItem(int position) {
        final E city = mData.remove(position);
        notifyItemRemoved(position);
        return city;
    }

    public void addItem(int position, E city) {
        mData.add(position, city);
        notifyItemInserted(position);
    }

    public void moveItem(int from, int to) {
        final E city = mData.remove(from);
        mData.add(to, city);
        notifyItemMoved(from, to);
    }


    protected void applyAndAnimateMovedItems(List<E> data) {
        for (int toPosition = data.size() - 1; toPosition >= 0; toPosition--) {
            final E city = data.get(toPosition);
            final int fromPosition = mData.indexOf(city);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    protected void applyAndAnimateAdditions(List<E> data) {
        for (int i = 0, count = data.size(); i < count; i++) {
            final E city = data.get(i);
            if (!mData.contains(city)) {
                addItem(i, city);
            }
        }
    }

    protected void applyAndAnimateRemovals(List<E> data) {
        for (int i = mData.size() - 1; i >= 0; i--) {
            final E city = mData.get(i);
            if (!data.contains(city)) {
                removeItem(i);
            }
        }
    }

}
