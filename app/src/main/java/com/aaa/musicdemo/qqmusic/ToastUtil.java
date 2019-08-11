package com.aaa.musicdemo.qqmusic;

import android.widget.Toast;

import com.aaa.musicdemo.App;

public class ToastUtil {
    public static void show(String message) {
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
