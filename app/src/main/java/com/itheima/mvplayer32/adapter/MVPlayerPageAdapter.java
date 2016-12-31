package com.itheima.mvplayer32.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itheima.mvplayer32.ui.fragment.MVCommentFragment;
import com.itheima.mvplayer32.ui.fragment.MVDescriptionFragment;
import com.itheima.mvplayer32.ui.fragment.RelativeMVFragment;

public class MVPlayerPageAdapter extends FragmentPagerAdapter {
    public static final String TAG = "MVPlayerPageAdapter";

    public MVPlayerPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MVDescriptionFragment();
            case 1:
                return new MVCommentFragment();
            case 2:
                return new RelativeMVFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
