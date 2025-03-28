package com.example.haridarshan.models;

import java.io.Serializable;

public class Article implements Serializable {
    private String title;
    private String description;
    private int imageResId;

    public Article(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }
}

