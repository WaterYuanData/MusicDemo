package com.aaa.musicdemo.qqmusic;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QQMusicApi {
    // 查询链接
    // https://c.y.qq.com/soso/fcgi-bin/client_search_cp?aggr=1&cr=1&flag_qc=0&p=1&n=2&w=如歌
    // IllegalArgumentException: Unable to create converter for class java.lang.String
    @GET("/soso/fcgi-bin/client_search_cp")
    Observable<ResponseBody> getMusicData(
            @Query("aggr") int aggr,
            @Query("cr") int cr,
            @Query("flag_qc") int flag_qc,
            @Query("p") int p,
            @Query("n") int n, // 搜索结果的数量
            @Query("w") String w
    );
}
