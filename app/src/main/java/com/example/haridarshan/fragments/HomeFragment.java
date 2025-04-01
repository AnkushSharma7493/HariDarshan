package com.example.haridarshan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.haridarshan.service.ArticleService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Home Fragment
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> articleList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        articleAdapter = new ArticleAdapter(articleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(articleAdapter);

        loadArticles();

        // Handle item clicks
        articleAdapter.setOnItemClickListener(article -> {
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            intent.putExtra("article_data", (Serializable) article);
            startActivity(intent);
        });

        return view;
    }




    private void loadArticles(){
        Log.d("Home Fragement","**********************FETCHING ARTICLES**********************");
        ArticleService.getInstance().fetchArticles(articles -> {
            if (!articles.isEmpty()) {
                articleList.clear();
                articleList.addAll(articles);
                articleAdapter.notifyDataSetChanged();
                Log.d("Firestore", "Fetched " + articles.size() + " articles");
            } else {
                Log.d("Firestore", "No articles found");
            }
        });
    }
}


