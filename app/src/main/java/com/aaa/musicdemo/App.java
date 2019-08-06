package com.aaa.musicdemo;

import android.app.Application;

public class App extends Application {
    public static Application getInstance() {
        return instance;
    }

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
