package com.example.palyandroid.net;

import android.os.Environment;

import com.example.palyandroid.base.BaseApp;

import java.io.File;

/**
 * Created by 走马 on 2019/4/28.
 */

public interface Constants {
    String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "codeest" + File.separator + "GeekNews";

    String FILE_PROVIDER_AUTHORITY="com.baidu.geek.fileprovider";

    //网络缓存的地址
    String PATH_DATA = BaseApp.getInstance().getCacheDir().getAbsolutePath() +
            File.separator + "data";

    String PATH_CACHE = PATH_DATA + "/NetCache";
    String DATA = "data";
    String URL = "url";
    String TITLE = "title";
    //夜间模式
    String MODE = "mode";
    String NIGHT_CURRENT_FRAG_POS = "fragment_pos";
    // 保存设置日夜间模式的时候的碎片
    String DAY_NIGHT_FRAGMENT_POS = "";

    //无图模式
    String PICTOR = "PICTOR";

    String NAME = "name";
    String IMG = "img";

    String FOLLOW = "follow";
}
