package com.itheima.mvplayer32.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.itheima.mvplayer32.adapter.YueDanAdapter;
import com.itheima.mvplayer32.presenter.BaseListPresenter;
import com.itheima.mvplayer32.presenter.impl.YueDanPresenterImpl;
import com.itheima.mvplayer32.view.BaseListView;

public class YueDanFragment extends BaseListFragment {

    private YueDanPresenterImpl mYueDanPresenter;
    @Override
    protected BaseListPresenter getPresenter(BaseListView listView) {
        mYueDanPresenter = new YueDanPresenterImpl(listView);
        return mYueDanPresenter;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new YueDanAdapter(getContext(), mYueDanPresenter.getDataList());
    }
}
