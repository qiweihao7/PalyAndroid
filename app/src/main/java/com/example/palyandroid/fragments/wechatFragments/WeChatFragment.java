package com.example.palyandroid.fragments.wechatFragments;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.palyandroid.R;
import com.example.palyandroid.adapters.weChatAdapter.WeChatFragmentAdapter;
import com.example.palyandroid.base.BaseFragment;
import com.example.palyandroid.beans.weChatBean.WeChatTabBean;
import com.example.palyandroid.presenters.wechatPresenter.WeChatPresenter;
import com.example.palyandroid.views.wechatView.WeChatView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeChatFragment extends BaseFragment<WeChatPresenter, WeChatView> implements WeChatView {

    @BindView(R.id.tabLayou_w)
    TabLayout tabLayou;
    @BindView(R.id.vp_w)
    ViewPager vp;
    @BindView(R.id.search)
    Button searchButton;
    @BindView(R.id.et_text_w)
    SearchView searchView;
    private List<WeChatTabBean.DataBean> list;

    @Override
    protected WeChatPresenter initPresenter() {
        return new WeChatPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_we_chat;
    }

    @Override
    protected void initData() {
        Map<String, Object> map = new HashMap<>();
        mPresenter.getData("wxarticle/chapters/json", map);
    }

    @Override
    protected void initListener() {
        // tab栏切换谁谁谁带你发现更多干货
        TabLayoutListener();
        // 搜索框文字监听
        SearchViewListener();

    }

    private void SearchViewListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override //单击搜索按钮的监听
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override //输入字符的监听
            public boolean onQueryTextChange(String newText) {
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(newText);
                    }
                });
                return true;
            }
        });
    }


    private void TabLayoutListener() {
        tabLayou.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();
                searchView.setQueryHint(list.get(position).getName() + "带你发现更多干货");

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void showData(WeChatTabBean weChatTabBean) {
        list = weChatTabBean.getData();
        if (list != null) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            for (WeChatTabBean.DataBean dataBean : list) {
                Fragment weChatListFragment = WeChatListFragment.isWeChatListFragment(dataBean.getId());
                fragments.add(weChatListFragment);
            }
            WeChatFragmentAdapter weChatFragmentAdapter = new WeChatFragmentAdapter(getChildFragmentManager(), list, fragments);
            vp.setAdapter(weChatFragmentAdapter);
            tabLayou.setupWithViewPager(vp);
        }
    }

}
