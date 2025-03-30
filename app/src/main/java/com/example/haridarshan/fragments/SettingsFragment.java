package com.example.haridarshan.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.example.haridarshan.R;
import com.example.haridarshan.models.Article;
import com.example.haridarshan.service.ArticleService;

public class SettingsFragment extends Fragment {

    private Switch switchTheme, switchNotifications;
    private Button btnAddDummyArticle;
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize UI elements
        switchTheme = view.findViewById(R.id.switchTheme);
        switchNotifications = view.findViewById(R.id.switchNotifications);
        btnAddDummyArticle = view.findViewById(R.id.btnAddDummyArticle);

        btnAddDummyArticle.setOnClickListener(v -> addDummyArticle());

        // SharedPreferences for storing user settings
        sharedPreferences = requireActivity().getSharedPreferences("AppSettings", 0);
        boolean isDarkMode = sharedPreferences.getBoolean("DarkMode", false);
        boolean isNotificationsEnabled = sharedPreferences.getBoolean("Notifications", true);

        // Set switch states based on saved preferences
        switchTheme.setChecked(isDarkMode);
        switchNotifications.setChecked(isNotificationsEnabled);

        // Theme toggle logic
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("DarkMode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Notifications toggle logic
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("Notifications", isChecked);
            editor.apply();

            String message = isChecked ? "Notifications Enabled" : "Notifications Disabled";
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });

        // About button logic
//        btnAbout.setOnClickListener(v ->
//                Toast.makeText(requireContext(), "Hari Darshan v1.0 by Developer", Toast.LENGTH_LONG).show()
//        );
        return view;
    }

        private void addDummyArticle() {
            long today = System.currentTimeMillis();
            long tomorrow = today + 86400000;

            Article dummyArticle = new Article(
                    "Sample Title",
                    "This is a dummy article : "+Math.random(),
                    today
            );

            Article dummyArticle2 = new Article(
                    "Sample Title",
                    "This is a dummy article - "+Math.random(),
                    tomorrow
            );


            ArticleService.getInstance().addArticle(dummyArticle);
            ArticleService.getInstance().addArticle(dummyArticle2);
                Log.d("Firestore", "2 Dummy article added");
                Toast.makeText(requireContext(), "2 Dummy article added", Toast.LENGTH_LONG).show();

        }

}
