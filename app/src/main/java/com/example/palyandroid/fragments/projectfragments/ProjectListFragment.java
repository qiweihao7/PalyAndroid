package com.example.palyandroid.fragments.projectfragments;


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
import com.example.palyandroid.adapters.projectAdapter.MyAdapterProjectList;
import com.example.palyandroid.base.BaseFragment;
import com.example.palyandroid.beans.projectBean.ProjectListBean;
import com.example.palyandroid.presenters.projectPresenters.ProjectListPresenter;
import com.example.palyandroid.utils.CircularAnimUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.views.projectView.ProjectListView;
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
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectListFragment extends BaseFragment<ProjectListPresenter, ProjectListView> implements ProjectListView {

    @BindView(R.id.rlv_PROJECT)
    RecyclerView rlv;
    @BindView(R.id.sm)
    SmartRefreshLayout sm;
    @BindView(R.id.fab_project)
    FloatingActionButton fab;
    Unbinder unbinder;
    private int id;
    private List<ProjectListBean.DataBean.DatasBean> list;
    private MyAdapterProjectList myAdapterProjectList;
    private int mPosition;
    private int mPage;

    public static Fragment isProjectListFragment(int id) {

        ProjectListFragment projectListFragment = new ProjectListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        projectListFragment.setArguments(bundle);

        return projectListFragment;

    }

    @Override
    protected ProjectListPresenter initPresenter() {
        return new ProjectListPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_project_list;
    }


    private void getCid() {
        Bundle arguments = getArguments();
        id = arguments.getInt("id");
    }

    @Override
    protected void initView() {

        // 获取界面id
        getCid();

        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        myAdapterProjectList = new MyAdapterProjectList(getContext(), list);
        rlv.setAdapter(myAdapterProjectList);


        WebListener();
        HideTablayout_Float();

    }

    private void LoadMore() {
        //设置 Footer 为 球脉冲
        sm.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));

        sm.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                initData();
                myAdapterProjectList.notifyDataSetChanged();
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

    private void WebListener() {

        myAdapterProjectList.setOnItemClickListener(new MyAdapterProjectList.OnItemClickListener() {
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
                ToastUtil.showShort("项目Web");

                /*Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("link", list.get(mPosition).getLink());
                intent.putExtra("listTitle", list.get(mPosition).getTitle());
                startActivity(intent);*/

            }
        });


    }

    @Override
    protected void initData() {

        // https://www.wanandroid.com/project/list/1/json?cid=294
        Map<String, Object> map = new HashMap<>();
        map.put("cid", id);
        mPresenter.getProjectList(1, map);

    }

    @Override
    public void setProjectList(ProjectListBean projectListBean) {
        list = projectListBean.getData().getDatas();
        if (list != null && list.size() > 0) {
            myAdapterProjectList.setList(list);
        }
    }

}
