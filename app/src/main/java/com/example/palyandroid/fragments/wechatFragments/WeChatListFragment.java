package com.example.palyandroid.fragments.wechatFragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.MainActivity;
import com.example.palyandroid.activitys.otherActivity.WebActivity;
import com.example.palyandroid.adapters.weChatAdapter.WeChatListAdapter;
import com.example.palyandroid.base.BaseFragment;
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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeChatListFragment extends BaseFragment<WeChatListPresenter, WeChatListView> implements WeChatListView {

    @BindView(R.id.rlv_WECHAT)
    RecyclerView rlv;
    @BindView(R.id.sm)
    SmartRefreshLayout sm;
    @BindView(R.id.fab_wechat)
    FloatingActionButton fab;
    Unbinder unbinder;
    private int id;
    private int mPage = 1;
    private List<WeChatListBean.DataBean.DatasBean> list;
    private WeChatListAdapter weChatListAdapter;
    private int mPosition;

    public static Fragment isWeChatListFragment(int id) {
        WeChatListFragment weChatListFragment = new WeChatListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        weChatListFragment.setArguments(bundle);
        return weChatListFragment;
    }

    @Override
    protected WeChatListPresenter initPresenter() {
        return new WeChatListPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_we_chat_list;
    }

    @Override
    protected void initView() {

        eventBus.register(this);
        Bundle arguments = getArguments();
        id = arguments.getInt("id");

        HideTablayout_Float();

        // 初始化适配器
        initAdapter();

        // 加载更多
        LoadMore();

        // 子条目点击事件
        AdapterListener();

    }

    private void initAdapter() {
        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        weChatListAdapter = new WeChatListAdapter(getContext(), list);
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
                intent.setClass(getActivity(), WebActivity.class);
                CircularAnimUtil.startActivity(getActivity(), intent, rlv, R.color.org);
                ToastUtil.showShort("公众号Web");

              /*  Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("link", list.get(mPosition).getLink());
                intent.putExtra("listTitle", list.get(mPosition).getTitle());
                startActivity(intent);*/

            }
        });
    }

    private void HideTablayout_Float() {

        final MainActivity mainActivity = (MainActivity) getContext();
        //滑动recyclerView隐藏tablayout
        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (firstVisibleItem != 0) {
                    if (dy > 15) {
                        fab.setVisibility(View.GONE);
                        mainActivity.getTabLayout().setVisibility(View.GONE);
                    } else if (dy < -15) {
                        fab.setVisibility(View.VISIBLE);
                        mainActivity.getTabLayout().setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //点击悬浮按钮回到底部
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlv.smoothScrollToPosition(0);
            }
        });

    }

    private void LoadMore() {

        //设置 Footer 为 球脉冲
        sm.setRefreshFooter(new ClassicsFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));

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

    @Override
    protected void initData() {

        Map<String, Object> map = new HashMap<>();
        mPresenter.getData(id, mPage, map);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBean(String query) {

        weChatListAdapter.list.clear();

        Map<String, Object> map = new HashMap<>();
        map.put("k", query);
        mPresenter.getData(id, mPage, map);

    }


    @Override
    public void showData(WeChatListBean weChatListBean) {
        list = weChatListBean.getData().getDatas();
        if (list != null) {
            weChatListAdapter.setList(list);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

}
