package com.example.palyandroid.net;

/**
 * Created by 走马 on 2019/4/28.
 */

public interface CallBack<T> {
    void onSuccess(T t);
    void onFail(String msg);
}
