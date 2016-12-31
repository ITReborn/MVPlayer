package com.itheima.mvplayer32.presenter.impl;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.itheima.mvplayer32.model.HomeListItemBean;
import com.itheima.mvplayer32.network.HomeRequest;
import com.itheima.mvplayer32.network.NetworkListener;
import com.itheima.mvplayer32.presenter.BaseListPresenter;
import com.itheima.mvplayer32.view.BaseListView;

import java.util.ArrayList;
import java.util.List;

public class HomePresenterImpl implements BaseListPresenter<HomeListItemBean> {
    public static final String TAG = "HomePresenterImpl";

    private List<HomeListItemBean> mListData;

    //双向绑定，P层持有View层引用
    private BaseListView mHomeView;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Gson mGson;


    public HomePresenterImpl(BaseListView view) {
        mListData = new ArrayList<HomeListItemBean>();
        mGson  = new Gson();
        mHomeView = view;
    }

    @Override
    public void loadDataList() {
        loadHomeData(0);
    }


    @Override
    public void refresh() {
        //清空当前数据集合
        mListData.clear();
        loadHomeData(0);
    }

    @Override
    public void loadMoreData() {
        loadHomeData(mListData.size());
    }

    @Override
    public List<HomeListItemBean> getDataList() {
        return mListData;
    }

    private void loadHomeData(int offset) {
        HomeRequest.getHomeRequest(offset, mListNetworkListener).execute();

//        new MVPlayer32Request<List<HomeListItemBean>>(URLProviderUtils.getHomeUrl(0, 10), mListNetworkListener).execute();
    }

    private NetworkListener<List<HomeListItemBean>> mListNetworkListener = new NetworkListener<List<HomeListItemBean>>() {

        /**
         * 在主线程回调失败
         * @param s
         */
        @Override
        public void onFailed(String s) {
            mHomeView.onDataLoadFailed(s);
        }

        /**
         * 在主线程回调成功的结果
         * @param result
         */
        @Override
        public void onSuccess(List<HomeListItemBean> result) {
            mListData.addAll(result);
            mHomeView.onDataLoaded();
        }
    };
}
