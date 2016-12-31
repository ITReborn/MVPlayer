package com.itheima.mvplayer32.network;

import com.google.gson.Gson;
import com.itheima.mvplayer32.model.NetworkManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 包含一个请求所需必要的信息url, 调用NetworkManager发送请求，解析网络结果，解析发送请求时想要的java bean
 * 还可以提供回调方法
 * @param <T>
 */
public class MVPlayer32Request<T> {
    public static final String TAG = "MVPlayer32Request";

    public static final int DEFAULT_PAGE_SIZE = 10;

    private String mUrl;
    private NetworkListener mNetworkListener;

    private Gson mGson;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public NetworkListener getNetworkListener() {
        return mNetworkListener;
    }

    public void setNetworkListener(NetworkListener networkListener) {
        mNetworkListener = networkListener;
    }

    public MVPlayer32Request(String url, NetworkListener listener) {
        mUrl = url;
        mNetworkListener = listener;
        mGson = new Gson();
    }

    public void execute() {
        NetworkManager.getInstance().sendRequest(this);
    }

    /**
     * 解析网络结果，解析发送请求时想要的java bean
     * @param string
     * @return
     */
    public T parseNetworkResponse(String string) {
        Class classz = getClass();
        Type genericSuperclass = classz.getGenericSuperclass();
        ParameterizedType parameterized = (ParameterizedType) genericSuperclass;
        return mGson.fromJson(string, parameterized.getActualTypeArguments()[0]);
    }
}
