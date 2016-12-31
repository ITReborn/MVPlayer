package com.itheima.mvplayer32.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.itheima.mvplayer32.model.MusicListItemBean;
import com.itheima.mvplayer32.widget.MusicListItemView;

public class MusicListCursorAdapter extends CursorAdapter {
    public static final String TAG = "MusicListCursorAdapter";

    public MusicListCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return new MusicListItemView(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MusicListItemBean itemBean = MusicListItemBean.parseFromCursor(cursor);
        ((MusicListItemView)view).bindView(itemBean);
    }
}
