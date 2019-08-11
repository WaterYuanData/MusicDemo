package com.aaa.musicdemo.qqmusic;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtil {
    public static void show(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
