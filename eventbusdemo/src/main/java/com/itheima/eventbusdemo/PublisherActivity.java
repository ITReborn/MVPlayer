package com.itheima.eventbusdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

public class PublisherActivity extends AppCompatActivity {
    public static final String TAG = "PublisherActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
    }

    public void onPostMessage(View view) {
        MyEvent myEvent = new MyEvent("来自Publisher的问候");
        EventBus.getDefault().post(myEvent);
    }

    public void onPostMessageInBackground(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyEvent myEvent = new MyEvent("来自Publisher的问候");
                EventBus.getDefault().post(myEvent);
            }
        }).start();
    }
}
