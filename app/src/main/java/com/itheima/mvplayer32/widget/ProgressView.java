package com.itheima.mvplayer32.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.itheima.mvplayer32.R;

public class ProgressView extends RelativeLayout {
    public static final String TAG = "ProgressView";

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_progress, this);
    }
}
