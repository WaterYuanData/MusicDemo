package com.aaa.io.bean;

public class RootBean {
    private int code;

    private int cid;

    private String userip;

    private DataBean data;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCid() {
        return this.cid;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getUserip() {
        return this.userip;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public DataBean getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "Root{" +
                "code=" + code +
                ", cid=" + cid +
                ", userip='" + userip + '\'' +
                ", data=" + data +
                '}';
    }
}
