package com.aaa.musicdemo.qqmusic.bomb;

import cn.bmob.v3.BmobObject;

// http://doc.bmob.cn/data/android/#sdk
public class MusicKey extends BmobObject {
    String vkey;

    public String getVkey() {
        return vkey;
    }

    public void setVkey(String vkey) {
        this.vkey = vkey;
    }
}
