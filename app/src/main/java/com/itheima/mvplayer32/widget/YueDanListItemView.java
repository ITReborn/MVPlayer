package com.itheima.mvplayer32.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.mvplayer32.R;
import com.itheima.mvplayer32.model.YueDanBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class YueDanListItemView extends RelativeLayout {
    public static final String TAG = "YueDanListItemView";
    @BindView(R.id.mv_img)
    ImageView mMvImg;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.creator)
    TextView mCreator;
    @BindView(R.id.mv_count)
    TextView mMvCount;

    public YueDanListItemView(Context context) {
        this(context, null);
    }

    public YueDanListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_yue_dan_list_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(YueDanBean.PlayListsBean playListsBean) {
        Glide.with(getContext()).load(playListsBean.getPlayListPic()).centerCrop().into(mMvImg);
        Glide.with(getContext()).load(playListsBean.getCreator().getLargeAvatar())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(mAvatar);
        mTitle.setText(playListsBean.getTitle());
        mCreator.setText(playListsBean.getCreator().getNickName());
        String count = String.format(getContext().getString(R.string.mv_count), playListsBean.getVideoCount());
        mMvCount.setText(count);
    }
}
