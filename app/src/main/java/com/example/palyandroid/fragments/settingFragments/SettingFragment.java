package com.example.palyandroid.fragments.settingFragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.palyandroid.R;
import com.example.palyandroid.activitys.MainActivity;
import com.example.palyandroid.base.BaseFragment;
import com.example.palyandroid.net.Constants;
import com.example.palyandroid.presenters.mainPresenter.MainPresenter;
import com.example.palyandroid.utils.ACache;
import com.example.palyandroid.utils.ShareUtil;
import com.example.palyandroid.utils.SpUtil;
import com.example.palyandroid.utils.ToastUtil;
import com.example.palyandroid.utils.UIModeUtil;
import com.example.palyandroid.views.mainView.MainView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment<MainPresenter, MainView> implements MainView {

    @BindView(R.id.cache)
    CheckBox mCache;
    @BindView(R.id.picture)
    CheckBox mPicture;
    @BindView(R.id.nightMode)
    CheckBox mNightMode;
    @BindView(R.id.opinion)
    RelativeLayout mOpinion;
    @BindView(R.id.cache_size)
    TextView mCacheSize;
    @BindView(R.id.cache_clear)
    RelativeLayout mCacheClear;

    private File cacheFile;   //缓存的文件

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initData() {

        cacheFile = new File(Constants.PATH_CACHE);
        mCacheSize.setText(ACache.getCacheSize(cacheFile));

        //判断当前是否日间模式，如果是设置为未选中状态，不是设置为选中状态
        int currentNightMode = getActivity().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            mNightMode.setChecked(false);
        } else {
            mNightMode.setChecked(true);
        }

    }

    @Override
    protected void initListener() {
        //无图模式
        mPicture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SpUtil.setParam(Constants.PICTOR, isChecked);
                } else {
                    SpUtil.setParam(Constants.PICTOR, isChecked);
                }
            }
        });


        //夜间模式
        mNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //切换日夜间模式 --- Activity会重建 对应的碎片也会重建
                if (buttonView.isPressed()) {
                    // 切换并保存模式
                    UIModeUtil.changeModeUI((MainActivity) getActivity());
                    // 保存当前碎片的type
                    SpUtil.setParam(Constants.NIGHT_CURRENT_FRAG_POS, MainActivity.TYPE_SETTING);
                }
            }
        });
    }

    @OnClick({R.id.opinion, R.id.cache_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.opinion:
                ShareUtil.sendEmail(getActivity(),getString(R.string.email));
                break;
            case R.id.cache_clear:
                clearCache();
                break;
        }
    }

    //清除缓存
    private void clearCache() {
        ACache.deleteDir(cacheFile);
        mCacheSize.setText(ACache.getCacheSize(cacheFile));
    }


}
