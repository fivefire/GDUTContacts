package com.fivefire.app.gdutcontacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fivefire.app.gdutcontacts.R;

/**
 *
 * Created by MicroStudent on 2016/5/23.
 */
public class VerifyAdapter extends RecyclerView.Adapter<VerifyAdapter.VerifyViewHolder> {
    private Context mContext;

    public VerifyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public VerifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_verify, parent, false);
        return new VerifyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VerifyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class VerifyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,phone,sno, sClass;
        ImageView sex;
        OnItemClickListener onItemClickListener;

        public VerifyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            phone = (TextView) itemView.findViewById(R.id.tv_phone);
            sno = (TextView) itemView.findViewById(R.id.tv_sno);
            sClass = (TextView) itemView.findViewById(R.id.tv_class);
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

    interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
