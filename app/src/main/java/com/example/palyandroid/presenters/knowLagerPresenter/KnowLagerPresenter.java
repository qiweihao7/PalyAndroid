package com.example.palyandroid.presenters.knowLagerPresenter;

import com.example.palyandroid.base.BasePresenter;
import com.example.palyandroid.beans.knowlagerBean.KnowLagerBean;
import com.example.palyandroid.models.knowLagerModel.KnowLagerModel;
import com.example.palyandroid.net.CallBack;
import com.example.palyandroid.views.knowLagerView.KnowLagerView;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by 走马 on 2019/4/26.
 */

public class KnowLagerPresenter extends BasePresenter<KnowLagerView>{

    private KnowLagerModel mKnowLagerModel;

    @Override
    protected void initModel() {
        mKnowLagerModel = new KnowLagerModel();
        mModels.add(mKnowLagerModel);
    }

    public void getData(String url, Map<String, Object> map) {
        mKnowLagerModel.getData(url,map, new CallBack() {
            @Override
            public void onSuccess(Object o) {

                String data = (String) o;

                KnowLagerBean knowLagerBean = new Gson().fromJson(data, KnowLagerBean.class);
                if (knowLagerBean != null) {
                    if (mView != null) {
                        mView.showData(knowLagerBean);
                    }
                }

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
