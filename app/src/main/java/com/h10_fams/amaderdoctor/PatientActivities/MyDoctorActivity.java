package com.h10_fams.amaderdoctor.PatientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.Models.Doctor;
import com.h10_fams.amaderdoctor.R;

public class MyDoctorActivity extends AppCompatActivity {
    EditText etSpeciality, etName, etMobile;

    Doctor doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctor);

        etSpeciality = findViewById(R.id.etSpeciality);
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);


        final String[] drId = {""};

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d("LLLLL", ds.getKey());
                    if(ds.getKey().equals("assigned")){
                        drId[0] = ds.getValue(String.class);

                        FirebaseDatabase.getInstance().getReference().child("doctors").child(drId[0]).child("infos").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                doctor = snapshot.getValue(Doctor.class);

                                etMobile.setText(doctor.getPhone());
                                etName.setText(doctor.getName());
                                etSpeciality.setText(doctor.getSpeciality());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }


                }

                /*;*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}