package com.itheima.eventbusdemo;

public class MyEvent {
    public static final String TAG = "MyEvent";
    public String msg;

    public MyEvent(String msg) {
        this.msg = msg;
    }
}
