package com.livisync.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PreferencesActivity extends AppCompatActivity {

    Spinner spSleep, spCleanliness;
    EditText etBudgetMin, etBudgetMax, etCity;
    Switch swSmoking, swPets, swGuests;
    Button btnSavePrefs;
    FirebaseFirestore db;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        spSleep = findViewById(R.id.spSleep);
        spCleanliness = findViewById(R.id.spCleanliness);
        etBudgetMin = findViewById(R.id.etBudgetMin);
        etBudgetMax = findViewById(R.id.etBudgetMax);
        etCity = findViewById(R.id.etCity);
        swSmoking = findViewById(R.id.swSmoking);
        swPets = findViewById(R.id.swPets);
        swGuests = findViewById(R.id.swGuests);
        btnSavePrefs = findViewById(R.id.btnSavePrefs);

        // Sleep options
        ArrayAdapter<String> sleepAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Early Bird", "Night Owl", "Flexible"});
        sleepAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSleep.setAdapter(sleepAdapter);

        // Cleanliness options
        ArrayAdapter<String> cleanAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"1 - Very Relaxed", "2", "3 - Average", "4", "5 - Very Clean"});
        cleanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCleanliness.setAdapter(cleanAdapter);

        btnSavePrefs.setOnClickListener(v -> savePreferences());
    }

    private void savePreferences() {
        String city = etCity.getText().toString().trim();
        String budgetMin = etBudgetMin.getText().toString().trim();
        String budgetMax = etBudgetMax.getText().toString().trim();

        if (city.isEmpty()) {
            etCity.setError("City is required");
            return;
        }
        if (budgetMin.isEmpty()) {
            etBudgetMin.setError("Required");
            return;
        }
        if (budgetMax.isEmpty()) {
            etBudgetMax.setError("Required");
            return;
        }

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("sleepSchedule", spSleep.getSelectedItem().toString());
        prefs.put("cleanliness", spCleanliness.getSelectedItemPosition() + 1);
        prefs.put("budgetMin", Integer.parseInt(budgetMin));
        prefs.put("budgetMax", Integer.parseInt(budgetMax));
        prefs.put("city", city);
        prefs.put("smokingAllowed", swSmoking.isChecked());
        prefs.put("petsAllowed", swPets.isChecked());
        prefs.put("guestsAllowed", swGuests.isChecked());

        db.collection("preferences").document(uid)
                .set(prefs)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Setup complete!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PreferencesActivity.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}