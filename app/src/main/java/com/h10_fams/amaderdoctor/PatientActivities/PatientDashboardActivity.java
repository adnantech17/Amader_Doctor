package com.h10_fams.amaderdoctor.PatientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.Models.Patient;
import com.h10_fams.amaderdoctor.PatientFragments.EmergencyFragment;
import com.h10_fams.amaderdoctor.PatientFragments.HomeFragment;
import com.h10_fams.amaderdoctor.PatientFragments.ProfileFragment;
import com.h10_fams.amaderdoctor.R;

import java.util.Random;

public class PatientDashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    public static Patient patient;
    public static int type = 1;
    public static String condition = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        if(type==1)
            condition = "mild";
        else if(type==2)
            condition = "critical";
        else
            condition = "dead";


        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("condition").setValue(condition);

        updatePatient();

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectorFragment = new HomeFragment();
                        break;
                    case R.id.nav_profile:
                        selectorFragment = new ProfileFragment();
                        break;
                    case R.id.nav_emergency:
                        selectorFragment = new EmergencyFragment();
                        break;
                }
                if(selectorFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectorFragment).commit();
                }
                return true;
            }

        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
    }


    private void updatePatient() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("infos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patient = snapshot.getValue(Patient.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}