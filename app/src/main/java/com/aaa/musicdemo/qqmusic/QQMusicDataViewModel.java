package com.aaa.musicdemo.qqmusic;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aaa.musicdemo.App;
import com.aaa.musicdemo.qqmusic.bean.QQMusic;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class QQMusicDataViewModel extends ViewModel {
    private static final String TAG = "MusicDataViewModel";
    private MutableLiveData<QQMusic> mMutableLiveData = new MutableLiveData();
    private MutableLiveData<Boolean> mIsLoaded = new MutableLiveData();
    private MediaPlayer mMediaPlayer;
    private Gson mGson = new Gson();

    public QQMusicDataViewModel() {
        mIsLoaded.setValue(false);
        Log.i(TAG, "QQMusicDataViewModel: ");
    }

    public MutableLiveData<Boolean> getIsLoaded() {
        return mIsLoaded;
    }

    public void setIsLoaded(MutableLiveData<Boolean> isLoaded) {
        mIsLoaded = isLoaded;
    }

    public MutableLiveData<QQMusic> getMutableLiveData() {
        return mMutableLiveData;
    }

    public void queryQQMusicData(String songName) {
        mIsLoaded.setValue(false);
        QQMudicManager.getInstance().getMusicData(songName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // IllegalArgumentException: Unable to create converter for class java.lang.String
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String string = responseBody.string();
                        Log.i(TAG, "accept: responseBody=" + string);
                        string = string.substring("callback(".length(), string.length() - 1);
                        Log.i(TAG, "accept: substring=" + string);
                        QQMusic qqMusic = mGson.fromJson(string, QQMusic.class);
                        mMutableLiveData.setValue(qqMusic);
                        mIsLoaded.setValue(true);
                    }
                });
    }

    public void doPlay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String songmid = mMutableLiveData.getValue().getData().getSong().getList().get(0).getSongmid();
                Log.d(TAG, "run: " + songmid);
                try {
                    // String url = "http://sc1.111ttt.cn/2017/1/05/09/298092035545.mp3"; // your URL here
                    // "songmid":"003iHc0e2UIgMC"
                    // String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400003iHc0e2UIgMC.m4a?guid=7249969248&vkey=1B6E68A9D3C7B8150A04A706A030952375F383B4FB99151FE4CB7D3C1EFF497171F3294BC62D5EB34C012AA5552F615A325F3F7756BE7533&uin=0&fromtag=38";
                    // String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400001Q8ojB1EM8mw.m4a?guid=7249969248&vkey=1B6E68A9D3C7B8150A04A706A030952375F383B4FB99151FE4CB7D3C1EFF497171F3294BC62D5EB34C012AA5552F615A325F3F7756BE7533&uin=0&fromtag=38";
//                    String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C40000210iUr2jg9fl.m4a?guid=7249969248&vkey=1B6E68A9D3C7B8150A04A706A030952375F383B4FB99151FE4CB7D3C1EFF497171F3294BC62D5EB34C012AA5552F615A325F3F7756BE7533&uin=0&fromtag=38";
                    String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400"
                            + songmid
                            + ".m4a?guid=7249969248&vkey=1B6E68A9D3C7B8150A04A706A030952375F383B4FB99151FE4CB7D3C1EFF497171F3294BC62D5EB34C012AA5552F615A325F3F7756BE7533&uin=0&fromtag=38";
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
