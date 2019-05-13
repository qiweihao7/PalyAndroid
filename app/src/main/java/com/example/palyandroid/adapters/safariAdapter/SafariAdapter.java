package com.example.palyandroid.adapters.safariAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.otherActivity.WebActivity;
import com.example.palyandroid.beans.safariBean.SafariBean;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.FlowLayout;
import com.example.palyandroid.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SafariAdapter extends RecyclerView.Adapter {
    public List<SafariBean.DataBean> list;
    private Context context;

    public SafariAdapter(List<SafariBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.navigationitem, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final int num = i;
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv.setText(list.get(i).getName());

        holder.tabflowlayout.removeAllViews();

        ArrayList<String> title = new ArrayList<>();
        for (int j = 0; j < list.get(i).getArticles().size(); j++) {
            String title1 = list.get(i).getArticles().get(j).getTitle();
            title.add(title1);

            if (title != null) {
                View inflate = LayoutInflater.from(context).inflate(R.layout.tag_textview, null);
                TextView tv = inflate.findViewById(R.id.tag_textview);

                tv.setText(title1);
                String[] colors = {"#8A1D46", "#00005A", "#50821D", "#4A1744", "#884E16", "#035892"};
                Random random = new Random();
                int c = random.nextInt(6);
                tv.setTextColor(Color.parseColor(colors[c]));

                int finalJ = j;
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        //WebView用的数据
                        intent.putExtra("link", list.get(i).getArticles().get(finalJ).getLink());
                        intent.putExtra("listTitle", list.get(i).getArticles().get(finalJ).getTitle());
                        //收藏用的数据
                        intent.putExtra("anthor", list.get(i).getArticles().get(finalJ).getAuthor());
                        intent.putExtra("all", list.get(i).getArticles().get(finalJ).getSuperChapterName());
                        intent.putExtra("time", list.get(i).getArticles().get(finalJ).getNiceDate());
                        intent.setClass(context, WebActivity.class);
                        CircularAnimUtil.startActivity((Activity) context, intent,holder.tv, R.color.org);
                        ToastUtil.showShort("导航Web");

                    }
                });

                holder.tabflowlayout.addView(tv);

            }
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setAList(List<SafariBean.DataBean> AList) {
        this.list = AList;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_qqq)
        TextView tv;
        @BindView(R.id.tabflowlayout)
        FlowLayout tabflowlayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

