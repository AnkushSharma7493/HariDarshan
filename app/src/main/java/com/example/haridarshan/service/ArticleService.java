package com.example.haridarshan.service;

import android.util.Log;

import com.example.haridarshan.models.Article;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ArticleService {
    private static ArticleService instance;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static synchronized ArticleService getInstance() {
        if (instance == null) {
            instance = new ArticleService();
        }
        return instance;
    }

    public void addArticle(Article article) {
        article.setTimestamp(System.currentTimeMillis());
        db.collection("articles")
                .add(article)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "Article added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error adding article", e);
                });
    }

    public void fetchTodaysArticles() {
        long startOfDay = getStartOfDay();

        db.collection("articles")
                .whereGreaterThanOrEqualTo("timestamp", startOfDay)
                .get(Source.CACHE)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            Article article = doc.toObject(Article.class);
                            Log.d("Firestore", "Today's Article: " + article.getTitle());
                        }
                    } else {
                        Log.e("Firestore", "Error fetching articles", task.getException());
                    }
                });
    }


    public void fetchArticlesByPublishDate(long dateInMillis) {
        db.collection("articles")
                .whereEqualTo("publishDate", dateInMillis)
                .get(Source.CACHE)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            Article article = doc.toObject(Article.class);
                            Log.d("Firestore", "Fetched Article: " + article.getTitle());
                        }
                    } else {
                        Log.e("Firestore", "Error fetching articles", task.getException());
                    }
                });
    }

    // Utility method to get today's date in milliseconds
    public long getTodayDateInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }



    private long getStartOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    public List<Article> fetchArticles(FirestoreCallback callback) {
        List<Article> articles = new ArrayList<>();
        db.collection("articles")
                .get(Source.CACHE)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            Article article = doc.toObject(Article.class);
                            articles.add(article);
                            Log.d("Firestore", "Fetched Article: " + article.getTitle());
                        }
                    } else {
                        Log.e("Firestore", "Error fetching articles", task.getException());
                    }
                    callback.onArticlesFetched(articles); // Callback after fetch
                });

        return articles;
    }


    public void updateArticle(String docId, Article updatedArticle) {
        db.collection("articles").document(docId)
                .set(updatedArticle)
                .addOnSuccessListener(unused -> {
                    Log.d("Firestore", "Article updated successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error updating article", e);
                });
    }

    public void deleteArticle(String docId) {
        db.collection("articles").document(docId)
                .delete()
                .addOnSuccessListener(unused -> {
                    Log.d("Firestore", "Article deleted successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error deleting article", e);
                });
    }


}
