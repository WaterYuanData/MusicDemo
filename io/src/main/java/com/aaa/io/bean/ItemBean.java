package com.aaa.io.bean;

public class ItemBean {
    private int subcode;

    private String songmid;

    private String filename;

    private String vkey;

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public int getSubcode() {
        return this.subcode;
    }

    public void setSongmid(String songmid) {
        this.songmid = songmid;
    }

    public String getSongmid() {
        return this.songmid;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setVkey(String vkey) {
        this.vkey = vkey;
    }

    public String getVkey() {
        return this.vkey;
    }

    @Override
    public String toString() {
        return "Items{" +
                "subcode=" + subcode +
                ", songmid='" + songmid + '\'' +
                ", filename='" + filename + '\'' +
                ", vkey='" + vkey + '\'' +
                '}';
    }
}