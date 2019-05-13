package com.example.palyandroid.adapters.homeAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.palyandroid.R;
import com.example.palyandroid.activitys.otherActivity.WebActivity;
import com.example.palyandroid.beans.homeBean.AlertListBean;
import com.example.palyandroid.beans.homeBean.BannerBean;
import com.example.palyandroid.net.Constants;
import com.example.palyandroid.sql.DaoUtils;
import com.example.palyandroid.sql.MyDb;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.SpUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 走马 on 2019/4/28.
 */

public class MyAdapterHome extends RecyclerView.Adapter {
    private final Context mContext;
    private List<AlertListBean.DataBean.DatasBean> mArticleList;
    private List<BannerBean.DataBean> mBannerList;
    private int oldPosition;
    private OnItemClickListener mListener;
    private boolean isChecked;
    private int selectPosition;

    public MyAdapterHome(Context context, List<AlertListBean.DataBean.DatasBean> alertList, List<BannerBean.DataBean> bannerList) {

        this.mContext = context;
        this.mArticleList = alertList;
        this.mBannerList = bannerList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1) {

            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_banner_home, null, false);
            return new BannerViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_alertlist_home, null, false);
            return new ArticleViewHolder(inflate);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == 1) {
            final BannerViewHolder holder1 = (BannerViewHolder) holder;

            // 设置banner动画效果
            holder1.lunbo.setBannerAnimation(Transformer.DepthPage);
            // 设置轮播时间
            holder1.lunbo.setDelayTime(2000);
            // 设置自动轮播，默认为true
            holder1.lunbo.isAutoPlay(true);
            // 设置指示器位置
            holder1.lunbo.setIndicatorGravity(BannerConfig.CENTER);
            ArrayList<String> titleNum = new ArrayList<>();
            for (int i = 0; i < mBannerList.size(); i++) {
                titleNum.add(mBannerList.get(i).getTitle());
            }
            // 设置指示器风格
            holder1.lunbo.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
            // 设置数量
            holder1.lunbo.setBannerTitles(titleNum);
            // 设置图片集合
            holder1.lunbo.setImages(mBannerList);
            // 设置图片加载器
            holder1.lunbo.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    BannerBean.DataBean bean = (BannerBean.DataBean) path;
                    //赋值
                    Boolean isPicture = (Boolean) SpUtil.getParam(Constants.PICTOR, false);
                    if (isPicture) {
                        Glide.with(mContext).load(R.mipmap.ic_launcher).into(imageView);
                    } else {
                        Glide.with(mContext).load(bean.getImagePath()).into(imageView);
                    }
                }
            });
            // 轮播图点击事件
            holder1.lunbo.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {

                    Intent intent = new Intent();
                    //WebView用的数据
                    intent.putExtra("link", mBannerList.get(position).getUrl());
                    intent.putExtra("listTitle", mBannerList.get(position).getTitle());
                    //收藏用的数据
                    intent.putExtra("anthor", mBannerList.get(position).getDesc());
                    intent.putExtra("all", mBannerList.get(position).getTitle());
                    intent.putExtra("time", mBannerList.get(position).getId());

                    intent.setClass(mContext, WebActivity.class);
                    CircularAnimUtil.startActivity((Activity) mContext, intent, holder1.lunbo, R.color.org);
                }
            });

            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < mBannerList.size(); i++) {
                list.add(mBannerList.get(i).getTitle());
            }
            // 设置标题集合（当banner样式有显示title时）
            holder1.lunbo.setBannerTitles(list);
            // 开启轮播
            holder1.lunbo.start();

        } else {
            //因为Position = 0的位置被banner取代了。所以，这边索引是从1开始的
            int newPosition = position;
            if (mBannerList.size() > 0) {
                newPosition = position - 1;
            }
            AlertListBean.DataBean.DatasBean datasBean = mArticleList.get(newPosition);
            ArticleViewHolder holder2 = (ArticleViewHolder) holder;
            //作者
            holder2.anthor.setText(datasBean.getAuthor());
            holder2.all.setTextColor(mContext.getResources().getColor(R.color.bule));
            holder2.all.setText(datasBean.getAuthor() + "/" + datasBean.getSuperChapterName());
            holder2.info.setText(datasBean.getTitle());
            holder2.time.setText(datasBean.getNiceDate());
            //是否为最新文章
            if (datasBean.isFresh()) {
                holder2.news.setVisibility(View.VISIBLE);
            } else {
                holder2.news.setVisibility(View.GONE);
            }

            final int finalNewPosition = newPosition;

            holder2.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.OnItemClick(finalNewPosition);
                    }
                }
            });

           /*
            if (followSelect.size() > 0) {
                if (noIsCheck && followSelect.get(0).getTitle().equals(datasBean.getTitle())) {
                    holder2.follow.setImageResource(R.drawable.follow);
                } else if (!noIsCheck){
                    holder2.follow.setImageResource(R.drawable.follow_unselected);
                }
            }*/

            List<MyDb> followSelect = DaoUtils.getDaoUtils().select();
            boolean noIsCheck = (boolean) SpUtil.getParam(Constants.FOLLOW, false);
            int selectSize = followSelect.size();
            if (selectSize > 0) {
                selectPosition = 0;
                for (int i = 0; i < selectSize; i++) {
                    if (noIsCheck && followSelect.get(selectPosition).getTitle().equals(datasBean.getTitle())) {
                        holder2.follow.setImageResource(R.drawable.follow);
                    } else if (!noIsCheck && !followSelect.get(selectPosition).getTitle().equals(datasBean.getTitle())) {
                        holder2.follow.setImageResource(R.drawable.follow_unselected);
                    }
                    selectPosition++;
//                  the selectPosition <= selectSize;
                }
            }


            holder2.follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpUtil.setParam(Constants.FOLLOW, true);
                    MyDb myDb = new MyDb(null, datasBean.getLink(), datasBean.getTitle(), datasBean.getAuthor(), datasBean.getSuperChapterName(), datasBean.getNiceDate());
                    DaoUtils.getDaoUtils().insert_one(myDb);
                    holder2.follow.setImageResource(R.drawable.follow);
                }
            });


            /*holder2.follow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // img_home_follow
                    // 如果被点击了 变为红色
                    SpUtil.setParam(Constants.FOLLOW,true);
                    MyDb myDb = new MyDb(null, datasBean.getLink(), datasBean.getTitle(), datasBean.getAuthor(), datasBean.getSuperChapterName(), datasBean.getNiceDate());
                    DaoUtils.getDaoUtils().insert_one(myDb);
                    holder2.follow.setImageResource(R.drawable.follow);
                    return false;
                }
            });*/


            /*List<MyDb> select = DaoUtils.getDaoUtils().select();
            String anthor = select.get(0).getAnthor();
            if (anthor.equals(datasBean.getAuthor())) {
                holder2.follow.setImageResource(R.drawable.follow);
            }else {
                holder2.follow.setImageResource(R.drawable.follow_unselected);
            }

            boolean followF = (boolean) SpUtil.getParam(Constants.FOLLOW, false);
            if (!anthor.equals(datasBean.getAuthor()) && followF) {
                holder2.follow.setImageResource(R.drawable.follow_unselected);
            }*/

          /*  boolean isChecked = (boolean) SpUtil.getParam(Constants.FOLLOW, true);
            List<MyDb> select = DaoUtils.getDaoUtils().select();
            String anthor = select.get(finalNewPosition).getAnthor();

            String author = datasBean.getAuthor();
            if (isChecked && anthor.equals(author)){
                holder2.follow.setImageResource(R.drawable.follow);
            }else {
                holder2.follow.setImageResource(R.drawable.follow_unselected);
            }*/

        }

    }


    @Override
    public int getItemCount() {
        if (mBannerList.size() > 0) {
            return mArticleList.size() + 1;
        } else {
            return mArticleList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mBannerList.size() > 0) {
            return 1;
        } else {
            return 2;
        }
    }

    public void setAlertList(List<AlertListBean.DataBean.DatasBean> alertList) {
        this.mArticleList = alertList;
        notifyDataSetChanged();
    }

    public void setBannerList(List<BannerBean.DataBean> bannerList) {
        this.mBannerList = bannerList;
        notifyDataSetChanged();
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        private final Banner lunbo;

        public BannerViewHolder(View itemView) {
            super(itemView);

            lunbo = itemView.findViewById(R.id.banner);

        }
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        private final TextView time;
        private final TextView info;
        private final TextView all;
        private final TextView anthor;
        private final ImageView news;
        private final ImageView follow;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            follow = itemView.findViewById(R.id.img_home_follow);

            anthor = itemView.findViewById(R.id.anthor_home);
            all = itemView.findViewById(R.id.all_home);
            info = itemView.findViewById(R.id.info_home);
            time = itemView.findViewById(R.id.time_home);
            news = itemView.findViewById(R.id.news_home);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

}
