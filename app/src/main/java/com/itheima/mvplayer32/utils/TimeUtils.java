package com.itheima.mvplayer32.utils;

public class TimeUtils {
    public static final String TAG = "TimeUtils";

    public static final int HOUR = 60 * 60 * 1000;
    public static final int MINUTE = 60 * 1000;
    public static final int SECOND = 1000;

    public static String parseTime(int progress) {
        int hour = progress / HOUR;
        int minute = progress % HOUR / MINUTE;
        int second = progress % HOUR % MINUTE / SECOND;
        String result = "";
        if (hour > 0) {
            result = String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            result = String.format("%02d:%02d", minute, second);
        }
        return result;
    }
}
