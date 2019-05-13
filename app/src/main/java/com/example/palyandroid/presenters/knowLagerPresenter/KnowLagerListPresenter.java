package com.example.palyandroid.presenters.knowLagerPresenter;

import com.example.palyandroid.base.BasePresenter;
import com.example.palyandroid.beans.knowlagerBean.KnowLagerListBean;
import com.example.palyandroid.models.knowLagerModel.KnowLagerListModel;
import com.example.palyandroid.net.CallBack;
import com.example.palyandroid.views.knowLagerView.KnowLagerListView;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by 走马 on 2019/5/4.
 */

public class KnowLagerListPresenter extends BasePresenter<KnowLagerListView> {

    private KnowLagerListModel mKnowLagerListModel;

    @Override
    protected void initModel() {
        mKnowLagerListModel = new KnowLagerListModel();
        mModels.add(mKnowLagerListModel);
    }

    public void getData(int page, Map<String, Object> map) {
        mKnowLagerListModel.getKnowLagerListData(page,map, new CallBack() {
            @Override
            public void onSuccess(Object o) {

                String data = (String) o;
                KnowLagerListBean knowLagerListBean = new Gson().fromJson(data, KnowLagerListBean.class);
                if (knowLagerListBean != null) {
                    if (mView != null) {
                        mView.showListData(knowLagerListBean);
                    }
                }

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
