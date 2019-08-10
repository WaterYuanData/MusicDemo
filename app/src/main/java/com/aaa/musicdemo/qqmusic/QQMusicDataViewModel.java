package com.aaa.musicdemo.qqmusic;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aaa.musicdemo.App;
import com.aaa.musicdemo.qqmusic.bean.QQMusic;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class QQMusicDataViewModel extends ViewModel {
    private static final String TAG = "MusicDataViewModel";
    private MutableLiveData<QQMusic> mMutableLiveData = new MutableLiveData(); // 查询到的音乐数据
    private MutableLiveData<Boolean> mIsLoaded = new MutableLiveData(); // 查询是否完成
    private MutableLiveData<Boolean> mIsPrepared = new MutableLiveData(); // 播放准备工作是否完成
    private MutableLiveData<Boolean> mCompleted = new MutableLiveData(); // 歌曲是否播放完毕
    private MutableLiveData<Integer> mCurrentPosition = new MutableLiveData(); // 歌曲进度
    private MutableLiveData<Integer> mDuration = new MutableLiveData(); // 歌曲时长
    private MediaPlayer mMediaPlayer;
    private Gson mGson = new Gson();

    public QQMusicDataViewModel() {
        Log.i(TAG, "QQMusicDataViewModel: init");
    }

    public MutableLiveData<Integer> getDuration() {
        return mDuration;
    }

    public void setDuration(MutableLiveData<Integer> duration) {
        mDuration = duration;
    }

    public MutableLiveData<Integer> getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(MutableLiveData<Integer> currentPosition) {
        mCurrentPosition = currentPosition;
    }

    public MutableLiveData<Boolean> getIsPrepared() {
        return mIsPrepared;
    }

    public void setIsPrepared(MutableLiveData<Boolean> isPrepared) {
        mIsPrepared = isPrepared;
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
        Log.i(TAG, "doPlay: " + Thread.currentThread());
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } else {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        }
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCompleted.postValue(true);
            }
        });
        mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                Log.i(TAG, "onSeekComplete: ");
            }
        });
        mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.i(TAG, "onInfo: what=" + what + " extra=" + extra);
                return false;
            }
        });
        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.i(TAG, "onBufferingUpdate: " + percent);
            }
        });
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mIsPrepared.postValue(true);
                mMediaPlayer.start();
                int duration = mMediaPlayer.getDuration();
                Log.i(TAG, "onPrepared: duration=" + duration);
                mDuration.postValue(duration);
                // TODO: 2019/8/10
//                mCompleted.postValue(false); // null 的原因 postValue为异步执行
                mCompleted.setValue(false);
                Log.i(TAG, "onPrepared: null的原因 " + mCompleted.getValue()); // null
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int currentPosition = 0;
                        // TODO: 2019/8/10
                        Log.i(TAG, "run: " + mCompleted.getValue()); // null
                        // postValue为异步执行导致mCompleted.getValue()为null，引发mCompleted.getValue().booleanValue()空指针异常
                        while (!mCompleted.getValue()) { // 空指针异常
                            currentPosition = mMediaPlayer.getCurrentPosition();
//                            Log.i(TAG, "run: currentPosition=" + currentPosition);
                            mCurrentPosition.postValue(currentPosition);

                            Integer value = getCurrentPosition().getValue();
//                            Log.i(TAG, "callback: " + value);
                        }
                    }
                }).start();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                String songmid = null;
                try {
                    songmid = mMutableLiveData.getValue().getData().getSong().getList().get(0).getSongmid();
                    Log.i(TAG, "run: " + songmid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(songmid)) {
                    songmid = "00210iUr2jg9fl"; // 默认播放如歌
                }
                try {
                    // String url = "http://sc1.111ttt.cn/2017/1/05/09/298092035545.mp3"; // your URL here
                    // "songmid":"003iHc0e2UIgMC"
//                    String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C40000210iUr2jg9fl.m4a?guid=7249969248&vkey=C37544B5C80D97ADDEF2147C64826B7942C0D8E311F975EF9F38C29BCEBAF4DE0CE871907AB36043A637890DFE3C62E4A8BE4CBBDBBD797E&uin=0&fromtag=38";
                    String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400"
                            + songmid
                            + ".m4a?guid=353267452&&vkey=C37544B5C80D97ADDEF2147C64826B7942C0D8E311F975EF9F38C29BCEBAF4DE0CE871907AB36043A637890DFE3C62E4A8BE4CBBDBBD797E&uin=0&fromtag=38";
                    mMediaPlayer.setDataSource(url);
                    mIsPrepared.postValue(false);
                    mMediaPlayer.prepare(); // might take long! (for buffering, etc)
                } catch (Exception e) {
                    e.printStackTrace();
                    // todo 该toast显示不出来？？？
                    Toast.makeText(App.getInstance(), "出错，请重新播放", Toast.LENGTH_SHORT).show();
                    // TODO: 2019/8/10
                    // 失败重试
                }
            }
        }).start();
    }

    public void doPause() {
        mMediaPlayer.stop();
        mMediaPlayer.reset(); // 不然重新设置setDataSource会状态异常
    }

    public void doSeek(int progress) {
        Log.i(TAG, "doSeek: progress=" + progress);
        mMediaPlayer.seekTo(progress);
    }
}
