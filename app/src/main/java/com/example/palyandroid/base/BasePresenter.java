package com.example.palyandroid.base;

import com.example.palyandroid.views.BaseView;

import java.util.ArrayList;

/**
 * Created by 走马 on 2019/4/26.
 */

public abstract class BasePresenter<V extends BaseView> {

    protected V mView;
    protected ArrayList<BaseModel> mModels = new ArrayList<>();

    public BasePresenter() {
        initModel();
    }
    protected abstract void initModel();

    public void bind(V mView) {
        this.mView = mView;
    }

    public void onDestroy() {

        mView = null;

        // mModels.size() > 0 说明他有M层
        if ( mModels.size() > 0 ){

            for (BaseModel model : mModels) {
                model.onDestory();
            }

        }

    }

}
