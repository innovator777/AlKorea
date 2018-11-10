package com.innovator.alkorea.library.models;

public class Info {
    private int image;
    private String title;
    private String content;
    private String detail;

    public int getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDetail() {
        return this.detail;
    }

    public String getContent() {
        return this.content;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Info(int image, String title, String content) {
        this.image = image;
        this.title = title;
        this.content = content;
    }

    public Info(int image, String title, String content, String detail) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.detail = detail;
    }

}
