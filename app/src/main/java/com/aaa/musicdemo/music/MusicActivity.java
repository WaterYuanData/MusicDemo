package com.aaa.musicdemo.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aaa.musicdemo.R;
import com.aaa.musicdemo.databinding.ActivityMusicBinding;
import com.aaa.musicdemo.music.bean.MusicDataViewModel;
import com.aaa.musicdemo.qqmusic.QQMusicDataViewModel;

public class MusicActivity extends AppCompatActivity {
    private static final String TAG = "MusicActivity";
    private ActivityMusicBinding mBinding;
    private MusicDataViewModel mMusicDataViewModel;
    private QQMusicDataViewModel mQQMusicDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_music);
        mMusicDataViewModel = ViewModelProviders.of(this).get(MusicDataViewModel.class);
        mQQMusicDataViewModel = ViewModelProviders.of(this).get(QQMusicDataViewModel.class);
        Log.i(TAG, "onCreate: ");

        //        mMusicDataViewModel.loadMusicData();
        //        mMusicDataViewModel.doPlay();
    }

    public void doStop(View view) {
        //        mMusicDataViewModel.doPause();
        mQQMusicDataViewModel.doPause();
    }

    public void doPlay(View view) {
        mQQMusicDataViewModel.doPlay();
    }

    public void doQuery(View view) {
        mQQMusicDataViewModel.queryQQMusicData();
    }
}
