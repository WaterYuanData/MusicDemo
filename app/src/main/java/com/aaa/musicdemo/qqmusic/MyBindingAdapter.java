package com.aaa.musicdemo.qqmusic;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class MyBindingAdapter {
    @BindingAdapter({"loadImg"})
    public static void loadImg(ImageView imageView, String url) {
        Glide.with(imageView).load(url).into(imageView);
    }
}
