package com.example.palyandroid.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.palyandroid.net.Constants;
import com.example.palyandroid.utils.SpUtil;
import com.example.palyandroid.utils.UIModeUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import retrofit2.http.Multipart;

/**
 * Created by 走马 on 2019/4/28.
 */

public class BaseApp extends MultiDexApplication {

    public static BaseApp sBaseApp;
    //默认不是夜间模式
    public static int mMode = AppCompatDelegate.MODE_NIGHT_NO;
    public static int mWidthPixels;
    public static int mHeightPixels;

    public BaseApp() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApp = this;
        getScreenWH();
        // setDayNightMode();
        setUmeng();
    }

    private void setUmeng() {

        // ap:5cc793b54ca3576869000384
        UMConfigure.init(this,"5cc793b54ca3576869000384"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        /*PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");
        PlatformConfig.setYnote("9c82bf470cba7bd2f1819b0ee26f86c6ce670e9b");*/


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void setDayNightMode() {
        //根据Sp里面的模式设置对应的夜间模式
        mMode = (int) SpUtil.getParam(Constants.MODE, AppCompatDelegate.MODE_NIGHT_NO);
        UIModeUtil.setAppMode(mMode);
    }

    //屏幕宽高
    private void getScreenWH() {
        WindowManager manger = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = manger.getDefaultDisplay();
        DisplayMetrics metics = new DisplayMetrics();
        defaultDisplay.getMetrics(metics);
        mWidthPixels = metics.widthPixels;
        mHeightPixels = metics.heightPixels;
    }

    public static BaseApp getInstance(){
        return sBaseApp;
    }



}
