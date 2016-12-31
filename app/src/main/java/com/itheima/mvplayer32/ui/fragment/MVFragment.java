package com.itheima.mvplayer32.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.itheima.mvplayer32.R;
import com.itheima.mvplayer32.adapter.MVAdapter;
import com.itheima.mvplayer32.model.AreaBean;
import com.itheima.mvplayer32.network.MVAreaRequest;
import com.itheima.mvplayer32.network.NetworkListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MVFragment extends BaseFragment {
    public static final String TAG = "HomeFragment";
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private MVAdapter mMVAdapter;

    private List<AreaBean> mListData;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mv;
    }

    @Override
    protected void init() {
        super.init();
        mListData = new ArrayList<AreaBean>();
        mMVAdapter = new MVAdapter(getChildFragmentManager(), mListData);
        mViewPager.setAdapter(mMVAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        loadAreaData();
    }

    private void loadAreaData() {
        MVAreaRequest.getMVAreaRequest(mListNetworkListener).execute();
    }

    private NetworkListener<List<AreaBean>> mListNetworkListener = new NetworkListener<List<AreaBean>>() {
        @Override
        public void onFailed(String s) {

        }

        @Override
        public void onSuccess(List<AreaBean> result) {
            mListData.addAll(result);
            mMVAdapter.notifyDataSetChanged();
        }
    };
}
