package com.itheima.mvplayer32.presenter;

import com.itheima.mvplayer32.model.HomeListItemBean;

import java.util.List;

public interface HomePresenter {
    //定义主要业务逻辑
    void loadDataList();

    void refresh();

    void loadMoreData();

    List<HomeListItemBean> getDataList();
}
