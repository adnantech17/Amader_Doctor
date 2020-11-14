package com.h10_fams.amaderdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.DoctorFragments.DoctorAnalysisFragment;
import com.h10_fams.amaderdoctor.DoctorFragments.DoctorHomeFragment;
import com.h10_fams.amaderdoctor.DoctorFragments.DoctorProfileFragment;
import com.h10_fams.amaderdoctor.Models.Doctor;
import com.h10_fams.amaderdoctor.Models.Patient;
import com.h10_fams.amaderdoctor.PatientFragments.EmergencyFragment;
import com.h10_fams.amaderdoctor.PatientFragments.HomeFragment;
import com.h10_fams.amaderdoctor.PatientFragments.ProfileFragment;

public class DoctorDashboardActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    public static Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        updateDoctor();

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectorFragment = new DoctorHomeFragment();
                        break;
                    case R.id.nav_profile:
                        selectorFragment = new DoctorProfileFragment();
                        break;
                    case R.id.nav_emergency:
                        selectorFragment = new DoctorAnalysisFragment();
                        break;
                }
                if(selectorFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectorFragment).commit();
                }
                return true;
            }

        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DoctorHomeFragment()).commit();
    }

    private void updateDoctor() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors").child(userID).child("infos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctor = snapshot.getValue(Doctor.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}