package com.example.palyandroid.models.safariModel;

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
 * Created by 走马 on 2019/4/26.
 */

public class SafaritModel extends BaseModel{
    public void getData(String url, Map<String, Object> map, final CallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyServer.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        MyServer myServer = retrofit.create(MyServer.class);
        Observable<String> data = myServer.getData(url, map);
        data.subscribeOn(Schedulers.io())  //被观察者在子线程
                        .observeOn(AndroidSchedulers.mainThread())  //观察者在主线程中执行
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String value) {
                                Log.i("wyt", "showVerify: "+value);
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
