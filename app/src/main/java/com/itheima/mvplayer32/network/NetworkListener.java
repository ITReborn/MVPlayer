package com.itheima.mvplayer32.network;

public interface NetworkListener<T> {

    void onFailed(String s);

    /**
     * 回调解析后的结果
     * @param result
     */
    void onSuccess(T result);
}
