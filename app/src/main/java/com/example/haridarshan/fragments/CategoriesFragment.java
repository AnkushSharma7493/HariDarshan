package com.example.haridarshan.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.haridarshan.R;
import com.example.haridarshan.activities.ArticleDetailActivity;
import com.example.haridarshan.adapters.CategoryAdapter;
import com.example.haridarshan.models.Category;

public class CategoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCategories);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load Categories
        categoryList = new ArrayList<>();
        categoryList.add(new Category("Bhagavad Gita", "Teachings from Bhagavad Gita", R.drawable.ic_gita));
        categoryList.add(new Category("Meditation", "Articles on mindfulness & meditation", R.drawable.ic_meditation));
        categoryList.add(new Category("Devotional Songs", "Lyrics and meanings of devotional songs", R.drawable.ic_music));
        categoryList.add(new Category("Spiritual Stories", "Short stories with deep spiritual lessons", R.drawable.ic_book));

        // Set Adapter
        categoryAdapter = new CategoryAdapter(categoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(categoryAdapter);

        // Handle item clicks
        categoryAdapter.setOnItemClickListener(category -> {
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            intent.putExtra("category_data", (Serializable) category);
            startActivity(intent);
        });


        return view;
    }
}
