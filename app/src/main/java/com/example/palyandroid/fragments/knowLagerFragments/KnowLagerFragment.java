package com.example.palyandroid.fragments.knowLagerFragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.MainActivity;
import com.example.palyandroid.activitys.otherActivity.KnowLagerActivity;
import com.example.palyandroid.activitys.otherActivity.WebActivity;
import com.example.palyandroid.adapters.knowLagerAdapter.MyAdapterKnowLager;
import com.example.palyandroid.base.BaseFragment;
import com.example.palyandroid.beans.knowlagerBean.KnowLagerBean;
import com.example.palyandroid.presenters.knowLagerPresenter.KnowLagerPresenter;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.views.knowLagerView.KnowLagerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class KnowLagerFragment extends BaseFragment<KnowLagerPresenter, KnowLagerView> implements KnowLagerView {

    @BindView(R.id.rlv_KnowLager)
    RecyclerView rlv;
    @BindView(R.id.sm)
    SmartRefreshLayout sm;
    private List<KnowLagerBean.DataBean> list;
    private MyAdapterKnowLager myAdapterKnowLager;
    private int mPosition;

    @Override
    protected KnowLagerPresenter initPresenter() {
        return new KnowLagerPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_know_lager;
    }

    @Override
    protected void initView() {

        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        myAdapterKnowLager = new MyAdapterKnowLager(getContext(), list);
        rlv.setAdapter(myAdapterKnowLager);
        //子条目点击事件
        itemListener();
        HideTablayout_Float();
    }

    private void itemListener() {
        myAdapterKnowLager.setOnItemClickListener(new MyAdapterKnowLager.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                mPosition = position;

                Intent intent = new Intent();
                intent.putExtra("title",list.get(mPosition).getName());
                intent.putExtra("position",mPosition);
                intent.setClass(getActivity(), KnowLagerActivity.class);
                CircularAnimUtil.startActivity(getActivity(), intent, rlv, R.color.org);
                ToastUtil.showShort("知识体系详情");

               /* Intent intent = new Intent(getContext(), KnowLagerActivity.class);
                intent.putExtra("title",list.get(mPosition).getName());
                intent.putExtra("position",mPosition);
                startActivity(intent);*/

            }
        });
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
    protected void initData() {
        Map<String, Object> map = new HashMap<>();
        mPresenter.getData("tree/json",map);
    }

    @Override
    public void showData(KnowLagerBean knowLagerBean) {
        list = knowLagerBean.getData();
        if (list != null) {
            myAdapterKnowLager.setList(list);
        }
    }
}
