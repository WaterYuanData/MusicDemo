package com.aaa.musicdemo.music.bean;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aaa.musicdemo.App;
import com.aaa.musicdemo.music.MusicHttpsUtil;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MusicDataViewModel extends ViewModel {
    private static final String TAG = "MusicDataViewModel";

    MutableLiveData<MusicData> mMutableLiveData = new MutableLiveData();
    private MediaPlayer mMediaPlayer;

    public MutableLiveData<MusicData> getMutableLiveData() {
        return mMutableLiveData;
    }

    public void loadMusicData() {
        MusicHttpsUtil.getInstance().getMusicData("如歌")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MusicData>() {
                    @Override
                    public void accept(MusicData musicData) throws Exception {
                        Log.i(TAG, "accept: musicData=" + musicData);
                        mMutableLiveData.setValue(musicData);
                        List<Song> song = musicData.getSong();
                        for (int i = 0; i < song.size(); i++) {
                            if (i == 0) {
                                Toast.makeText(App.getInstance(), song.get(i).getArtistname(), Toast.LENGTH_SHORT).show();
                            }
                            Log.i(TAG, "accept: getArtistname=" + song.get(i).getArtistname()
                                    + " getSongname=" + song.get(i).getSongname());
                        }
                    }
                });
    }

    public void doPlay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://sc1.111ttt.cn/2017/1/05/09/298092035545.mp3"; // your URL here
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setDataSource(url);
                    Log.i(TAG, "run: prepare");
                    mMediaPlayer.prepare(); // might take long! (for buffering, etc)
                    Log.i(TAG, "run: prepare success");
                    mMediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "doPlay: " + e.getMessage());
//                    子线程不能弹土司
//                    Toast.makeText(App.getInstance(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    public void doPause() {
        mMediaPlayer.stop();
    }
}
