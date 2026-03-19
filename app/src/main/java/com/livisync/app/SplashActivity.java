package com.livisync.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Wait 2 seconds then decide where to go
        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null) {
                // Already logged in → go to main screen
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                // Not logged in → go to login
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish(); // destroy splash so user cant go back to it
        }, 2000);
    }
}