package com.example.haridarshan.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import com.example.haridarshan.R;
import com.example.haridarshan.databinding.ActivityMainBinding;
import com.example.haridarshan.fragments.AboutDialogFragment;
import com.example.haridarshan.fragments.CategoriesFragment;
import com.example.haridarshan.fragments.FavoritesFragment;
import com.example.haridarshan.fragments.HomeFragment;
import com.example.haridarshan.fragments.SettingsFragment;
import com.example.haridarshan.service.AuthService;
import com.example.haridarshan.service.FirebaseNotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;
    private AuthService authService;

    private FirebaseNotificationService firebaseNotificationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        authService=new AuthService(this);
        firebaseNotificationService= new FirebaseNotificationService(this);

        if (!checkUserLoggedIn()) {
            navigateToLogin();
        } else {

            // Initialize View Binding
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            setSupportActionBar(binding.toolbar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, binding.drawerLayout, binding.toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            binding.drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                binding.navView.setCheckedItem(R.id.nav_home);
            }

            binding.navView.setNavigationItemSelectedListener(item -> {

                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                } else if (id == R.id.nav_categories) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoriesFragment()).commit();
                } else if (id == R.id.nav_favorites) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoritesFragment()).commit();
                } else if (id == R.id.nav_settings) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                }

                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            });

            // Handle About Link Click
            View footerView = findViewById(R.id.nav_footer); // Add footer view
            TextView aboutLink = footerView.findViewById(R.id.about_link);

            // Apply underline programmatically
            aboutLink.setPaintFlags(aboutLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            if (!aboutLink.hasOnClickListeners()) {
                aboutLink.setOnClickListener(v -> {
                    AboutDialogFragment aboutDialog = new AboutDialogFragment();
                    aboutDialog.show(getSupportFragmentManager(), "AboutDialog");
                    binding.drawerLayout.closeDrawers(); // Close drawer on click
                });
            }


            // Set for Push notification
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String token = task.getResult();
                    Log.d("FCM Token", token);
                }
            });

            // Create Notification Channel
            //firebaseNotificationService.createNotificationChannel();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Notification", "Permission granted");
            } else {
                Log.e("Notification", "Permission denied");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_view_profile) {
            startActivity(new Intent(this, UserProfileActivity.class));
            return true;
        } else if(id == R.id.action_logout){
            authService.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            showDialog("User logged out successfully");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkUserLoggedIn() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null && !isUserStoredInDevice()) {
            return false;
        } else {
            showDialog("User is logged : "+currentUser.getDisplayName());
            Log.d("MainActivity","***************************************************");
            Log.d("MainActivity", "User is logged : "+currentUser.getDisplayName());
            Log.d("MainActivity","***************************************************");
            return true;
        }
    }

    private boolean isUserStoredInDevice() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);
        return userId != null;
    }

    private void showDialog(String msg) {
        AboutDialogFragment aboutDialog = new AboutDialogFragment();
        aboutDialog.show(getSupportFragmentManager(), msg);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
