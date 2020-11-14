package com.h10_fams.amaderdoctor.PatientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
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
import com.h10_fams.amaderdoctor.Models.Fever;
import com.h10_fams.amaderdoctor.Models.Patient;
import com.h10_fams.amaderdoctor.Models.Sensors;
import com.h10_fams.amaderdoctor.R;
import com.h10_fams.amaderdoctor.YourPatientsActivity;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RecordActivity extends AppCompatActivity {
    private Button btnLipid, btnHormone, btnBloodCell;
    private EditText etName, etAge, etDate, etHeight, etWeight, etTemp, etCystolicPressure, etDiastolicPressure;
    Patient patient;
    Spinner spnSuger, spnCough;
    Fever fever = new Fever();
    private int mInterval = 1000;
    private Handler mHandler;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        init();


        mHandler = new Handler();
        startRepeatingTask();

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("infos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patient = snapshot.getValue(Patient.class);
                etAge.setText(patient.getAge());
                etName.setText(patient.getName());
                etDate.setText(patient.getDob());
                etHeight.setText(patient.getHeight());
                etWeight.setText(patient.getWeight());
                etTemp.setText(fever.getTemperature());

                if (fever.getCough().equals("Yes")) {
                    spnCough.setSelection(0);
                } else {
                    spnCough.setSelection(1);
                }

                if (fever.getSuger().equals("Yes")) {
                    spnSuger.setSelection(0);
                } else {
                    spnSuger.setSelection(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spnSuger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fever.setSuger(spnSuger.getSelectedItem().toString());
                uploadFeverInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnCough.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fever.setCough(spnCough.getSelectedItem().toString());
                uploadFeverInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnLipid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecordActivity.this, LipidActivity.class));
            }
        });


        btnHormone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecordActivity.this, HormoneActivity.class));
            }
        });


        btnBloodCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecordActivity.this, BloodCountActivity.class));
            }
        });
        uploadFeverInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                index++;
                Log.d("LLLLL", Sensors.getDiastoleData(PatientDashboardActivity.type, index) + "");
                etCystolicPressure.setText(Sensors.getSystoleData(PatientDashboardActivity.type, index) + "");
                etDiastolicPressure.setText(Sensors.getDiastoleData(PatientDashboardActivity.type, index) + "");
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private void uploadFeverInfo() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("fever");
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("suger", fever.getSuger());
        hashMap.put("temperature", fever.getTemperature());
        hashMap.put("cough", fever.getCough());

        ref.setValue(hashMap);
    }

    private void init() {

        btnLipid = findViewById(R.id.btnLipid);
        btnHormone = findViewById(R.id.btnHormone);
        btnBloodCell = findViewById(R.id.btnBloodCell);


        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etDate = findViewById(R.id.etDate);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        etTemp = findViewById(R.id.etTemp);
        spnSuger = findViewById(R.id.spnSuger);
        spnCough = findViewById(R.id.spnCough);
        etCystolicPressure = findViewById(R.id.etCystolicPressure);
        etDiastolicPressure = findViewById(R.id.etDiastolicPressure);

    }
}