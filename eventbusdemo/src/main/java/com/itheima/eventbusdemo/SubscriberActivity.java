package com.itheima.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SubscriberActivity extends AppCompatActivity {

    private static final String TAG = "SubscriberActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

    }

    public void onStartPublisher(View view) {
        Intent intent = new Intent(this, PublisherActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 方法是public方法 要接收什么事件，就声明什么类型的形参
     * 不管发布消息在哪个线程，接收消息都在主线程
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(MyEvent event) {
        Log.d(TAG, "onEventMain: " + Thread.currentThread().getName());
    };

    /**
     * 在哪个线程发布的消息，就在哪个线程执行
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventPosting(MyEvent event) {
        Log.d(TAG, "onEventPosting: " + Thread.currentThread().getName());
    };

    /**
     * 如果发布消息是在主线程，那么接收消息就在EventBus的线程池中执行
     * 如果发布消息是在子线程，那么接收消息就在子线程
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackground(MyEvent event) {
        Log.d(TAG, "onEventBackground: " + Thread.currentThread().getName());
    };

    /**
     * 不管发布消息在哪个线程，都在EventBus的线程池中执行
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventAsync(MyEvent event) {
        Log.d(TAG, "onEventAsync: " + Thread.currentThread().getName());
    };

}
