package com.livisync.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileSetupActivity extends AppCompatActivity {

    EditText etAge, etBio;
    Spinner spGender;
    Button btnSaveProfile;
    FirebaseFirestore db;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        etAge = findViewById(R.id.etAge);
        etBio = findViewById(R.id.etBio);
        spGender = findViewById(R.id.spGender);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        // Gender spinner options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Male", "Female", "Other"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);

        btnSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String age = etAge.getText().toString().trim();
        String bio = etBio.getText().toString().trim();
        String gender = spGender.getSelectedItem().toString();

        if (age.isEmpty()) {
            etAge.setError("Age is required");
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        updates.put("age", age);
        updates.put("bio", bio);
        updates.put("gender", gender);

        db.collection("users").document(uid)
                .update(updates)
                .addOnSuccessListener(unused -> {
                    startActivity(new Intent(ProfileSetupActivity.this, PreferencesActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}