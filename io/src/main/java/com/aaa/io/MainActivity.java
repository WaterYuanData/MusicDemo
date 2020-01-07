package com.aaa.io;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Sink;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadFile3();
            }
        }).start();
    }

    private void downloadFile3() {
        //下载路径，如果路径无效了，可换成你的下载路径
        final String url = "https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg?format=json205361747&platform=yqq&cid=205361747&songmid=003lghpv0jfFXG&filename=C400003lghpv0jfFXG.m4a&guid=126548448";
        final long startTime = System.currentTimeMillis();
        Log.i("DOWNLOAD", "startTime=" + startTime);

        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                e.printStackTrace();
                Log.i("DOWNLOAD", "download failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Sink sink = null;
                BufferedSink bufferedSink = null;
                BufferedSource source = null;
                try {
//                    String mSDCardPath = getDataDir().getAbsolutePath();
//                    File dest = new File(mSDCardPath, "fcg_music_express_mobile3.txt");
//                    sink = Okio.sink(dest);
//                    bufferedSink = Okio.buffer(sink);
//                    bufferedSink.writeAll(response.body().source());
//                    bufferedSink.close();
//                    Log.i("DOWNLOAD", "download success");
//                    Log.i("DOWNLOAD", "totalTime=" + (System.currentTimeMillis() - startTime));
//
                    // p10打印不出d级别的log
                    source = response.body().source();
                    byte[] bytes = new byte[128];
                    StringBuffer stringBuffer = new StringBuffer();
                    int size = 0;
                    while ((size = source.read(bytes)) > -1) {
                        Log.i(TAG, "onResponse: length=" + bytes.length + " readSize=" + size); // length=1024 size=289
                        // byte数组转字符串 todo
                        stringBuffer.append(new String(bytes, 0, size));
                    }
                    String jsonString = stringBuffer.toString();
                    Log.i(TAG, "onResponse: jsonString=" + jsonString);
                    // String转Json todo
                    Root root = new Gson().fromJson(jsonString, Root.class);

//                    Log.i(TAG, "onResponse: bytes.toString=" + bytes.toString()); // [B@bedf75f
//                    String str = new String(bytes);
//                    Log.i(TAG, "onResponse: " + str);
//                    Log.i(TAG, "onResponse: " + str.replaceAll("[\\W]", ""));
//                    2020-01-07 13:33:55.153 15930-15979/com.aaa.io I/MainActivity: onResponse: {"code":0,"cid":205361747,"userip":"117.136.63.138","data":{"expiration":80400,"items":[{"subcode":0,"songmid":"003lghpv0jfFXG","filename":"C400003lghpv0jfFXG.m4a","vkey":"35D21CD3346BF562F674A9601DAA8C8BDA9EEA77E6C5E400EFAAD5C2BB895027C102BA5BA7082A01CF41D22C7D697A1DCEA6D2F4F5D58825"}]}}������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������
//                    2020-01-07 13:33:55.154 15930-15979/com.aaa.io I/MainActivity: onResponse: code0cid205361747userip11713663138dataexpiration80400itemssubcode0songmid003lghpv0jfFXGfilenameC400003lghpv0jfFXGm4avkey35D21CD3346BF562F674A9601DAA8C8BDA9EEA77E6C5E400EFAAD5C2BB895027C102BA5BA7082A01CF41D22C7D697A1DCEA6D2F4F5D58825

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("DOWNLOAD", "download failed");
                } finally {
                    if (bufferedSink != null) {
                        bufferedSink.close();
                    }
                    if (source != null) {
                        source.close();
                    }
                }
            }
        });
    }
//    原文链接：https://blog.csdn.net/u010203716/article/details/73194804
}
