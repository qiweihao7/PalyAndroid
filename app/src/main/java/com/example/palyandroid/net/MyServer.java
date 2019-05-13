package com.example.palyandroid.net;

import com.example.palyandroid.beans.otherBean.NetBean;
import com.example.palyandroid.beans.safariBean.SafariBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by 走马 on 2019/4/28.
 */

public interface MyServer {

    // 首页文章列表  --->  https://www.wanandroid.com/article/list/0/json
    // 首页banner  --->  https://www.wanandroid.com/banner/json
    // 常用网站     --->  https://www.wanandroid.com/friend/json
    // 搜索热词  --->  https://www.wanandroid.com//hotkey/json
    // 体系数据 ---> http://www.wanandroid.com/tree/json
    // 体系下文文章 ---> http://www.wanandroid.com/article/list/0/json?cid=60
    // 置顶文章  --->  https://www.wanandroid.com/article/top/json
    // 导航数据  --->  https://www.wanandroid.com/navi/json
    // 项目分类  --->  https://www.wanandroid.com/project/tree/json
    // 项目列表数据  --->  https://www.wanandroid.com/project/list/1/json?cid=294


    String URL = "https://www.wanandroid.com/";

    String wanurl="http://www.wanandroid.com/";
    @GET("friend/json")
    Observable<NetBean> netData();

    @GET("navi/json")
    Observable<SafariBean> moveData();

    @GET
    Observable<String> getData(@Url String url, @QueryMap Map<String,Object> map);

    @GET("article/list/{page}/json")
    Observable<String> getHomeListData(@Path("page") int page, @QueryMap Map<String,Object> map);

    @GET("project/list/{page}/json")
    Observable<String> getProjectListData(@Path("page") int page, @QueryMap Map<String,Object> map);

    @GET("article/list/{page}/json")
    Observable<String> getKnowLagerListData(@Path("page") int page, @QueryMap Map<String,Object> map);

    // https://wanandroid.com/wxarticle/list/408/1/json
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<String> getWechatListData(@Path("id") int id, @Path("page") int page,@QueryMap Map<String, Object> map);
    // https://wanandroid.com/wxarticle/list/408/1/json?k=Java

}
