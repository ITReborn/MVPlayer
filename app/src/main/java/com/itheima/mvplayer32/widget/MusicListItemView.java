package com.itheima.mvplayer32.widget;

import android.content.Context;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.mvplayer32.R;
import com.itheima.mvplayer32.model.MusicListItemBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicListItemView extends RelativeLayout {
    public static final String TAG = "MusicListItemView";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.artist)
    TextView mArtist;
    @BindView(R.id.size)
    TextView mSize;

    public MusicListItemView(Context context) {
        this(context, null);
    }

    public MusicListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_music_list_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(MusicListItemBean musicListItemBean) {
        mTitle.setText(musicListItemBean.getTitle());
        mArtist.setText(musicListItemBean.getArtist());
//        mSize.setText(String.valueOf(musicListItemBean.getSize()));
        mSize.setText(Formatter.formatFileSize(getContext(), musicListItemBean.getSize()));
    }
}
