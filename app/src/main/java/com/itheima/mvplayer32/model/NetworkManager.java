package com.itheima.mvplayer32.model;

import android.os.Handler;
import android.os.Looper;

import com.itheima.mvplayer32.network.MVPlayer32Request;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * NetworkManager 持有一个OkHttpClient对象来发送网络请求，处理网络结果的线程的切换
 */
public class NetworkManager {

    public static final String TAG = "NetworkManager";

    public static NetworkManager sNetworkManager;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private NetworkManager() {
        mOkHttpClient = new OkHttpClient();
    }

    private OkHttpClient mOkHttpClient;

    public static NetworkManager getInstance() {
        if (sNetworkManager == null) {
            synchronized (NetworkManager.class) {
                if (sNetworkManager == null) {
                    sNetworkManager = new NetworkManager();
                }
            }
        }
        return sNetworkManager;
    }

    public void sendRequest(final MVPlayer32Request mvPlayer32Request) {
        final Request request = new Request.Builder().url(mvPlayer32Request.getUrl()).get().build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mvPlayer32Request.getNetworkListener().onFailed(e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //解析结果在在子线程做
                final Object o = mvPlayer32Request.parseNetworkResponse(response.body().string());
                //回调网络请求成功，传入解析后的结果
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mvPlayer32Request.getNetworkListener().onSuccess(o);
                    }
                });
            }
        });
    }
}
