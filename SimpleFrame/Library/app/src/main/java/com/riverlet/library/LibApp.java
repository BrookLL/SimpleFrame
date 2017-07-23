package com.riverlet.library;

import android.app.Application;

import com.riverlet.lib.util.LogUtil;

/**
 * Created by jian on 2017/7/23.
 */

public class LibApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(true,true,'v', "tag",this.getCacheDir().toString());
    }
}
