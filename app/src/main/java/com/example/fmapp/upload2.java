package com.example.fmapp;

public class upload2 {
    String img;
    String text;
    upload2()
    {

    }
    upload2(String img, String text)
    {
        this.img = img;
        this.text = text;
    }

    public String getImg() {
        return img;
    }
    public String getText() {
        return text;
    }

    public void setImg(String img) {
        this.img = img;
    }
}