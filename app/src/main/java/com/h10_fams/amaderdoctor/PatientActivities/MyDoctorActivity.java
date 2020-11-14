package com.h10_fams.amaderdoctor.PatientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.Models.Doctor;
import com.h10_fams.amaderdoctor.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MyDoctorActivity extends AppCompatActivity {
    EditText etSpeciality, etName, etMobile;
    Button button;
    Spinner spnCondition;

    Doctor doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctor);
        final String[] drId = {""};

        etSpeciality = findViewById(R.id.etSpeciality);
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        spnCondition = findViewById(R.id.spnCondition);
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
                                spinnerTest();

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

    void spinnerTest() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notification").child(doctor.getUid()).child(userID);

        spnCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss    dd:MM:yyyy");
            String formattedDate = df.format(c.getTime());
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PatientDashboardActivity.condition = spnCondition.getSelectedItem().toString();
                if(spnCondition.getSelectedItem().toString().equals("mild")) {
                    PatientDashboardActivity.type = 1;
                }
                else if(spnCondition.getSelectedItem().toString().equals("dead")) {
                    PatientDashboardActivity.type = 3;
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", PatientDashboardActivity.patient.getName() + " is Dead!");
                    hashMap.put("time", df.toString());
                    ref.setValue(hashMap);
                }
                else if(spnCondition.getSelectedItem().toString().equals("critical")) {
                    PatientDashboardActivity.type = 2;
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", PatientDashboardActivity.patient.getName() + " is in Critical Condition");
                    hashMap.put("time", df.toString());
                    ref.setValue(hashMap);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}