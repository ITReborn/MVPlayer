package com.itheima.mvplayer32.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.itheima.mvplayer32.adapter.MVPageAdapter;
import com.itheima.mvplayer32.presenter.BaseListPresenter;
import com.itheima.mvplayer32.presenter.impl.MVPagePresenter;
import com.itheima.mvplayer32.view.BaseListView;

public class MVPageFragment extends BaseListFragment {

    private String mCode;

    private MVPagePresenter mMVPagePresenter;

    public static MVPageFragment newInstance(String code) {
        MVPageFragment fragment = new MVPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mCode = (String) arguments.get("code");
    }

    @Override
    protected BaseListPresenter getPresenter(BaseListView listView) {
        mMVPagePresenter = new MVPagePresenter(listView, mCode);
        return mMVPagePresenter;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new MVPageAdapter(getContext(), mMVPagePresenter.getDataList());
    }
}
