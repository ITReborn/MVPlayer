package com.itheima.mvplayer32.factory;

import android.support.v4.app.Fragment;

import com.itheima.mvplayer32.R;
import com.itheima.mvplayer32.ui.fragment.HomeFragment;
import com.itheima.mvplayer32.ui.fragment.MVFragment;
import com.itheima.mvplayer32.ui.fragment.VBangFragment;
import com.itheima.mvplayer32.ui.fragment.YueDanFragment;

public class FragmentFactory {
    public static final String TAG = "FragmentFactory";

    public static FragmentFactory sFragmentFactory;
    private Fragment mHomeFragment;
    private Fragment mMVFragment;
    private Fragment mVBangFragment;
    private Fragment mYueDanFragment;


    private FragmentFactory() {}

    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (sFragmentFactory == null) {
                    sFragmentFactory = new FragmentFactory();
                }
            }
        }
        return sFragmentFactory;
    }

    public Fragment getFragment(int tabId) {
        switch (tabId) {
            case R.id.tab_home:
                return getHomeFragment();
            case R.id.tab_mv:
                return getMVFragment();
            case R.id.tab_vbang:
                return getVBangFragment();
            case R.id.tab_yue_dan:
                return getYueDanFragment();
        }
        return null;
    }

    public Fragment getHomeFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        return mHomeFragment;
    }

    public Fragment getMVFragment() {
        if (mMVFragment == null) {
            mMVFragment = new MVFragment();
        }
        return mMVFragment;
    }

    public Fragment getVBangFragment() {
        if (mVBangFragment == null) {
            mVBangFragment = new VBangFragment();
        }
        return mVBangFragment;
    }

    public Fragment getYueDanFragment() {
        if (mYueDanFragment == null) {
            mYueDanFragment = new YueDanFragment();
        }
        return mYueDanFragment;
    }
}
