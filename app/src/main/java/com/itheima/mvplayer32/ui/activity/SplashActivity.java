package com.itheima.mvplayer32.ui.activity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.itheima.mvplayer32.R;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    public static final String TAG = "SplashActivity";
    @BindView(R.id.imageView)
    ImageView mImageView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        super.init();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        animation.setAnimationListener(mAnimationListener);
        mImageView.startAnimation(animation);
    }

    private Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            goTo(MainActivity.class);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

}
