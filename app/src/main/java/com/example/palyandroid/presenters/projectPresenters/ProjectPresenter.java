package com.example.palyandroid.presenters.projectPresenters;

import com.example.palyandroid.base.BasePresenter;
import com.example.palyandroid.beans.projectBean.ProjectTabBean;
import com.example.palyandroid.models.projectModels.ProjectModel;
import com.example.palyandroid.net.CallBack;
import com.example.palyandroid.views.projectView.ProjectView;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by 走马 on 2019/4/26.
 */

public class ProjectPresenter extends BasePresenter<ProjectView>{

    private ProjectModel mProjectModel;

    @Override
    protected void initModel() {
        mProjectModel = new ProjectModel();
        mModels.add(mProjectModel);
    }

    public void getTabList(String url, Map<String, Object> map) {
        mProjectModel.getProjectTabList(url,map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                String data = (String) o;
                ProjectTabBean tabBean = new Gson().fromJson(data, ProjectTabBean.class);
                if (tabBean != null) {
                    if (mView != null) {
                        mView.setTabBean(tabBean);
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
