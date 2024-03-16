package com.example.music.model;

import java.io.Serializable;

public class MusicOn implements Serializable {
    String idSong;
    String linkNhac;
    String tenNhac;

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    public String getLinkNhac() {
        return linkNhac;
    }

    public void setLinkNhac(String linkNhac) {
        this.linkNhac = linkNhac;
    }

    public String getTenNhac() {
        return tenNhac;
    }

    public void setTenNhac(String tenNhac) {
        this.tenNhac = tenNhac;
    }

    public MusicOn(String idSong, String linkNhac, String tenNhac) {
        this.idSong = idSong;
        this.linkNhac = linkNhac;
        this.tenNhac = tenNhac;
    }

    public MusicOn() {
    }
}
