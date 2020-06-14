package com.application.ksgu.Model;

public class Item {
    private String judul;
    private int gambar;
    private String id;

    public Item(String judul, int gambar, String id){
        this.judul  = judul;
        this.gambar = gambar;
        this.id     = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
