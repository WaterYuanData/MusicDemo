package com.aaa.musicdemo.qqmusic;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aaa.musicdemo.App;
import com.aaa.musicdemo.music.MusicHttpsUtil;
import com.aaa.musicdemo.music.bean.MusicData;
import com.aaa.musicdemo.music.bean.Song;
import com.aaa.musicdemo.qqmusic.bean.QQMusic;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class QQMusicDataViewModel extends ViewModel {
    private static final String TAG = "MusicDataViewModel";

    private MutableLiveData<QQMusic> mMutableLiveData = new MutableLiveData();
    private MediaPlayer mMediaPlayer;
    private Gson mGson = new Gson();

    public MutableLiveData<QQMusic> getMutableLiveData() {
        return mMutableLiveData;
    }

    public void queryQQMusicData() {
        QQMudicManager.getInstance().getMusicData("如歌")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>() {
                    @Override
                    public void accept(JsonObject musicData) throws Exception {
//                        String substring = musicData.substring("callback(".length(), musicData.length() - 1);
                        Log.i(TAG, "accept: musicData=" + musicData);
                        //                        mMutableLiveData.setValue();
                    }
                });
    }

    public void doPlay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //                    String url = "http://sc1.111ttt.cn/2017/1/05/09/298092035545.mp3"; // your URL here
                    //                    "songmid":"003iHc0e2UIgMC"
                    //                    String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400003iHc0e2UIgMC.m4a?guid=7249969248&vkey=1B6E68A9D3C7B8150A04A706A030952375F383B4FB99151FE4CB7D3C1EFF497171F3294BC62D5EB34C012AA5552F615A325F3F7756BE7533&uin=0&fromtag=38";
                    //                    String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400001Q8ojB1EM8mw.m4a?guid=7249969248&vkey=1B6E68A9D3C7B8150A04A706A030952375F383B4FB99151FE4CB7D3C1EFF497171F3294BC62D5EB34C012AA5552F615A325F3F7756BE7533&uin=0&fromtag=38";
                    String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C40000210iUr2jg9fl.m4a?guid=7249969248&vkey=1B6E68A9D3C7B8150A04A706A030952375F383B4FB99151FE4CB7D3C1EFF497171F3294BC62D5EB34C012AA5552F615A325F3F7756BE7533&uin=0&fromtag=38";
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
                    Toast.makeText(App.getInstance(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    public void doPause() {
        mMediaPlayer.stop();
    }
}
