package com.example.palyandroid.presenters.safariPresenter;

import com.example.palyandroid.base.BasePresenter;
import com.example.palyandroid.beans.safariBean.SafariBean;
import com.example.palyandroid.models.safariModel.SafaritModel;
import com.example.palyandroid.net.CallBack;
import com.example.palyandroid.views.safariView.SafariView;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by 走马 on 2019/4/26.
 */

public class SafariPresenter extends BasePresenter<SafariView>{

    private SafaritModel mSafaritModel;

    @Override
    protected void initModel() {
        mSafaritModel = new SafaritModel();
        mModels.add(mSafaritModel);
    }

    public void getData(String url, Map<String, Object> map) {
        mSafaritModel.getData(url,map, new CallBack() {
            @Override
            public void onSuccess(Object o) {

                String data = (String) o;
                SafariBean safariBean = new Gson().fromJson(data, SafariBean.class);
                if (safariBean != null) {
                    if (mView != null) {
                        mView.showData(safariBean);
                    }
                }

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
