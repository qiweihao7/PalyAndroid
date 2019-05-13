package com.example.palyandroid.fragments.safariFragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.MainActivity;
import com.example.palyandroid.activitys.otherActivity.WebActivity;
import com.example.palyandroid.adapters.safariAdapter.SafariAdapter;
import com.example.palyandroid.base.BaseFragment;
import com.example.palyandroid.beans.safariBean.SafariBean;
import com.example.palyandroid.net.MyServer;
import com.example.palyandroid.presenters.safariPresenter.SafariPresenter;
import com.example.palyandroid.utils.BaseObserver;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.HttpUtils;
import com.example.palyandroid.utils.RxUtils;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.views.safariView.SafariView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SafariFragment extends BaseFragment<SafariPresenter,SafariView> implements SafariView{

    @BindView(R.id.tab)
    VerticalTabLayout tab;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    private SafariAdapter mRecyclerAdapter;
    private boolean isScroll;
    private List<SafariBean.DataBean> list;
    private int mPosition;

    @Override
    protected SafariPresenter initPresenter() {
        return new SafariPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_safari;
    }

    @Override
    protected void initData() {
        Map<String, Object> map = new HashMap<>();
        mPresenter.getData("navi/json", map);
    }

    @Override
    protected void initView() {
        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        mRecyclerAdapter = new SafariAdapter(list, getContext());
        rlv.setAdapter(mRecyclerAdapter);

        tab.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) rlv.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(position, 0);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //重写该方法主要是判断recyclerview是否在滑动
                //0停止 ，1,2都是滑动
                if (newState == 0) {
                    isScroll = false;
                } else {
                    isScroll = true;
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) rlv.getLayoutManager();
                //可见的第一条条目
                int top = layoutManager.findFirstVisibleItemPosition();
                tab.setTabSelected(top);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //这个主要是recyclerview滑动时让tab定位的方法

                                               /*recyclerView : 当前滚动的view
                                dx : 水平滚动距离
                                dy : 垂直滚动距离
                                dx > 0 时为手指向左滚动,列表滚动显示右面的内容
                                dx < 0 时为手指向右滚动,列表滚动显示左面的内容
                                dy > 0 时为手指向上滚动,列表滚动显示下面的内容
                                dy < 0 时为手指向下滚动,列表滚动显示上面的内容*/
                LinearLayoutManager layoutManager = (LinearLayoutManager) rlv.getLayoutManager();
                //可见的第一条条目
                int top = layoutManager.findFirstVisibleItemPosition();
                //可见的最后一条条目
                int bottom = layoutManager.findLastVisibleItemPosition();
                if (isScroll) {
                    if (dy > 0) {
                        tab.setTabSelected(top);
                    }
                }
            }
        });

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

    @Override
    public void showData(SafariBean safariBean) {
        list = safariBean.getData();
        if (list != null) {
            mRecyclerAdapter.setAList(list);
            //tab栏的适配器
            tab.setTabAdapter(new TabAdapter() {
                @Override
                public int getCount() {
                    return list.size();
                }

                @Override
                public ITabView.TabBadge getBadge(int position) {
                    return null;
                }

                @Override
                public ITabView.TabIcon getIcon(int position) {
                    return null;
                }

                @Override
                public ITabView.TabTitle getTitle(int position) {
                    return new TabView.TabTitle.Builder()
                            .setContent(list.get(position).getName())
                            .build();
                }

                @Override
                public int getBackground(int position) {
                    return 0;
                }
            });
        }

    }

}