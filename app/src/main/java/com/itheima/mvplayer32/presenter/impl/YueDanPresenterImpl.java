package com.itheima.mvplayer32.presenter.impl;

import com.itheima.mvplayer32.model.YueDanBean;
import com.itheima.mvplayer32.network.NetworkListener;
import com.itheima.mvplayer32.network.YueDanRequest;
import com.itheima.mvplayer32.presenter.BaseListPresenter;
import com.itheima.mvplayer32.view.BaseListView;

import java.util.ArrayList;
import java.util.List;

public class YueDanPresenterImpl implements BaseListPresenter<YueDanBean.PlayListsBean> {

    private static final String TAG = "YueDanPresenterImpl";

    public BaseListView mYueDanView;

    private List<YueDanBean.PlayListsBean> mDataList;

    public YueDanPresenterImpl(BaseListView view) {
        mYueDanView = view;
        mDataList = new ArrayList<YueDanBean.PlayListsBean>();
    }

    @Override
    public void loadDataList() {
        YueDanRequest.getYueDanRequest(0, mYueDanBeanNetworkListener).execute();
    }

    private NetworkListener<YueDanBean> mYueDanBeanNetworkListener = new NetworkListener<YueDanBean>() {
        @Override
        public void onFailed(String s) {
            mYueDanView.onDataLoadFailed(s);
        }

        @Override
        public void onSuccess(YueDanBean result) {
            mDataList.addAll(result.getPlayLists());
            //通知view层更新
            mYueDanView.onDataLoaded();
        }
    };

    @Override
    public void refresh() {
        mDataList.clear();
        loadDataList();
    }

    @Override
    public void loadMoreData() {
        YueDanRequest.getYueDanRequest(mDataList.size(), mYueDanBeanNetworkListener).execute();
    }

    @Override
    public List<YueDanBean.PlayListsBean> getDataList() {
        return mDataList;
    }
}
