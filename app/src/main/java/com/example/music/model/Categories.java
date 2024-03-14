package com.example.music.model;

import java.io.Serializable;

public class Categories implements Serializable {
    String maCate;
    String TenCate;
    byte[] imgCate;

    public String getMaCate() {
        return maCate;
    }

    public void setMaCate(String maCate) {
        this.maCate = maCate;
    }

    public String getTenCate() {
        return TenCate;
    }

    public void setTenCate(String tenCate) {
        TenCate = tenCate;
    }

    public byte[] getImgCate() {
        return imgCate;
    }

    public void setImgCate(byte[] imgCate) {
        this.imgCate = imgCate;
    }

    public byte[] getBannerCate() {
        return bannerCate;
    }

    public void setBannerCate(byte[] bannerCate) {
        this.bannerCate = bannerCate;
    }

    public Categories(String maCate, String tenCate, byte[] imgCate, byte[] bannerCate) {
        this.maCate = maCate;
        TenCate = tenCate;
        this.imgCate = imgCate;
        this.bannerCate = bannerCate;
    }

    public Categories() {
    }

    byte[] bannerCate;


}
