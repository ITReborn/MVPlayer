package com.itheima.mvplayer32.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.itheima.mvplayer32.adapter.HomeListAdapter;
import com.itheima.mvplayer32.presenter.BaseListPresenter;
import com.itheima.mvplayer32.presenter.impl.HomePresenterImpl;
import com.itheima.mvplayer32.view.BaseListView;

public class HomeFragment extends BaseListFragment {

    private HomePresenterImpl mHomePresenter;

    @Override
    protected BaseListPresenter getPresenter(BaseListView view) {
        mHomePresenter = new HomePresenterImpl(view);
        return mHomePresenter;
    }



    @Override
    public RecyclerView.Adapter getAdapter() {
        return new HomeListAdapter(getContext(), mHomePresenter.getDataList());
    }
}
