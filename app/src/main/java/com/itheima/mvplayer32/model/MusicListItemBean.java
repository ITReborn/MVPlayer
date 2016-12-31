package com.itheima.mvplayer32.model;

import android.database.Cursor;
import android.provider.MediaStore;

public class MusicListItemBean {
    public static final String TAG = "MusicListItemBean";

    public int id;
    public String title;
    public String artist;
    public String displayName;
    public long size;
    public long duration;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String data;

    public static MusicListItemBean parseFromCursor(Cursor cursor) {
        MusicListItemBean itemBean = new MusicListItemBean();
        itemBean.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
        itemBean.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
        itemBean.setDisplayName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
        itemBean.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
        itemBean.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
        itemBean.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
        itemBean.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
        return itemBean;
    }
}
