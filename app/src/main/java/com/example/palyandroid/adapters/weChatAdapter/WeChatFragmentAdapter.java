package com.example.palyandroid.adapters.weChatAdapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.palyandroid.beans.weChatBean.WeChatTabBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 走马 on 2019/5/4.
 */

public class WeChatFragmentAdapter extends FragmentStatePagerAdapter{

    private final List<WeChatTabBean.DataBean> list;
    private final ArrayList<Fragment> fragments;

    public WeChatFragmentAdapter(FragmentManager fm, List<WeChatTabBean.DataBean> list, ArrayList<Fragment> fragments) {
        super(fm);
        this.list = list;
        this.fragments = fragments;
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
