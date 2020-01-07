package com.aaa.io;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private int expiration;

    private List<Items> items;

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public int getExpiration() {
        return this.expiration;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public List<Items> getItems() {
        return this.items;
    }
}