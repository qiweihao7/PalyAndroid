package com.example.palyandroid.models.wechatModel;

import android.util.Log;

import com.example.palyandroid.base.BaseModel;
import com.example.palyandroid.net.CallBack;
import com.example.palyandroid.net.MyServer;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 走马 on 2019/5/4.
 */

public class WeChatListModel extends BaseModel {
    public void getData(int id, int mPage, Map<String, Object> map, CallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(MyServer.URL)
                .build();
        MyServer myServer = retrofit.create(MyServer.class);
        Observable<String> wechatListData = myServer.getWechatListData(id, mPage,map);
        wechatListData.subscribeOn(Schedulers.io())  //被观察者在子线程
                .observeOn(AndroidSchedulers.mainThread())  //观察者在主线程中执行
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.i("wyt", "showVerify: " + value);
                        callBack.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("wyt", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
