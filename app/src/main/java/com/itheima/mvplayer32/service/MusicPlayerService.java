package com.itheima.mvplayer32.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.itheima.mvplayer32.app.Constant;
import com.itheima.mvplayer32.model.MusicListItemBean;
import com.itheima.mvplayer32.model.MusicManager;

import java.io.IOException;
import java.util.Random;

public class MusicPlayerService extends Service {
    public static final String TAG = "MusicPlayerService";

    private MusicPlayerProxy mMusicPlayerProxy;
    private MediaPlayer mMediaPlayer;
    private int mCurrentPosition; //当前播放歌曲在音乐列表中的位置

    public static final int PLAY_MODE_ORDER = 0;
    public static final int PLAY_MODE_RANDOM = 1;
    public static final int PLAY_MODE_SINGLE = 2;

    private int mCurrentMode = PLAY_MODE_ORDER;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMusicPlayerProxy;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMusicPlayerProxy = new MusicPlayerProxy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        int position = intent.getIntExtra(Constant.Extra.MUSIC_POSITION, -1);
        mCurrentPosition = position;
        startPlay();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startPlay() {
        MusicListItemBean item = MusicManager.getInstance().getItem(mCurrentPosition);
        //如果以前有mediaplayer在播放歌曲，重置MediaPlayer 释放资源
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        //播放音乐
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(item.getData());
            mMediaPlayer.prepareAsync();//异步准备
            mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            //播放结束后根据播放模式播放下一首歌
            playNextByMode();
        }
    };

    private void playNextByMode() {
        switch (mCurrentMode) {
            case PLAY_MODE_ORDER:
                mCurrentPosition = (mCurrentPosition + 1) % MusicManager.getInstance().getMusicCount();
                break;
            case PLAY_MODE_RANDOM:
                mCurrentPosition = new Random().nextInt(MusicManager.getInstance().getMusicCount());
                break;
            case PLAY_MODE_SINGLE:
                break;
        }
        startPlay();
    }


    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
            notifyStartPlay();
        }
    };

    private void notifyStartPlay() {
        Intent intent = new Intent();
        intent.setAction(Constant.Action.START_PLAY);
        intent.putExtra(Constant.Extra.MUSIC_POSITION, mCurrentPosition);
        sendBroadcast(intent);
    }

    public class MusicPlayerProxy extends Binder {

        /**
         * 播放或者暂停
         */
        public void togglePlay() {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            } else {
                mMediaPlayer.start();
            }
        }

        public boolean isPlaying() {
            return mMediaPlayer.isPlaying();
        }

        public void playNext() {
            mCurrentPosition ++;
            startPlay();
        }

        public boolean isLast() {
            return mCurrentPosition == MusicManager.getInstance().getMusicCount() - 1;
        }

        public boolean isFirst() {
            return mCurrentPosition == 0;
        }

        public void playPre() {
            mCurrentPosition --;
            startPlay();
        }

        /**
         * 返回当前播放的进度
         * @return
         */
        public int getProgress() {
            return mMediaPlayer.getCurrentPosition();
        }

        /**
         * 返回歌曲的时长
         * @return
         */
        public int getDuration() {
            return mMediaPlayer.getDuration();
        }

        public void setProgress(int progress) {
            mMediaPlayer.seekTo(progress);
        }

        public void pause() {
            mMediaPlayer.pause();
        }

        public void start() {
            mMediaPlayer.start();
        }

        public void switchPlayMode() {
            mCurrentMode = (mCurrentMode + 1) % 3;
        }

        public int getCurrentMode() {
            return mCurrentMode;
        }
    }
}
