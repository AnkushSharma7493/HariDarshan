package com.example.haridarshan.models;

import java.io.Serializable;

public class Article implements Serializable {
    private String title;
    private String description;
    private long timestamp;

    private long publishDate;
    private int imageResId;

    public Article() {} // Required for Firestore

    public Article(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Article(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    public Article(String title, String description, long publishDate) {
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }
}

