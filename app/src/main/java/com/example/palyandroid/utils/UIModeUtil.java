package com.example.palyandroid.utils;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.example.palyandroid.base.BaseApp;
import com.example.palyandroid.net.Constants;


/**
 * Created by 走马 on 2019/4/2.
 */

public class UIModeUtil {
    /**
     * 夜间模式切换
     */
    public static void changeModeUI(AppCompatActivity activity){
        int currentNightMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if(currentNightMode == Configuration.UI_MODE_NIGHT_NO){
            //日间模式,切成夜间模式
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SpUtil.setParam(Constants.MODE,AppCompatDelegate.MODE_NIGHT_YES);
            BaseApp.mMode = AppCompatDelegate.MODE_NIGHT_YES;
        }else{
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SpUtil.setParam(Constants.MODE,AppCompatDelegate.MODE_NIGHT_NO);
            BaseApp.mMode = AppCompatDelegate.MODE_NIGHT_NO;
        }
    }

    /**
     * 设置当前的模式
     * @param activity
     */
    public static void setActModeFromSp(AppCompatActivity activity){
        int mode = (int) SpUtil.getParam(Constants.MODE, AppCompatDelegate.MODE_NIGHT_NO);
        activity.getDelegate().setLocalNightMode(mode);
    }

    /**
     * 设置当前的模式
     * @param activity
     */
    public static void setActModeUseMode(AppCompatActivity activity,int mode){
        activity.getDelegate().setLocalNightMode(mode);
    }

    public static void setAppMode(int mode){
        AppCompatDelegate.setDefaultNightMode(mode);
    }
}
