package com.itheima.jpush;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

public class JpushApplication extends Application {
    public static final String TAG = "JpushApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
