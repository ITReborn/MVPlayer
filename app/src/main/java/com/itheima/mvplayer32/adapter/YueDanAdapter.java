package com.itheima.mvplayer32.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.mvplayer32.model.YueDanBean;
import com.itheima.mvplayer32.widget.ProgressView;
import com.itheima.mvplayer32.widget.YueDanListItemView;

import java.util.List;

public class YueDanAdapter extends RecyclerView.Adapter {
    public static final String TAG = "YueDanAdapter";

    private Context mContext;
    private List<YueDanBean.PlayListsBean> mPlayListsBeanList;

    private static final int ITEM_TYPE_NORMAL = 0;
    private static final int ITEM_TYPE_PROGRESS = 1;

    public YueDanAdapter(Context context, List<YueDanBean.PlayListsBean> listsBeen) {
        mContext = context;
        mPlayListsBeanList = listsBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            return new YueDanListItemViewHolder(new YueDanListItemView(mContext));
        } else {
            return new ProgressViewHolder(new ProgressView(mContext));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_NORMAL) {
            ((YueDanListItemView)(holder.itemView)).bindView(mPlayListsBeanList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mPlayListsBeanList.size() == 0) {
            return 0;
        }
        return mPlayListsBeanList.size() + 1;
    }

    /**
     * 根据位置返回不同item的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == mPlayListsBeanList.size()) {
            return ITEM_TYPE_PROGRESS;
        }
        return ITEM_TYPE_NORMAL;
    }

    public class YueDanListItemViewHolder extends RecyclerView.ViewHolder {

        private YueDanListItemView mYueDanListItemView;

        public YueDanListItemViewHolder(YueDanListItemView itemView) {
            super(itemView);
            mYueDanListItemView = itemView;
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }


}
