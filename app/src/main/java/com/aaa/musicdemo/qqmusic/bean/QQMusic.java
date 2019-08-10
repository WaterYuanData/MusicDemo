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

    @Override
    public String toString() {
        return "QQMusic{" +
                "code=" + code +
                ", data=" + data.toString() +
                ", message='" + message + '\'' +
                '}';
    }

    public static class Data {
        private Song song;

        public void setSong(Song song) {
            this.song = song;
        }

        public Song getSong() {
            return this.song;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "song=" + song.toString() +
                    '}';
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

        @Override
        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            for (List list1 : list) {
                stringBuffer.append(list1.toString());
            }
            return "Song{" +
                    "list=" + stringBuffer +
                    '}';
        }
    }


    public static class List {
        private java.util.List<Singer> singer;
        private String songmid;
        private String songname;
        private String albumname;
        // private int switch;

        public String getAlbumname() {
            return albumname;
        }

        public void setAlbumname(String albumname) {
            this.albumname = albumname;
        }

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

        @Override
        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            for (Singer sing : singer) {
                stringBuffer.append(sing.toString());
            }
            return "List{" +
                    "singer=" + stringBuffer.toString() +
                    ", songmid='" + songmid + '\'' +
                    ", songname='" + songname + '\'' +
                    ", albumname='" + albumname + '\'' +
                    '}';
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

        @Override
        public String toString() {
            return "Singer{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
