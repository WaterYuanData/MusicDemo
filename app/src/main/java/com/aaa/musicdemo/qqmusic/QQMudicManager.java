package com.aaa.musicdemo.qqmusic;

import android.util.Log;

import com.aaa.musicdemo.music.MusicHttpsUtil;

import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class QQMudicManager {
    private static final String TAG = "QQMudicApi";
    private static final String BASE_URL = "https://c.y.qq.com";
    // 播放链接
    // "songmid":"003iHc0e2UIgMC"
    // String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400003iHc0e2UIgMC.m4a?guid=7249969248&vkey=1B6E68A9D3C7B8150A04A706A030952375F383B4FB99151FE4CB7D3C1EFF497171F3294BC62D5EB34C012AA5552F615A325F3F7756BE7533&uin=0&fromtag=38";
    // String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400001Q8ojB1EM8mw.m4a?guid=7249969248&vkey=1B6E68A9D3C7B8150A04A706A030952375F383B4FB99151FE4CB7D3C1EFF497171F3294BC62D5EB34C012AA5552F615A325F3F7756BE7533&uin=0&fromtag=38";
    // String url = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C40000210iUr2jg9fl.m4a?guid=7249969248&vkey=1B6E68A9D3C7B8150A04A706A030952375F383B4FB99151FE4CB7D3C1EFF497171F3294BC62D5EB34C012AA5552F615A325F3F7756BE7533&uin=0&fromtag=38";
    // guid 与 vkey 的值在变化，导致链接失效
    // 查询链接
    // https://c.y.qq.com/soso/fcgi-bin/client_search_cp?aggr=1&cr=1&flag_qc=0&p=1&n=2&w=如歌

    private final Retrofit mRetrofit;

    private static volatile QQMudicManager instance;

    public static QQMudicManager getInstance() {
        if (instance == null) {
            synchronized (MusicHttpsUtil.class) {
                if (instance == null) {
                    instance = new QQMudicManager();
                }
            }
        }
        return instance;
    }

    private QQMudicManager() {
        // https://www.jianshu.com/p/1fea1e7dc465
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
                Log.i(TAG, "loggingInterceptor: " + s);
            }
        });
        // https://blog.csdn.net/willhanweijia/article/details/69942039
        OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        Log.i(TAG, "hostnameVerifier: hostname=" + hostname + " SSLSession=" + session);
                        return true;
                    }
                }).build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // HTTP 403 Forbidden
                .addConverterFactory(GsonConverterFactory.create())
                // https://www.jianshu.com/p/1fea1e7dc465
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }

    public Observable getMusicData(String query) {
        Log.i(TAG, "getMusicData: query=" + query);
        return mRetrofit.create(QQMusicApi.class)
                .getMusicData(1, 1, 0, 1, 2, query);
    }
}

