package com.livisync.app;

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

public class EditProfileActivity extends AppCompatActivity {

    EditText etName, etAge, etBio, etBudgetMin, etBudgetMax, etCity;
    Spinner spGender, spSleep, spCleanliness;
    Switch swSmoking, swPets, swGuests;
    Button btnSaveChanges;
    FirebaseFirestore db;
    String uid;

    String[] genderOptions = {"Male", "Female", "Other"};
    String[] sleepOptions = {"Early Bird", "Night Owl", "Flexible"};
    String[] cleanOptions = {"1 - Very Relaxed", "2", "3 - Average", "4", "5 - Very Clean"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etBio = findViewById(R.id.etBio);
        etBudgetMin = findViewById(R.id.etBudgetMin);
        etBudgetMax = findViewById(R.id.etBudgetMax);
        etCity = findViewById(R.id.etCity);
        spGender = findViewById(R.id.spGender);
        spSleep = findViewById(R.id.spSleep);
        spCleanliness = findViewById(R.id.spCleanliness);
        swSmoking = findViewById(R.id.swSmoking);
        swPets = findViewById(R.id.swPets);
        swGuests = findViewById(R.id.swGuests);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        setupSpinners();
        loadCurrentData();

        btnSaveChanges.setOnClickListener(v -> saveChanges());
    }

    private void setupSpinners() {
        spGender.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genderOptions));
        spSleep.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sleepOptions));
        spCleanliness.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cleanOptions));
    }

    private void loadCurrentData() {
        // Load user data and prefill fields
        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        etName.setText(doc.getString("name"));
                        etAge.setText(doc.getString("age"));
                        etBio.setText(doc.getString("bio"));

                        // Set gender spinner to current value
                        String gender = doc.getString("gender");
                        for (int i = 0; i < genderOptions.length; i++) {
                            if (genderOptions[i].equals(gender)) {
                                spGender.setSelection(i);
                                break;
                            }
                        }
                    }
                });

        // Load preferences and prefill
        db.collection("preferences").document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        etCity.setText(doc.getString("city"));

                        Long min = doc.getLong("budgetMin");
                        Long max = doc.getLong("budgetMax");
                        if (min != null) etBudgetMin.setText(String.valueOf(min));
                        if (max != null) etBudgetMax.setText(String.valueOf(max));

                        swSmoking.setChecked(Boolean.TRUE.equals(doc.getBoolean("smokingAllowed")));
                        swPets.setChecked(Boolean.TRUE.equals(doc.getBoolean("petsAllowed")));
                        swGuests.setChecked(Boolean.TRUE.equals(doc.getBoolean("guestsAllowed")));

                        // Set sleep spinner
                        String sleep = doc.getString("sleepSchedule");
                        for (int i = 0; i < sleepOptions.length; i++) {
                            if (sleepOptions[i].equals(sleep)) {
                                spSleep.setSelection(i);
                                break;
                            }
                        }

                        // Set cleanliness spinner
                        Long clean = doc.getLong("cleanliness");
                        if (clean != null) spCleanliness.setSelection((int)(clean - 1));
                    }
                });
    }

    private void saveChanges() {
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String bio = etBio.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String budgetMin = etBudgetMin.getText().toString().trim();
        String budgetMax = etBudgetMax.getText().toString().trim();

        if (name.isEmpty()) { etName.setError("Required"); return; }
        if (age.isEmpty()) { etAge.setError("Required"); return; }
        if (city.isEmpty()) { etCity.setError("Required"); return; }
        if (budgetMin.isEmpty()) { etBudgetMin.setError("Required"); return; }
        if (budgetMax.isEmpty()) { etBudgetMax.setError("Required"); return; }

        // Save to users collection
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("name", name);
        userUpdates.put("age", age);
        userUpdates.put("bio", bio);
        userUpdates.put("gender", spGender.getSelectedItem().toString());

        db.collection("users").document(uid)
                .update(userUpdates);

        // Save to preferences collection
        Map<String, Object> prefUpdates = new HashMap<>();
        prefUpdates.put("sleepSchedule", spSleep.getSelectedItem().toString());
        prefUpdates.put("cleanliness", spCleanliness.getSelectedItemPosition() + 1);
        prefUpdates.put("budgetMin", Integer.parseInt(budgetMin));
        prefUpdates.put("budgetMax", Integer.parseInt(budgetMax));
        prefUpdates.put("city", city);
        prefUpdates.put("smokingAllowed", swSmoking.isChecked());
        prefUpdates.put("petsAllowed", swPets.isChecked());
        prefUpdates.put("guestsAllowed", swGuests.isChecked());

        db.collection("preferences").document(uid)
                .update(prefUpdates)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
                    finish(); // go back to profile fragment
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}