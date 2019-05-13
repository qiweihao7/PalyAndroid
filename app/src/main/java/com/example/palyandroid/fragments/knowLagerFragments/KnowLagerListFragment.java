package com.example.palyandroid.fragments.knowLagerFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.otherActivity.WebActivity;
import com.example.palyandroid.adapters.homeAdapter.MyAdapterHome;
import com.example.palyandroid.adapters.knowLagerAdapter.MyAdapterKnowLagerList;
import com.example.palyandroid.base.BaseFragment;
import com.example.palyandroid.beans.knowlagerBean.KnowLagerListBean;
import com.example.palyandroid.presenters.knowLagerPresenter.KnowLagerListPresenter;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.views.knowLagerView.KnowLagerListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class KnowLagerListFragment extends BaseFragment<KnowLagerListPresenter, KnowLagerListView> implements KnowLagerListView {

    @BindView(R.id.rlv_KnowLagerList)
    RecyclerView rlv;
    @BindView(R.id.sm)
    SmartRefreshLayout sm;
    @BindView(R.id.fab_know)
    FloatingActionButton fabKnow;
    Unbinder unbinder;
    private int id;
    private int mPage = 0;
    private List<KnowLagerListBean.DataBean.DatasBean> list;
    private MyAdapterKnowLagerList myAdapterKnowLagerList;
    private int mPosition;

    public static Fragment isKnowLagerListFragment(int id) {

        KnowLagerListFragment knowLagerListFragment = new KnowLagerListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        knowLagerListFragment.setArguments(bundle);

        return knowLagerListFragment;
    }

    @Override
    protected KnowLagerListPresenter initPresenter() {
        return new KnowLagerListPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_know_lager_list;
    }

    @Override
    protected void initView() {

        Bundle arguments = getArguments();
        id = arguments.getInt("id");

        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        myAdapterKnowLagerList = new MyAdapterKnowLagerList(getContext(), list);
        rlv.setAdapter(myAdapterKnowLagerList);

        myAdapterKnowLagerList.setOnItemClickListener(new MyAdapterHome.OnItemClickListener() {
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
                ToastUtil.showShort("知识体系Web");

               /* Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("link", list.get(mPosition).getLink());
                intent.putExtra("listTitle", list.get(mPosition).getTitle());*/

            }
        });

        LoadMore();
    }

    @Override
    protected void initListener() {
        fabKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlv.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    protected void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", id);
        mPresenter.getData(mPage, map);
    }

    private void LoadMore() {

        //设置 Footer 为 球脉冲
        sm.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));

        sm.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                initData();
                myAdapterKnowLagerList.notifyDataSetChanged();
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
    public void showListData(KnowLagerListBean knowLagerListBean) {
        if (knowLagerListBean != null) {
            list = knowLagerListBean.getData().getDatas();
            myAdapterKnowLagerList.setList(list);
        }
    }

}
