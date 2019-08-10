package com.aaa.musicdemo.qqmusic;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.aaa.musicdemo.App;

public class SharedPreferenceUtil {
    private static final SharedPreferenceUtil ourInstance = new SharedPreferenceUtil();
    private static SharedPreferences mPreferences;

    public static SharedPreferenceUtil getInstance() {
        return ourInstance;
    }

    private SharedPreferenceUtil() {
        mPreferences = App.getInstance().getSharedPreferences("music", Context.MODE_PRIVATE);
    }

    public static void putString(String key, @Nullable String value) {
        mPreferences.edit().putString(key, value).apply(); // 先写到内存，异步写到文件中
    }

    public static String getString(String key, @Nullable String defValue) {
        return mPreferences.getString(key, defValue); // 先写到内存，异步写到文件中
    }
}
