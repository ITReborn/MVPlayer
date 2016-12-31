package com.itheima.mvplayer32.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.itheima.mvplayer32.model.MusicListItemBean;
import com.itheima.mvplayer32.widget.MusicListItemView;

import java.util.List;

public class MusicListAdapter extends BaseAdapter {
    public static final String TAG = "MusicListAdapter";

    private Context mContext;
    private List<MusicListItemBean> mDataList;

    public MusicListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MusicListItemViewHolder viewHolder;
        if (convertView == null) {
            convertView = new MusicListItemView(mContext);
            viewHolder = new MusicListItemViewHolder((MusicListItemView) convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MusicListItemViewHolder) convertView.getTag();
        }
        viewHolder.mMusicListItemView.bindView(mDataList.get(position));
        convertView = viewHolder.mMusicListItemView;

        return convertView;
    }

    public void setDataList(List<MusicListItemBean> dataList) {
        mDataList = dataList;
        //通知更新列表
        notifyDataSetChanged();
    }

    private class MusicListItemViewHolder {
        private MusicListItemView mMusicListItemView;
        public MusicListItemViewHolder(MusicListItemView itemView) {
            mMusicListItemView = itemView;
        }
    }
}
