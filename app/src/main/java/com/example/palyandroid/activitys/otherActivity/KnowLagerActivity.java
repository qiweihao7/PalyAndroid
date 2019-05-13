package com.example.palyandroid.activitys.otherActivity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palyandroid.R;
import com.example.palyandroid.adapters.knowLagerAdapter.FragmentKnowLagerAdater;
import com.example.palyandroid.base.BaseActivity;
import com.example.palyandroid.beans.knowlagerBean.KnowLagerBean;
import com.example.palyandroid.fragments.knowLagerFragments.KnowLagerListFragment;
import com.example.palyandroid.presenters.knowLagerPresenter.KnowLagerPresenter;
import com.example.palyandroid.views.knowLagerView.KnowLagerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class KnowLagerActivity extends BaseActivity<KnowLagerPresenter, KnowLagerView> implements KnowLagerView {

    @BindView(R.id.toolBar_tv)
    TextView toolBarTv;
    @BindView(R.id.toolBar_KnowLager)
    Toolbar toolBar;
    @BindView(R.id.tabLayout_KnowLager)
    TabLayout tabLayout;
    @BindView(R.id.vp_KNOWLAGER)
    ViewPager vp;
    private int position;

    @Override
    protected KnowLagerPresenter initPresenter() {
        return new KnowLagerPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_know_lager;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        position = intent.getIntExtra("position",0);

        toolBar.setTitle("");
        toolBarTv.setText(title);
        toolBar.setNavigationIcon(R.drawable.zjt);
        setSupportActionBar(toolBar);

        toolBarListener();

    }

    @Override
    protected void initData() {
        Map<String, Object> map = new HashMap<>();
        mPresenter.getData("tree/json",map);
    }


    @Override
    public void showData(KnowLagerBean knowLagerBean) {

        if (knowLagerBean != null) {
            List<KnowLagerBean.DataBean.ChildrenBean> list = knowLagerBean.getData().get(position).getChildren();
            if (list != null) {
                ArrayList<Fragment> fragments = new ArrayList<>();
                for (KnowLagerBean.DataBean.ChildrenBean childrenBean : list) {
                    Fragment knowLagerListFragment = KnowLagerListFragment.isKnowLagerListFragment(childrenBean.getId());
                    fragments.add(knowLagerListFragment);
                }
                FragmentKnowLagerAdater fragmentKnowLagerAdater = new FragmentKnowLagerAdater(getSupportFragmentManager(), fragments,list);
                vp.setAdapter(fragmentKnowLagerAdater);
                tabLayout.setupWithViewPager(vp);
            }
        }

    }


    private void toolBarListener() {
        // 监听事件必须写在 setSupportActionBar(toolBar) 后面
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                finish();*/
                KnowLagerActivity.this.finish();
                //Snackbar.make(toolBar,"已关注",Snackbar.LENGTH_SHORT).show();
                Toast.makeText(KnowLagerActivity.this, "退出", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
