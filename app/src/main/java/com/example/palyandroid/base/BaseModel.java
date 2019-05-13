package com.example.palyandroid.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 走马 on 2019/4/26.
 */

public class BaseModel {
    // 定义一个容器
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    //退出界面
    public void onDestory() {
        mCompositeDisposable.clear();
    }
}
