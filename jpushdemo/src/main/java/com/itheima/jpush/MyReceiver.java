package com.itheima.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    public static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Toast.makeText(context, "收到自定义消息" + message, Toast.LENGTH_SHORT).show();
        }
    }
}
