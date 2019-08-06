package com.aaa.musicdemo.music.bean;


import java.util.List;

public class MusicData {
    private List<Song> song;

    private String order;

    private int error_code;

    private List<Album> album;

    public void setSong(List<Song> song) {
        this.song = song;
    }

    public List<Song> getSong() {
        return this.song;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return this.order;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getError_code() {
        return this.error_code;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public List<Album> getAlbum() {
        return this.album;
    }
}
