package com.example.palyandroid.adapters.projectAdapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.palyandroid.beans.projectBean.ProjectTabBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 走马 on 2019/4/28.
 */

public class ProjectTabFragmentAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> fragments;
    private final List<ProjectTabBean.DataBean> list;

    public ProjectTabFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments, List<ProjectTabBean.DataBean> list) {
        super(fm);
        this.fragments = fragments;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}
