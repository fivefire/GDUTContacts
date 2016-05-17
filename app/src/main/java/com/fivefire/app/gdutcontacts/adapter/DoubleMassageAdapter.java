package com.fivefire.app.gdutcontacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fivefire.app.gdutcontacts.Model.User;
import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.DoubleViewHold;

import java.util.List;
import java.util.Map;

/**
 * Created by Landy on 2016/5/17.
 */
public class DoubleMassageAdapter extends BaseAdapter{
    private Context context;
    private List<User> Userlist;
    String Name,Phone;
    public DoubleMassageAdapter(Context context,List<User> Userlist)
    {
        super();
        this.context=context;
        this.Userlist=Userlist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User item = Userlist.get(position);
        DoubleViewHold doubleViewHold = null;
        if (convertView==null)
        {
            doubleViewHold =new DoubleViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.double_name_list,null);
            doubleViewHold.Name=(TextView)convertView.findViewById(R.id.DoubleNameList_Name);
            doubleViewHold.Phone=(TextView)convertView.findViewById(R.id.DoubleNameList_Phone);
            convertView.setTag(doubleViewHold);
        }
        else
        {
            doubleViewHold = (DoubleViewHold)convertView.getTag();
        }
        doubleViewHold.Name.setText(item.getName());
        doubleViewHold.Phone.setText(item.getPhone());
        Name=doubleViewHold.Name.getText().toString();
        Phone=doubleViewHold.Phone.getText().toString();
        return convertView;
    }

    @Override
    public int getCount() {
        return Userlist.size();
    }

    @Override
    public Object getItem(int position) {
        return Userlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
