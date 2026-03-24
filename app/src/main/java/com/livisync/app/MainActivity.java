package com.livisync.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavigationView);
        loadFragment(new HomeFragment());

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.search) {
                loadFragment(new SearchFragment());
            } else if (id == R.id.req) {
                loadFragment(new RequestsFragment());
            } else if (id == R.id.chats) {
                loadFragment(new ChatFragment());
            } else if (id == R.id.profile) {
                loadFragment(new ProfileFragment());
            }
            else if(id==R.id.mainhome)
            {
                loadFragment(new HomeFragment());
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}