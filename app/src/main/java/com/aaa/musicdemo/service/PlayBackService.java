package com.aaa.musicdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PlayBackService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
