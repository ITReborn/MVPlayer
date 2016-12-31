package com.itheima.mvplayer32.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.itheima.mvplayer32.model.AreaBean;
import com.itheima.mvplayer32.ui.fragment.MVPageFragment;

import java.util.List;

public class MVAdapter extends FragmentStatePagerAdapter {
    public static final String TAG = "MVAdapter";

    private List<AreaBean> mAreaBeanList;

    public MVAdapter(FragmentManager fm, List<AreaBean> list) {
        super(fm);
        mAreaBeanList = list;
    }


    @Override
    public Fragment getItem(int position) {
        return MVPageFragment.newInstance(mAreaBeanList.get(position).getCode());
    }

    @Override
    public int getCount() {
        return mAreaBeanList.size();
    }

    /**
     * 返回TabLayout的标题
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mAreaBeanList.get(position).getName();
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }
}
