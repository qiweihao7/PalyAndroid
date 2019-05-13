package com.example.palyandroid.fragments.projectfragments;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.example.palyandroid.R;
import com.example.palyandroid.adapters.projectAdapter.ProjectTabFragmentAdapter;
import com.example.palyandroid.base.BaseFragment;
import com.example.palyandroid.beans.projectBean.ProjectTabBean;
import com.example.palyandroid.presenters.projectPresenters.ProjectPresenter;
import com.example.palyandroid.views.projectView.ProjectView;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends BaseFragment<ProjectPresenter, ProjectView> implements ProjectView {

    @BindView(R.id.tab_PROJECT)
    SlidingTabLayout tab;
    @BindView(R.id.vp_PROJECT)
    ViewPager vp;

    @Override
    protected ProjectPresenter initPresenter() {
        return new ProjectPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_project;
    }


   /* @Override
    protected void initView() {
        LinearLayout linearLayout = (LinearLayout) tab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.layout_divider_vertical));
    }*/

    @Override
    protected void initData() {

        // https://www.wanandroid.com/project/tree/json
        Map<String, Object> map = new HashMap<>();
        mPresenter.getTabList("project/tree/json", map);

    }

    @Override
    public void setTabBean(ProjectTabBean tabBean) {
        List<ProjectTabBean.DataBean> list = tabBean.getData();
        if (list != null) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            for (ProjectTabBean.DataBean dataBean : list) {
                Fragment projectListFragment = ProjectListFragment.isProjectListFragment(dataBean.getId());
                fragments.add(projectListFragment);
            }
            ProjectTabFragmentAdapter projectTabFragmentAdapter = new ProjectTabFragmentAdapter(getChildFragmentManager(), fragments, list);
            vp.setAdapter(projectTabFragmentAdapter);
            tab.setViewPager(vp);
        }

    }
}
