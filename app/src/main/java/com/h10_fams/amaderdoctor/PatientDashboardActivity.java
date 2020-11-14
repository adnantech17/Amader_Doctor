package com.h10_fams.amaderdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.h10_fams.amaderdoctor.PatientFragments.EmergencyFragment;
import com.h10_fams.amaderdoctor.PatientFragments.HomeFragment;
import com.h10_fams.amaderdoctor.PatientFragments.ProfileFragment;

public class PatientDashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

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
}