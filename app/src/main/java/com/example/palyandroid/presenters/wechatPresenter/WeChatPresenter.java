package com.example.palyandroid.presenters.wechatPresenter;

import com.example.palyandroid.base.BasePresenter;
import com.example.palyandroid.beans.weChatBean.WeChatTabBean;
import com.example.palyandroid.models.wechatModel.WeChatModel;
import com.example.palyandroid.net.CallBack;
import com.example.palyandroid.views.wechatView.WeChatView;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by 走马 on 2019/4/26.
 */

public class WeChatPresenter extends BasePresenter<WeChatView>{

    private WeChatModel mWeChatModel;

    @Override
    protected void initModel() {
        mWeChatModel = new WeChatModel();
        mModels.add(mWeChatModel);
    }

    public void getData(String url, Map<String, Object> map) {
        mWeChatModel.getData(url,map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                String data = (String) o;
                WeChatTabBean weChatTabBean = new Gson().fromJson(data, WeChatTabBean.class);
                if (weChatTabBean != null) {
                    if (mView != null) {
                        mView.showData(weChatTabBean);
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
