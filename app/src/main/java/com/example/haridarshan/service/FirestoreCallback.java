package com.example.haridarshan.service;

import com.example.haridarshan.models.Article;

import java.util.List;

public interface FirestoreCallback {
    void onArticlesFetched(List<Article> articles);
}
