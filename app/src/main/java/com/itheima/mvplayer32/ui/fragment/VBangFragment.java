package com.itheima.mvplayer32.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.itheima.mvplayer32.R;
import com.itheima.mvplayer32.adapter.MusicListCursorAdapter;
import com.itheima.mvplayer32.app.Constant;
import com.itheima.mvplayer32.model.MusicManager;
import com.itheima.mvplayer32.ui.activity.MusicPlayerActivity;

import butterknife.BindView;

public class VBangFragment extends BaseFragment {
    public static final String TAG = "HomeFragment";
    @BindView(R.id.list_view)
    ListView mListView;

//    private MusicListAdapter mMusicListAdapter;

    private MusicListCursorAdapter mMusicListAdapter;

    private static final int  REQUEST_READ_EXTERNAL_STORAGE = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_vbang;
    }

    @Override
    protected void init() {
        super.init();
        mMusicListAdapter = new MusicListCursorAdapter(getContext(), null, false);
        mListView.setAdapter(mMusicListAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
        //查询手机数据库
        if (checkIfHasReadExternalStoragePermission()) {
            MusicManager.getInstance().loadMusic(getContext(), mMusicListAdapter);
        } else {
            requestReadExteranlStoragePermission();
        }
    }

    private void requestReadExteranlStoragePermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_READ_EXTERNAL_STORAGE);
    }

    private boolean checkIfHasReadExternalStoragePermission() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MusicManager.getInstance().loadMusic(getContext(), mMusicListAdapter);
                } else {
                    Toast.makeText(getContext(), "权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getContext(), MusicPlayerActivity.class);
//            Cursor cursor = (Cursor) mMusicListAdapter.getItem(position);//获取对应位置的cursor
//            MusicListItemBean itemBean = MusicListItemBean.parseFromCursor(cursor);
//            String path = itemBean.getData();
//            String title = itemBean.getTitle();
//            String artist = itemBean.getArtist();
//            //传入歌曲的path
//            intent.putExtra(Constant.Extra.MUSIC_DATA, path);
//            intent.putExtra(Constant.Extra.MUSIC_TITLE, title);
//            intent.putExtra(Constant.Extra.MUSIC_ARTIST, artist);
            intent.putExtra(Constant.Extra.MUSIC_POSITION, position);
            startActivity(intent);
        }
    };
}
