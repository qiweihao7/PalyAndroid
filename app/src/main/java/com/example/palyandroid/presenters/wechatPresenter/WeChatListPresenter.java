package com.example.palyandroid.presenters.wechatPresenter;

import com.example.palyandroid.base.BasePresenter;
import com.example.palyandroid.beans.weChatBean.WeChatListBean;
import com.example.palyandroid.models.wechatModel.WeChatListModel;
import com.example.palyandroid.net.CallBack;
import com.example.palyandroid.views.wechatView.WeChatListView;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by 走马 on 2019/5/4.
 */

public class WeChatListPresenter extends BasePresenter<WeChatListView>{

    private WeChatListModel mWeChatListModel;

    @Override
    protected void initModel() {
        mWeChatListModel = new WeChatListModel();
        mModels.add(mWeChatListModel);
    }

    public void getData(int id, int mPage, Map<String, Object> map) {
        mWeChatListModel.getData(id,mPage, map,new CallBack() {
            @Override
            public void onSuccess(Object o) {

                String data = (String) o;
                WeChatListBean weChatListBean = new Gson().fromJson(data, WeChatListBean.class);
                if (weChatListBean != null) {
                    if (mView != null) {
                        mView.showData(weChatListBean);
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
