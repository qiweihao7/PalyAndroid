package com.example.palyandroid.adapters.knowLagerAdapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.palyandroid.beans.knowlagerBean.KnowLagerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 走马 on 2019/5/4.
 */

public class FragmentKnowLagerAdater extends FragmentStatePagerAdapter{
    private final ArrayList<Fragment> fragments;
    private final List<KnowLagerBean.DataBean.ChildrenBean> list;

    public FragmentKnowLagerAdater(FragmentManager fm, ArrayList<Fragment> fragments, List<KnowLagerBean.DataBean.ChildrenBean> list) {
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
