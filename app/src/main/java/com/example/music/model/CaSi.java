package com.example.music.model;

public class CaSi {
    String idCaSi;
    String tenCasi;

    public String getIdCaSi() {
        return idCaSi;
    }

    public void setIdCaSi(String idCaSi) {
        this.idCaSi = idCaSi;
    }

    public String getTenCasi() {
        return tenCasi;
    }

    public void setTenCasi(String tenCasi) {
        this.tenCasi = tenCasi;
    }

    public CaSi(String idCaSi, String tenCasi) {
        this.idCaSi = idCaSi;
        this.tenCasi = tenCasi;
    }

    public CaSi() {
    }
}
