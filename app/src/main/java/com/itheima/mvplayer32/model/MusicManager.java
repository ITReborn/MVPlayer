package com.itheima.mvplayer32.model;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import com.itheima.mvplayer32.adapter.MusicListCursorAdapter;
import com.itheima.mvplayer32.utils.MusicQueryHandler;

/**
 * 单例模式 管理音乐数据的模块
 */
public class MusicManager {
    public static final String TAG = "MusicManager";

    private static MusicManager sMusicManager;
    private MusicQueryHandler mMusicQueryHandler;

    private MusicManager() {}

    public static MusicManager getInstance() {
        if (sMusicManager == null) {
            synchronized (MusicManager.class ) {
                if (sMusicManager == null) {
                    sMusicManager = new MusicManager();
                }
            }
        }
        return sMusicManager;
    }

    public void loadMusic(Context context, MusicListCursorAdapter adapter) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA};//DATA是音频文件的路径
//        Cursor query = resolver.query(uri, projection, null, null, null);
//        PrintCursorUtils.printCursor(query);
        mMusicQueryHandler = new MusicQueryHandler(resolver);
        //token 标记查询
        mMusicQueryHandler.startQuery(0, adapter, uri, projection, null, null, null);

    }


    public MusicListItemBean getItem(int position) {
        return mMusicQueryHandler.getItem(position);
    }

    public int getMusicCount() {
        return mMusicQueryHandler.getCount();
    }
}
