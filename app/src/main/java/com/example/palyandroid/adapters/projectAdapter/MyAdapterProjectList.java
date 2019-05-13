package com.example.palyandroid.adapters.projectAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.palyandroid.R;
import com.example.palyandroid.beans.projectBean.ProjectListBean;
import com.example.palyandroid.net.Constants;
import com.example.palyandroid.utils.SpUtil;

import java.util.List;

/**
 * Created by 走马 on 2019/4/28.
 */

public class MyAdapterProjectList extends RecyclerView.Adapter{
    private final Context mContext;
    private List<ProjectListBean.DataBean.DatasBean> mList;
    private OnItemClickListener mListener;

    public MyAdapterProjectList(Context context, List<ProjectListBean.DataBean.DatasBean> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_project, null, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ViewHolder holder1 = (ViewHolder) holder;
        holder1.title.setText(mList.get(position).getTitle());
        holder1.info.setText(mList.get(position).getDesc());
        holder1.anthor.setText(mList.get(position).getAuthor());
        holder1.time.setText(mList.get(position).getNiceDate());

        //赋值
        Boolean isPicture = (Boolean) SpUtil.getParam(Constants.PICTOR, false);
        if (isPicture) {
            Glide.with(mContext).load(R.mipmap.ic_launcher).into(holder1.img);
        } else {
            Glide.with(mContext).load(mList.get(position).getEnvelopePic()).into(holder1.img);
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
        return mList.size();
    }

    public void setList(List<ProjectListBean.DataBean.DatasBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView info;
        private final TextView title;
        private final TextView anthor;
        private final TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_project);
            title = itemView.findViewById(R.id.title_project);
            info = itemView.findViewById(R.id.info_project);
            anthor = itemView.findViewById(R.id.anthor_project);
            time = itemView.findViewById(R.id.time_project);

        }
    }

     public interface OnItemClickListener{
             void OnItemClick(int position);
     }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

}
