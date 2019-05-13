package com.example.palyandroid.fragments.homeFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.MainActivity;
import com.example.palyandroid.activitys.otherActivity.WebActivity;
import com.example.palyandroid.adapters.homeAdapter.MyAdapterHome;
import com.example.palyandroid.base.BaseFragment;
import com.example.palyandroid.beans.homeBean.AlertListBean;
import com.example.palyandroid.beans.homeBean.BannerBean;
import com.example.palyandroid.presenters.homePresenter.HomePresenter;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.views.homeView.HomeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<HomePresenter, HomeView> implements HomeView {

    @BindView(R.id.rlv_HOME)
    RecyclerView rlv;
    @BindView(R.id.sm_home)
    SmartRefreshLayout sm;
    private List<AlertListBean.DataBean.DatasBean> alertList;
    private List<BannerBean.DataBean> bannerList;
    private MyAdapterHome myAdapterHome;
    private int mPage = 0;
    private int mPosition;


    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        alertList = new ArrayList<>();
        bannerList = new ArrayList<>();
        myAdapterHome = new MyAdapterHome(getContext(), alertList, bannerList);
        rlv.setAdapter(myAdapterHome);

        myAdapterHome.setOnItemClickListener(new MyAdapterHome.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                mPosition = position;

                //  holder2.anthor.setText(datasBean.getAuthor());
                // holder2.all.setTextColor(mContext.getResources().getColor(R.color.bule));
               // holder2.all.setText(datasBean.getAuthor()+"/"+datasBean.getSuperChapterName());
                 //holder2.info.setText(datasBean.getTitle());
                 //holder2.time.setText(datasBean.getNiceDate());


                Intent intent = new Intent();
                //WebView用的数据
                //WebView用的数据
                intent.putExtra("link", alertList.get(mPosition).getLink());
                intent.putExtra("listTitle", alertList.get(mPosition).getTitle());
                //收藏用的数据
                intent.putExtra("anthor", alertList.get(mPosition).getAuthor());
                intent.putExtra("all", alertList.get(mPosition).getSuperChapterName());
                intent.putExtra("time", alertList.get(mPosition).getNiceDate());
                intent.setClass(getActivity(), WebActivity.class);
                ToastUtil.showShort("主界面Web");
                CircularAnimUtil.startActivity(getActivity(), intent, rlv, R.color.org);

               /* Intent intent = new Intent(getActivity(), WebActivity.class);
                startActivity(intent);*/
            }
        });


        LoadMore();

        HideTablayout_Float();

    }

    private void HideTablayout_Float() {

        final MainActivity mainActivity = (MainActivity) getActivity();
        //滑动recyclerView隐藏tablayout
        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (firstVisibleItem != 0) {
                    if (dy > 15 ) {
                        mainActivity.getFab().setVisibility(View.GONE);
                        mainActivity.getTabLayout().setVisibility(View.GONE);
                    } else if (dy < -15 ) {
                        mainActivity.getFab().setVisibility(View.VISIBLE);
                        mainActivity.getTabLayout().setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //点击悬浮按钮回到底部
        mainActivity.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlv.smoothScrollToPosition(0);
            }
        });

    }

    private void LoadMore() {

        sm.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                initData();
                myAdapterHome.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }

        });

        sm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                initData();
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    protected void initData() {
        Map<String, Object> map = new HashMap<>();
        mPresenter.getAlertData(mPage, map);
        mPresenter.getBannerData("banner/json", map);
    }

    @Override
    public void setAlertBean(AlertListBean alertListBean) {
        alertList = alertListBean.getData().getDatas();
        if (alertList != null && alertList.size() > 0) {
            myAdapterHome.setAlertList(alertList);
        }
    }

    @Override
    public void setBannerBean(BannerBean bannerBean) {
        bannerList = bannerBean.getData();
        if (bannerList != null && bannerList.size() > 0) {
            myAdapterHome.setBannerList(bannerList);
        }
    }

}
