package com.fivefire.app.gdutcontacts.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Landy on 2016/5/15.
 */
public class AutoTextAdapter implements ListAdapter,Filterable {
    Context context;
    String[] content;
    String tempKeyString;
    MyFilter myFilter;
    public AutoTextAdapter(String[] content,Context context)
    {
        super();
        this.content=content;
        this.context=context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public Filter getFilter() {
        if(null==myFilter)
        {
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    @Override
    public int getCount() {
        return content.length;
    }

    @Override
    public Object getItem(int position) {
        return content[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv =  new TextView(context);
        String temp = content[position];
        tv.setTextSize(25);
        tv.setText(Html.fromHtml("<font color='#30acce' type='bold'>"+tempKeyString+"</font>"+""+temp.substring(tempKeyString.length(),temp.length())));
        return tv;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    class MyFilter extends Filter{
        String[] strContant;
        public MyFilter()
        {
            super();
            strContant = content;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            ArrayList<String> templist = new ArrayList<>();
            if(null!=constraint&&constraint.length()>0)
            {
                for (int i =0;i<strContant.length;i++)
                {
                    String temp =  strContant[i];
                    if (constraint.toString().contains(temp.substring(0,constraint.length())))
                        templist.add(temp);
                }
                results.values=templist;
                results.count=templist.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<String> templist = (ArrayList<String>)results.values;
            if(null!=templist)
            {
                String[] strTemps = new String[templist.size()];
                for(int i =0;i<templist.size();i++)
                {
                    strTemps[i]=templist.get(i);
                }
                content=strTemps;
                tempKeyString=constraint.toString();
            }
        }
    }
}
