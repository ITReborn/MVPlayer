package com.itheima.mvplayer32.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.itheima.mvplayer32.model.HomeListItemBean;
import com.itheima.mvplayer32.widget.HomeListItemView;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeListItemViewHolder> {
    public static final String TAG = "HomeListAdapter";

    private Context mContext;

    private List<HomeListItemBean> mList;

    public HomeListAdapter(Context context) {
        mContext = context;
    }

    public HomeListAdapter(Context context, List<HomeListItemBean> listData) {
        mContext = context;
        mList = listData;
    }

    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public HomeListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeListItemViewHolder(new HomeListItemView(mContext));
    }

    /**
     * 绑定ViewHolder 用对应position位置的数据绑定Viewholder hold住的视图
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(HomeListItemViewHolder holder, int position) {
        holder.mHomeListItemView.bindView(mList.get(position));
    }

    /**
     * 返回条目的个数
     * @return
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HomeListItemViewHolder extends RecyclerView.ViewHolder {

        private HomeListItemView mHomeListItemView;


        public HomeListItemViewHolder(HomeListItemView itemView) {
            super(itemView);
            mHomeListItemView = itemView;
        }
    }
}
