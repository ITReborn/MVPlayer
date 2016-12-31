package com.itheima.mvplayer32.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.mvplayer32.app.Constant;
import com.itheima.mvplayer32.model.MVPageBean;
import com.itheima.mvplayer32.ui.activity.MVPlayerActivity;
import com.itheima.mvplayer32.widget.MVPageListItemView;

import java.util.List;

public class MVPageAdapter extends RecyclerView.Adapter<MVPageAdapter.MVPageListItemViewHolder> {
    public static final String TAG = "MVPageAdapter";

    private Context mContext;
    private List<MVPageBean.VideosBean> mVideosBeanList;

    public MVPageAdapter(Context context, List<MVPageBean.VideosBean> list) {
        mContext = context;
        mVideosBeanList = list;
    }

    @Override
    public MVPageListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MVPageListItemViewHolder(new MVPageListItemView(mContext));
    }

    @Override
    public void onBindViewHolder(MVPageListItemViewHolder holder, final int position) {
        holder.mMVPageListItemView.bindView(mVideosBeanList.get(position));
        holder.mMVPageListItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MVPlayerActivity.class);
                intent.putExtra(Constant.Extra.MV_TITLE, mVideosBeanList.get(position).getTitle());
                intent.putExtra(Constant.Extra.MV_URL, mVideosBeanList.get(position).getUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideosBeanList.size();
    }

    public class MVPageListItemViewHolder extends RecyclerView.ViewHolder {

        private MVPageListItemView mMVPageListItemView;

        public MVPageListItemViewHolder(MVPageListItemView itemView) {
            super(itemView);
            mMVPageListItemView = itemView;
        }
    }
}
