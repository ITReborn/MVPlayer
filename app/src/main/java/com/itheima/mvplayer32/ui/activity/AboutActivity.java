package com.itheima.mvplayer32.ui.activity;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.itheima.mvplayer32.R;

public class AboutActivity extends BaseActivity{
    public static final String TAG = "AboutActivity";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {
        super.init();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.about);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
