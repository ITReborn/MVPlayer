package com.itheima.mvplayer32.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.mvplayer32.R;

public class SettingsActivity extends BaseActivity{
    public static final String TAG = "SettingsActivity";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void init() {
        super.init();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.settings);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean push_notification = defaultSharedPreferences.getBoolean("push_notification", false);
        Log.d(TAG, "init: " + push_notification);
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

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            addPreferencesFromResource(R.xml.settings_pref);
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            if (preference.getKey().equals("about")) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
    }
}
