package com.aaa.musicdemo.music;

import android.util.Log;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicHttpsUtil {
    private static final String TAG = "MusicHttpsUtil";
    private static final String BASE_URL = "http://tingapi.ting.baidu.com";
    private static final String BASE_URL1 = "https://tingapi.ting.baidu.com";
    private final Retrofit mRetrofit;

    private static volatile MusicHttpsUtil instance;

    public static MusicHttpsUtil getInstance() {
        if (instance == null) {
            synchronized (MusicHttpsUtil.class) {
                if (instance == null) {
                    instance = new MusicHttpsUtil();
                }
            }
        }
        return instance;
    }

    private MusicHttpsUtil() {
        // https://blog.csdn.net/willhanweijia/article/details/69942039
        OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }).build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL1) // HTTP 403 Forbidden
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }

    public Observable getMusicData(String query) {
        Log.i(TAG, "getMusicData: query=" + query);
        // http://tingapi.ting.baidu.com/v1/restserver/ting?
        // from=android&version=5.6.5.0&method=baidu.ting.search.catalogSug&format=json&query=%E4%B8%83%E9%87%8C%E9%A6%99
        return mRetrofit.create(MusicApi.class)
                .getMusicData("android", "5.6.5.0", "baidu.ting.search.catalogSug", "json", query);
    }
}
