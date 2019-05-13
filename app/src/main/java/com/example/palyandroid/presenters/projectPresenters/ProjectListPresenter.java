package com.example.palyandroid.presenters.projectPresenters;

import com.example.palyandroid.base.BasePresenter;
import com.example.palyandroid.beans.projectBean.ProjectListBean;
import com.example.palyandroid.models.projectModels.ProjectListModel;
import com.example.palyandroid.net.CallBack;
import com.example.palyandroid.views.projectView.ProjectListView;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * Created by 走马 on 2019/4/28.
 */

public class ProjectListPresenter extends BasePresenter<ProjectListView>{

    private ProjectListModel mProjectListModel;

    @Override
    protected void initModel() {
        mProjectListModel = new ProjectListModel();
        mModels.add(mProjectListModel);
    }

    public void getProjectList(int page, Map<String, Object> map) {
        mProjectListModel.getProjectList(page,map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                String data = (String) o;
                ProjectListBean projectListBean = new Gson().fromJson(data, ProjectListBean.class);
                if (projectListBean != null) {
                    if (mView != null) {
                        mView.setProjectList(projectListBean);
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
