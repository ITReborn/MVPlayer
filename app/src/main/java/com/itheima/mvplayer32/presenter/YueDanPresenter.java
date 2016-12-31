package com.itheima.mvplayer32.presenter;

import com.itheima.mvplayer32.model.YueDanBean;

import java.util.List;

public interface YueDanPresenter {

    void loadDataList();

    void refresh();

    void loadMoreData();

    List<YueDanBean.PlayListsBean> getDataList();
}
