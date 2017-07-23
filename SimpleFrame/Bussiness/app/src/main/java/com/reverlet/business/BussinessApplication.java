package com.reverlet.business;

import android.app.Application;

import com.riverlet.lib.util.LogUtil;

/**
 * Created by jian on 2017/7/23.
 */

public class BussinessApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(true,true,'v',"tag",getCacheDir().toString());
    }
}
