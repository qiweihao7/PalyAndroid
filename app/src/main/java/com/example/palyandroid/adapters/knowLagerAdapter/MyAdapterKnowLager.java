package com.example.palyandroid.adapters.knowLagerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.palyandroid.R;
import com.example.palyandroid.beans.knowlagerBean.KnowLagerBean;

import java.util.List;

/**
 * Created by 走马 on 2019/5/4.
 */

public class MyAdapterKnowLager extends RecyclerView.Adapter{
    private final Context context;
    private List<KnowLagerBean.DataBean> list;
    private OnItemClickListener mListener;

    public MyAdapterKnowLager(Context context, List<KnowLagerBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.item_knowlager, null);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ViewHolder holder1 = (ViewHolder) holder;

        List<KnowLagerBean.DataBean.ChildrenBean> childrenList = list.get(position).getChildren();

        StringBuilder stringBuilder = new StringBuilder();

        for (KnowLagerBean.DataBean.ChildrenBean bean:childrenList) {
            stringBuilder.append(bean.getName()).append("     ");
        }
        holder1.info.setText(stringBuilder.toString());

        holder1.title.setText(list.get(position).getName());
        if (position % 4 == 0) {
            holder1.title.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else if (position % 4 == 1) {
            holder1.title.setTextColor(ContextCompat.getColor(context, R.color.zi));
        } else if (position % 4 == 2) {
            holder1.title.setTextColor(ContextCompat.getColor(context, R.color.org));
        } else if (position % 4 == 3) {
            holder1.title.setTextColor(ContextCompat.getColor(context, R.color.bule));
        }

        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<KnowLagerBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView info;
        private final TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_knowlager);
            info = itemView.findViewById(R.id.info_knowlager);
        }
    }

     public interface OnItemClickListener{
             void OnItemClick(int position);
     }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

}
