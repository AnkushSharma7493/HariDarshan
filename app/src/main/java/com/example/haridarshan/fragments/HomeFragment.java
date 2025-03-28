package com.example.haridarshan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haridarshan.R;
import com.example.haridarshan.activities.ArticleDetailActivity;
import com.example.haridarshan.adapters.ArticleAdapter;
import com.example.haridarshan.models.Article;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Home Fragment
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> articleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        // Sample Data
        articleList = new ArrayList<>();
        articleList.add(new Article("Spiritual Awakening", "Understanding the deeper meaning of life.", R.drawable.sample_image));
        articleList.add(new Article("Daily Meditation", "Benefits and techniques for a peaceful mind.", R.drawable.sample_image));
        articleList.add(new Article("Yoga & Spirituality", "How yoga connects body, mind, and soul.", R.drawable.sample_image));

        articleAdapter = new ArticleAdapter(articleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(articleAdapter);

        // Handle item clicks
        articleAdapter.setOnItemClickListener(article -> {
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            intent.putExtra("article_data", (Serializable) article);
            startActivity(intent);
        });



        return view;
    }
}


