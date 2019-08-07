package com.aaa.musicdemo.music.bean;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aaa.musicdemo.music.MusicHttpsUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MusicDataViewModel extends ViewModel {
    private static final String TAG = "MusicDataViewModel";

    MutableLiveData<MusicData> mMutableLiveData = new MutableLiveData();

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
                            Log.i(TAG, "accept: getArtistname=" + song.get(i).getArtistname()
                                    + " getSongname=" + song.get(i).getSongname());
                        }
                    }
                });
    }

}
