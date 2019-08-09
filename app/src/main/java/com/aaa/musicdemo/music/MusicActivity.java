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

public class MusicActivity extends AppCompatActivity {
    private static final String TAG = "MusicActivity";
    private ActivityMusicBinding mBinding;
    private MusicDataViewModel mMusicDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_music);
        mMusicDataViewModel = ViewModelProviders.of(this).get(MusicDataViewModel.class);
        Log.i(TAG, "onCreate: ");
    }

    public void doQuery(View view) {
        mMusicDataViewModel.loadMusicData();
    }

    public void doPlay(View view) {
        mMusicDataViewModel.doPlay();
    }

    public void doStop(View view) {
        mMusicDataViewModel.doPause();
    }
}
