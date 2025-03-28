package com.example.haridarshan.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // Handle About Link Click
        View footerView = findViewById(R.id.nav_footer); // Add footer view
        TextView aboutLink = footerView.findViewById(R.id.about_link);

        // Apply underline programmatically
        aboutLink.setPaintFlags(aboutLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        aboutLink.setOnClickListener(v -> {
            AboutDialogFragment aboutDialog = new AboutDialogFragment();
            aboutDialog.show(getSupportFragmentManager(), "AboutDialog");
            binding.drawerLayout.closeDrawers(); // Close drawer on click
        });


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
    }

    private void showAboutDialog() {
        AboutDialogFragment aboutDialog = new AboutDialogFragment();
        aboutDialog.show(getSupportFragmentManager(), "AboutDialog");
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
