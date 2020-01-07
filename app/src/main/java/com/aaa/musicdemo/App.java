package com.aaa.musicdemo;

import android.app.Application;
import android.util.Log;

import com.aaa.musicdemo.qqmusic.SharedPreferenceUtil;
import com.aaa.musicdemo.qqmusic.bomb.MusicKey;

//import cn.bmob.v3.Bmob;
//import cn.bmob.v3.BmobQuery;
//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.QueryListener;

public class App extends Application {
    private static final String TAG = "App";

    public static Application getInstance() {
        return instance;
    }

    private static Application instance;

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
        instance = this;

        initBomb();

        updateVkey();
    }

    private void updateVkey() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferenceUtil.putString("vkey", "2BF859C2084A2075A989BB0B76219C09F80EA8F3BC16E758D041662D7FD7DD1CAB505907E8B5708971949BDD0FE3DE38F74A6AD09A81814F");
            }
        }).start();
    }

    public void initBomb() {
//        Bmob.initialize(this, "7d36601e3ab14eeec6255d64d9acf5f8");

        //查找MusicKey表里面id为eVKl000C的数据
//        BmobQuery<MusicKey> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject("eVKl000C", new QueryListener<MusicKey>() {
//            @Override
//            public void done(MusicKey musicKey, BmobException e) {
//                if (e == null) {
//                    String vkey = musicKey.getVkey();
//                    Log.i(TAG, "initBomb: vkey=" + vkey);
//                    SharedPreferenceUtil.putString("vkey", vkey);
//                } else {
//                    Log.e(TAG, "initBomb: " + e.getMessage());
//                }
//            }
//        });
    }
}
