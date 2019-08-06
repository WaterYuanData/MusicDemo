package com.aaa.musicdemo.music;

import com.aaa.musicdemo.music.bean.MusicData;

import io.reactivex.Observable;
import retrofit2.http.Query;

public interface MusicApi {
    // http://tingapi.ting.baidu.com/v1/restserver/ting?
    // from=android&version=5.6.5.0&method=baidu.ting.search.catalogSug&format=json&query=%E4%B8%83%E9%87%8C%E9%A6%99
    Observable<MusicData> getMusicData(
            @Query("from") String from,
            @Query("version") String version,
            @Query("method") String method,
            @Query("format") String format,
            @Query("query") String query
    );
}
