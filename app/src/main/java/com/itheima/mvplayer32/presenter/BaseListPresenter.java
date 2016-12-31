package com.itheima.mvplayer32.presenter;

import java.util.List;

public interface BaseListPresenter<T> {

    void loadDataList();

    void refresh();

    void loadMoreData();

    List<T> getDataList();
}
