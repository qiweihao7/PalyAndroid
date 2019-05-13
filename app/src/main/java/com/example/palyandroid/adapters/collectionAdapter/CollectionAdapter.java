package com.example.palyandroid.adapters.collectionAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.palyandroid.R;
import com.example.palyandroid.net.Constants;
import com.example.palyandroid.sql.DaoUtils;
import com.example.palyandroid.sql.MyDb;
import com.example.palyandroid.utils.SpUtil;
import com.example.palyandroid.utils.ToastUtil;

import java.util.List;

/**
 * Created by 走马 on 2019/5/8.
 */

public class CollectionAdapter extends RecyclerView.Adapter{
    private final Context context;
    private final List<MyDb> select;
    private OnItemClickListener mListener;
    private OnLongItemClickListener mLonglistener;
    private boolean isCheck;

    public CollectionAdapter(Context context, List<MyDb> select) {

        this.context = context;
        this.select = select;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_collection, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder holder1 = (ViewHolder) holder;
        MyDb myDb = select.get(position);
        //作者
        holder1.anthor.setText(myDb.getAnthor());
        holder1.all.setTextColor(context.getResources().getColor(R.color.bule));
        holder1.all.setText(myDb.getAnthor()+"/"+myDb.getAll());
        holder1.info.setText(myDb.getTitle());
        holder1.time.setText(myDb.getTime());

        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnItemClick(position);
            }
        });


       /* holder1.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mLonglistener.OnLongItemClick(position);
                return false;
            }
        });*/

        holder1.follow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DaoUtils.getDaoUtils().delete(select.get(position).getId());
                select.remove(position);
                notifyDataSetChanged();
                SpUtil.setParam(Constants.FOLLOW,false);
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return select.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView time;
        private final TextView info;
        private final TextView all;
        private final TextView anthor;
        private final ImageView news;
        private final ImageView follow;

        public ViewHolder(View itemView) {
            super(itemView);

            follow = itemView.findViewById(R.id.img_home_follow_collection);

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

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public interface OnLongItemClickListener {
        void OnLongItemClick(int position);
    }

    public void setOnLongItemClickListener(OnLongItemClickListener Longlistener) {
        this.mLonglistener = Longlistener;
    }

}
