package com.application.ksgu.Model;

public class News {
    private int gambar;
    private String title;

    public News (int gambar, String title){
        this.gambar = gambar;
        this.title  = title;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
