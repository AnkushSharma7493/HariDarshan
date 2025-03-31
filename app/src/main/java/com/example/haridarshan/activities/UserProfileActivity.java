package com.example.haridarshan.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haridarshan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {

    private TextView userName, userEmail;
    private ImageView userImage;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userImage = findViewById(R.id.user_image);

        firebaseAuth = FirebaseAuth.getInstance();
        loadUserProfile();
    }

    private void loadUserProfile() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            userName.setText(currentUser.getDisplayName());
            userEmail.setText(currentUser.getEmail());
            // Load user image if available
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            userName.setText(sharedPreferences.getString("userName", "Guest"));
            userEmail.setText(sharedPreferences.getString("userEmail", "N/A"));
        }
    }
}
