package com.itheima.mvplayer32.presenter.impl;

import android.util.Log;

import com.itheima.mvplayer32.model.MVPageBean;
import com.itheima.mvplayer32.network.MVPageRequest;
import com.itheima.mvplayer32.network.NetworkListener;
import com.itheima.mvplayer32.presenter.BaseListPresenter;
import com.itheima.mvplayer32.view.BaseListView;

import java.util.ArrayList;
import java.util.List;

public class MVPagePresenter implements BaseListPresenter<MVPageBean.VideosBean> {
    public static final String TAG = "MVPagePresenter";

    private BaseListView mBaseListView;
    private String mArea;

    private List<MVPageBean.VideosBean> mDataList;

    public MVPagePresenter(BaseListView view, String area) {
        mBaseListView = view;
        mArea = area;
        mDataList = new ArrayList<MVPageBean.VideosBean>();
    }

    @Override
    public void loadDataList() {
        Log.d(TAG, "loadDataList: ");
        MVPageRequest.getMVPageRequest(mArea, 0, mNetworkListener).execute();
    }

    @Override
    public void refresh() {
        mDataList.clear();
        loadDataList();
    }

    @Override
    public void loadMoreData() {
        MVPageRequest.getMVPageRequest(mArea, mDataList.size(), mNetworkListener).execute();
    }

    @Override
    public List<MVPageBean.VideosBean> getDataList() {
        return mDataList;
    }

    private NetworkListener<MVPageBean> mNetworkListener = new NetworkListener<MVPageBean>() {
        @Override
        public void onFailed(String s) {
            Log.d(TAG, "onFailed: " + s);
        }

        @Override
        public void onSuccess(MVPageBean result) {
            //先保存数据，再更新view层
            mDataList.addAll(result.getVideos());
            mBaseListView.onDataLoaded();
        }
    };
}
