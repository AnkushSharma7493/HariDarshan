package com.example.haridarshan.models;


import java.io.Serializable;

public class Category implements Serializable {
    private String title;
    private String description;
    private int imageResId;

    public Category(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    public Category(String title, String description) {
        this.title = title;
        this.description = description;
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

