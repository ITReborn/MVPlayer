package com.itheima.mvplayer32.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mvplayer32.R;
import com.itheima.mvplayer32.app.Constant;
import com.itheima.mvplayer32.model.MusicListItemBean;
import com.itheima.mvplayer32.model.MusicManager;
import com.itheima.mvplayer32.service.MusicPlayerService;
import com.itheima.mvplayer32.utils.TimeUtils;
import com.itheima.mvplayer32.widget.LyricView;

import butterknife.BindView;
import butterknife.OnClick;

public class MusicPlayerActivity extends BaseActivity {
    public static final String TAG = "MusicPlayerActivity";
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.artist)
    TextView mArtist;
    @BindView(R.id.animation)
    ImageView mAnimation;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.play_mode)
    ImageView mPlayMode;
    @BindView(R.id.pre)
    ImageView mPre;
    @BindView(R.id.play)
    ImageView mPlay;
    @BindView(R.id.next)
    ImageView mNext;
    @BindView(R.id.music_list)
    ImageView mMusicList;
    @BindView(R.id.lyric)
    LyricView mLyricView;

    private MusicPlayerService.MusicPlayerProxy mMusicPlayerProxy;

    private Handler mHandler = new Handler();

    private static final int UPDATE_TIME_INTERVAL = 1000;
    private static final int UPDATE_LYRIC_INTERVAL = 100;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_music_player;
    }

    @Override
    protected void init() {
        super.init();
//        mTitle.setText(getIntent().getStringExtra(Constant.Extra.MUSIC_TITLE));
//        mArtist.setText(getIntent().getStringExtra(Constant.Extra.MUSIC_ARTIST));
        int position = getIntent().getIntExtra(Constant.Extra.MUSIC_POSITION, -1);
        MusicListItemBean item = MusicManager.getInstance().getItem(position);
        mTitle.setText(item.getTitle());
        mArtist.setText(item.getArtist());
        //根据display name来查找歌词文件
        String displayName = item.getDisplayName();
        //beijingbeijing.mp3
        String[] split = displayName.split("\\.");
        //beijingbeijing  mp3
        mLyricView.setLyricFileName(split[0]);

        int progress = 0;//当前的播放的进度
        long duration = item.getDuration();
        String time = TimeUtils.parseTime(progress) + "/" + TimeUtils.parseTime((int) duration);
        mTime.setText(time);
        mSeekBar.setMax((int) duration);//设置seekbar最大值
        mSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);

        Intent intent = new Intent(this, MusicPlayerService.class);
        intent.putExtra(Constant.Extra.MUSIC_POSITION, position);
        startService(intent);

        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.Action.START_PLAY);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int position = intent.getIntExtra(Constant.Extra.MUSIC_POSITION, -1);//当前播放歌曲的位置
            MusicListItemBean item = MusicManager.getInstance().getItem(position);
            mTitle.setText(item.getTitle());
            mArtist.setText(item.getArtist());
            updateTime();
            //更新界面开始播放歌曲
            startAnimation();
            //开始更新时间
            startUpdateTime();
            //开始更新歌词
            startUpdateLyric();
        }
    };

    private void startUpdateLyric() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLyricView.roll(mMusicPlayerProxy.getProgress(), mMusicPlayerProxy.getDuration());
                startUpdateLyric();
            }
        }, UPDATE_LYRIC_INTERVAL);
    }

    private void startUpdateTime() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTime();
                startUpdateTime();//循环刷新
            }
        }, UPDATE_TIME_INTERVAL);

    }

    private void updateTime() {
        int progress = mMusicPlayerProxy.getProgress();//当前的播放的进度
        int duration = mMusicPlayerProxy.getDuration();
        Log.d(TAG, "startUpdateTime: " + progress + " " +duration);
        String time = TimeUtils.parseTime(progress) + "/" + TimeUtils.parseTime(duration);
        mTime.setText(time);
        //更新seekbar
        mSeekBar.setProgress(progress);
    }

    private void startAnimation() {
        //在布局中配置scr，直接getDrawable获取动画
        AnimationDrawable drawable = (AnimationDrawable) mAnimation.getDrawable();
        drawable.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @OnClick({R.id.back, R.id.play_mode, R.id.pre, R.id.play, R.id.next, R.id.music_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.play_mode:
                mMusicPlayerProxy.switchPlayMode();
                updatePlayModeView(mMusicPlayerProxy.getCurrentMode());
                break;
            case R.id.pre:
                if (mMusicPlayerProxy.isFirst()) {
                    Toast.makeText(this, getString(R.string.first_music), Toast.LENGTH_SHORT).show();
                } else {
                    mMusicPlayerProxy.playPre();//播放上一首
                }
                break;
            case R.id.play:
                mMusicPlayerProxy.togglePlay();
                if (mMusicPlayerProxy.isPlaying()) {
                    updateStartPlay();
                } else {
                    updatePausePlay();
                }
                break;
            case R.id.next:
                if (mMusicPlayerProxy.isLast()) {
                    Toast.makeText(this, getString(R.string.last_music), Toast.LENGTH_SHORT).show();
                } else {
                    mMusicPlayerProxy.playNext();
                }
                break;
            case R.id.music_list:
                finish();
                break;
        }
    }

    private void updatePlayModeView(int currentMode) {
        switch (currentMode) {
            case MusicPlayerService.PLAY_MODE_ORDER:
                mPlayMode.setImageResource(R.drawable.selector_btn_playmode_order);
                break;
            case MusicPlayerService.PLAY_MODE_RANDOM:
                mPlayMode.setImageResource(R.drawable.selector_btn_playmode_random);
                break;
            case MusicPlayerService.PLAY_MODE_SINGLE:
                mPlayMode.setImageResource(R.drawable.selector_btn_playmode_single);
                break;
        }
    }

    private void updatePausePlay() {
        mPlay.setImageResource(R.drawable.selector_btn_audio_pause);
        stopAnimation();
    }

    private void stopAnimation() {
        //在布局中配置scr，直接getDrawable获取动画
        AnimationDrawable drawable = (AnimationDrawable) mAnimation.getDrawable();
        drawable.stop();
    }

    /**
     * 当开始播放时，更新界面
     */
    private void updateStartPlay() {
        mPlay.setImageResource(R.drawable.selector_btn_audio_play);
        startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MusicPlayerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mServiceConnection);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicPlayerProxy = (MusicPlayerService.MusicPlayerProxy) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //只处理用户拖动触发seekbar的change
            if (fromUser) {
                //设置播放进度
                mMusicPlayerProxy.setProgress(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mMusicPlayerProxy.pause();//开始拖动时暂停播放
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mMusicPlayerProxy.start();//拖动结束开始播放
        }
    };
}
