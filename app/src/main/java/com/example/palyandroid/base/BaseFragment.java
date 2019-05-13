package com.example.palyandroid.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.palyandroid.views.BaseView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment<P extends BasePresenter,V extends BaseView> extends Fragment implements BaseView{

    private Unbinder mUnbinder;
    protected P mPresenter;
    protected EventBus eventBus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), null);
        mUnbinder = ButterKnife.bind(this, inflate);

        eventBus = EventBus.getDefault();

        mPresenter = initPresenter();

        if ( mPresenter != null ){
            mPresenter.bind(this);
        }

        initView();
        initData();
        initListener();

        return inflate;
    }

    protected void initListener(){}

    protected void initData(){}

    protected void initView(){}

    protected abstract P initPresenter();

    public abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 解绑
        mUnbinder.unbind();
        mPresenter.onDestroy();
        mPresenter = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
