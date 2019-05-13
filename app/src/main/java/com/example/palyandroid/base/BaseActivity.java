package com.example.palyandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.palyandroid.views.BaseView;

import butterknife.ButterKnife;

/**
 * Created by 走马 on 2019/4/26.
 */

public abstract class BaseActivity<P extends BasePresenter,V extends BaseView> extends AppCompatActivity implements BaseView{

    private int layoutId;

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        ButterKnife.bind(this);

        mPresenter = initPresenter();

        if (mPresenter != null) {
            mPresenter.bind(this);
        }

        initView();
        initData();
        initListener();

    }

    protected abstract P initPresenter();

    protected void initListener(){}

    protected void initData(){}

    protected void initView(){}

    public abstract int getLayoutId() ;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //切断V层和P层的联系
        mPresenter.onDestroy();
        mPresenter = null;
    }
}
