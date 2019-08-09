package com.aaa.musicdemo.qqmusic.bean;

public class QQMusic {
    private int code;
    private Data data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public static class Data {
        private Song song;

        public void setSong(Song song) {
            this.song = song;
        }

        public Song getSong() {
            return this.song;
        }
    }

    public static class Song {
        private java.util.List<List> list;

        public void setList(java.util.List<List> list) {
            this.list = list;
        }

        public java.util.List<List> getList() {
            return this.list;
        }
    }


    public static class List {
        private java.util.List<Singer> singer;
        private String songmid;
        private String songname;
        //        private int switch;

        public void setSinger(java.util.List<Singer> singer) {
            this.singer = singer;
        }

        public java.util.List<Singer> getSinger() {
            return this.singer;
        }

        public void setSongmid(String songmid) {
            this.songmid = songmid;
        }

        public String getSongmid() {
            return this.songmid;
        }

        public void setSongname(String songname) {
            this.songname = songname;
        }

        public String getSongname() {
            return this.songname;
        }
    }

    public static class Singer {
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
