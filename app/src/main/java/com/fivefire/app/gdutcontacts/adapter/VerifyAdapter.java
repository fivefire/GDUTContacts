package com.fivefire.app.gdutcontacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.utils.DataOperate;

import java.util.List;

import cn.bmob.v3.listener.UpdateListener;

/**
 *
 * Created by MicroStudent on 2016/5/23.
 */
public class VerifyAdapter extends RecyclerView.Adapter<VerifyAdapter.VerifyViewHolder> {
    private static final int FEMALE = 1;
    private static final int MALE = 0;


    private Context mContext;
    private List<User> mData;

    public VerifyAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<User> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public VerifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_verify, parent, false);
        return new VerifyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VerifyViewHolder holder, int position) {
        User user = mData.get(position);
        holder.name.setText(user.getName());
        holder.sno.setText(user.getSno());
        holder.phone.setText(user.getPhone());
        holder.aName.setText(user.getAname());
        holder.sex.setImageLevel(getSexBySno(user.getSno()));

        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                User user = mData.get(position);
                user.setTag(2);
                user.update(mContext, user.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(mContext, "update successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(mContext, "update error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private int getSexBySno(String sno) {
        if (sno != null && !sno.isEmpty()) {
            if (sno.length() >= 2 && sno.charAt(1) == '2') {
                return FEMALE;
            }
        }
        return MALE;
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    class VerifyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,phone,sno, aName;
        ImageView sex;
        OnItemClickListener onItemClickListener;

        public VerifyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            phone = (TextView) itemView.findViewById(R.id.tv_phone);
            sno = (TextView) itemView.findViewById(R.id.tv_sno);
            aName = (TextView) itemView.findViewById(R.id.tv_a_name);
            sex = (ImageView) itemView.findViewById(R.id.iv);
            itemView.findViewById(R.id.bt).setOnClickListener(this);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(itemView, getLayoutPosition());
            }
        }
    }


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}