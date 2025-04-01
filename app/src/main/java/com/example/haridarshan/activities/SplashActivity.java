package com.example.haridarshan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haridarshan.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logo);
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        Animation fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        logo.startAnimation(scaleAnim);
        logo.startAnimation(fadeInAnim);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 2000);
    }
}
