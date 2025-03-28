package com.example.haridarshan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.haridarshan.R;
import com.example.haridarshan.activities.ArticleDetailActivity;
import com.example.haridarshan.adapters.FavoritesAdapter;
import com.example.haridarshan.models.Article;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoritesAdapter favoriteAdapter;
    private List<Article> savedArticles;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        // Load saved articles (Dummy for now)
        savedArticles = new ArrayList<>();
        loadSavedArticles();

        favoriteAdapter = new FavoritesAdapter(savedArticles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(favoriteAdapter);

        // Handle item clicks
        favoriteAdapter.setOnItemClickListener(article -> {
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            intent.putExtra("article_data", (Serializable) article);
            startActivity(intent);
        });

        return root;
    }

    private void loadSavedArticles() {
        savedArticles.add(new Article("Mantra Meditation", "Chanting for inner peace", R.drawable.ic_meditation));
        savedArticles.add(new Article("Karma & Dharma", "Balancing duty & destiny", R.drawable.ic_gita));
        savedArticles.add(new Article("Bhagavad Gita Teachings", "Applying wisdom in life", R.drawable.ic_music));
    }
}
