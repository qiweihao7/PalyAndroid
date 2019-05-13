package com.example.palyandroid.presenters.homePresenter;

import com.example.palyandroid.base.BasePresenter;
import com.example.palyandroid.beans.homeBean.AlertListBean;
import com.example.palyandroid.beans.homeBean.BannerBean;
import com.example.palyandroid.models.homeModels.HomeModel;
import com.example.palyandroid.net.CallBack;
import com.example.palyandroid.views.homeView.HomeView;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by 走马 on 2019/4/26.
 */

public class HomePresenter extends BasePresenter<HomeView>{

    private HomeModel mHomeModel;

    @Override
    protected void initModel() {
        mHomeModel = new HomeModel();
        mModels.add(mHomeModel);
    }

    public void getAlertData(int page, Map<String, Object> map) {
        mHomeModel.getAlertListData(page,map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                String data = (String) o;
                AlertListBean alertListBean = new Gson().fromJson(data, AlertListBean.class);
                if (alertListBean != null) {
                    if (mView != null) {
                        mView.setAlertBean(alertListBean);
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void getBannerData(String url, Map<String, Object> map) {
        mHomeModel.getBannerData(url,map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                String data = (String) o;
                BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
                if (bannerBean != null) {
                    if (mView != null) {
                        mView.setBannerBean(bannerBean);
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
