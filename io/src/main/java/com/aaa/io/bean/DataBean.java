package com.aaa.io.bean;

import java.util.List;

public class DataBean {
    private int expiration;

    private List<ItemBean> items;

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public int getExpiration() {
        return this.expiration;
    }

    public void setItems(List<ItemBean> items) {
        this.items = items;
    }

    public List<ItemBean> getItems() {
        return this.items;
    }

    @Override
    public String toString() {
        return "Data{" +
                "expiration=" + expiration +
                ", items=" + items +
                '}';
    }
}