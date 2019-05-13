package com.example.palyandroid.views.homeView;

import com.example.palyandroid.views.BaseView;
import com.example.palyandroid.beans.homeBean.AlertListBean;
import com.example.palyandroid.beans.homeBean.BannerBean;

/**
 * Created by 走马 on 2019/4/26.
 */

public interface HomeView extends BaseView {
    void setAlertBean(AlertListBean alertListBean);
    void setBannerBean(BannerBean bannerBean);
}
