package com.example.palyandroid.activitys.otherActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.MainActivity;
import com.example.palyandroid.adapters.weChatAdapter.WeChatListAdapter;
import com.example.palyandroid.base.BaseActivity;
import com.example.palyandroid.beans.weChatBean.WeChatListBean;
import com.example.palyandroid.presenters.wechatPresenter.WeChatListPresenter;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.views.wechatView.WeChatListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SerachActivity extends BaseActivity<WeChatListPresenter, WeChatListView> implements WeChatListView {

    @BindView(R.id.toolBar_sreach)
    Toolbar toolBar;
    @BindView(R.id.rlv_Sreach)
    RecyclerView rlv;
    @BindView(R.id.sm_search)
    SmartRefreshLayout sm;
    private int mPage = 1;
    private List<WeChatListBean.DataBean.DatasBean> list;
    private String query;
    private WeChatListAdapter weChatListAdapter;
    private int mPosition;

    @Override
    protected WeChatListPresenter initPresenter() {
        return new WeChatListPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_serach;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        query = intent.getStringExtra("query");

        // 初始化ToolBar
        initToolBar();

        // 初始化适配器
        initAdapter();

        // 加载更多
        LoadMore();

        // 点击跳转
        AdapterListener();

    }

    private void initAdapter() {
        rlv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        weChatListAdapter = new WeChatListAdapter(this, list);
        rlv.setAdapter(weChatListAdapter);
    }


    private void AdapterListener() {
        weChatListAdapter.setOnItemClickListener(new WeChatListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                mPosition = position;

                Intent intent = new Intent();
                //WebView用的数据
                intent.putExtra("link", list.get(mPosition).getLink());
                intent.putExtra("listTitle", list.get(mPosition).getTitle());
                //收藏用的数据
                intent.putExtra("anthor", list.get(mPosition).getAuthor());
                intent.putExtra("all", list.get(mPosition).getSuperChapterName());
                intent.putExtra("time", list.get(mPosition).getNiceDate());
                intent.setClass(SerachActivity.this, WebActivity.class);
                CircularAnimUtil.startActivity(SerachActivity.this, intent, rlv, R.color.org);
                ToastUtil.showShort("搜索Web");

              /*  Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("link", list.get(mPosition).getLink());
                intent.putExtra("listTitle", list.get(mPosition).getTitle());
                startActivity(intent);*/

            }
        });
    }

    @Override
    protected void initData() {

        Map<String,Object> map = new HashMap<>();
        map.put("k",query);
        mPresenter.getData(408, mPage, map);

    }

    private void LoadMore() {

        //设置 Footer 为 球脉冲
        sm.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        sm.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                initData();
                weChatListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });

        sm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                initData();
                refreshLayout.finishRefresh();
            }
        });
    }

    private void initToolBar() {
        toolBar.setTitle("");
        toolBar.setNavigationIcon(getResources().getDrawable(R.drawable.zjt));
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SerachActivity.this, MainActivity.class);
                CircularAnimUtil.startActivity(SerachActivity.this, intent, rlv, R.color.org);
                ToastUtil.showShort("退出");
            }
        });
    }

    @Override
    public void showData(WeChatListBean weChatListBean) {
        list = weChatListBean.getData().getDatas();
        if (list != null) {
            weChatListAdapter.setList(list);
        }
    }

}
