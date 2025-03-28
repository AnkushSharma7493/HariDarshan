package com.example.haridarshan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.haridarshan.R;
import com.example.haridarshan.models.Article;
import com.example.haridarshan.models.Category;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerFragment;

public class ArticleDetailActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewDescription;

   // private YouTubePlayerFragment youTubePlayerFragment;
    private static final String YOUTUBE_API_KEY = "YOUR_YOUTUBE_API_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // UI Components
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        //youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_player_view);

        // Retrieve data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("category_data")) {
            Category category = (Category) intent.getSerializableExtra("category_data");
            // Set data
            textViewTitle.setText(category.getTitle());
            textViewDescription.setText(category.getDescription());
        } else if (intent != null && intent.hasExtra("article_data")) {
            Article article = (Article) intent.getSerializableExtra("article_data");
            // Set data
            textViewTitle.setText(article.getTitle());
            textViewDescription.setText(article.getDescription());
        }


        // Play YouTube video if available
//        if (videoId != null && !videoId.isEmpty()) {
//            initializeYouTubePlayer(videoId);
//        }
    }

//    private void initializeYouTubePlayer(String videoId) {
//        youTubePlayerFragment.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
//                if (!wasRestored) {
//                    youTubePlayer.cueVideo(videoId); // Play video
//                }
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
//                Toast.makeText(ArticleDetailActivity.this, "YouTube Player Initialization Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
