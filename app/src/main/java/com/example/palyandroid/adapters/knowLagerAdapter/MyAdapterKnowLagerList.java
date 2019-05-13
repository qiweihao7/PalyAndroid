package com.example.palyandroid.adapters.knowLagerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.palyandroid.R;
import com.example.palyandroid.adapters.homeAdapter.MyAdapterHome;
import com.example.palyandroid.beans.knowlagerBean.KnowLagerListBean;

import java.util.List;

/**
 * Created by 走马 on 2019/5/4.
 */

public class MyAdapterKnowLagerList extends RecyclerView.Adapter{
    private final Context context;
    private List<KnowLagerListBean.DataBean.DatasBean> list;
    private MyAdapterHome.OnItemClickListener mListener;

    public MyAdapterKnowLagerList(Context context, List<KnowLagerListBean.DataBean.DatasBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.item_knowlager_list, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ViewHolder holder1 = (ViewHolder) holder;
        holder1.anthor.setText(list.get(position).getAuthor());
        holder1.all.setText(list.get(position).getSuperChapterName()+"/"+list.get(position).getChapterName());
        holder1.info.setText(list.get(position).getTitle());
        holder1.time.setText(list.get(position).getNiceDate());
        //是否为最新文章
        if (list.get(position).isFresh()){
            holder1.news.setVisibility(View.VISIBLE);
        }else{
            holder1.news.setVisibility(View.GONE);
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

    public void setList(List<KnowLagerListBean.DataBean.DatasBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView time;
        private final TextView info;
        private final TextView all;
        private final TextView anthor;
        private final ImageView news;

        public ViewHolder(View itemView) {
            super(itemView);
            anthor = itemView.findViewById(R.id.anthor_home);
            all = itemView.findViewById(R.id.all_home);
            info = itemView.findViewById(R.id.info_home);
            time = itemView.findViewById(R.id.time_home);
            news = itemView.findViewById(R.id.news_home);
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(MyAdapterHome.OnItemClickListener listener){
        this.mListener = listener;
    }

}
