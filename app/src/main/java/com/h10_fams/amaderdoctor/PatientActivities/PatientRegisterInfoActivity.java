package com.h10_fams.amaderdoctor.PatientActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h10_fams.amaderdoctor.Models.Patient;
import com.h10_fams.amaderdoctor.R;

import java.util.HashMap;

public class PatientRegisterInfoActivity extends AppCompatActivity {

    TextView tvHeightWarning, tvWeightWarning, tvNameWarning, tvAgeWarning, tvDateWarning, tvPhoneNumberWarning;
    EditText etName, etHeight, etWeight, etAge, etPhoneNumber, etDD, etMM, etYY;
    Spinner spnGender;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register_info);

        init();
        
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAllData()) {
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("infos");
                    
                    HashMap<String, String> hashMap;
                    hashMap = getAllData();
                    ref.setValue(hashMap);
                    Intent intent = new Intent(PatientRegisterInfoActivity.this, PatientDashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private HashMap<String, String> getAllData() {
        HashMap hashMap = new HashMap();
        hashMap.put("name", etName.getText().toString());
        hashMap.put("age", etAge.getText().toString());
        hashMap.put("phone", etPhoneNumber.getText().toString());
        hashMap.put("dob", etDD.getText().toString()+":"+etMM.getText().toString()+":"+etYY.getText().toString());
        hashMap.put("gender", spnGender.getSelectedItem().toString());
        hashMap.put("height", etHeight.getText().toString());
        hashMap.put("weight", etWeight.getText().toString());
        hashMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        return hashMap;
    }

    private boolean checkAllData() {
        if(etName.getText().toString().isEmpty()) {
            tvNameWarning.setVisibility(View.VISIBLE);
            return false;
        }
        else if(etAge.getText().toString().isEmpty()) {
            tvAgeWarning.setVisibility(View.VISIBLE);
            return false;
        }
        else if(etHeight.getText().toString().isEmpty()) {
            tvHeightWarning.setVisibility(View.VISIBLE);
            return false;
        }
        else if(etWeight.getText().toString().isEmpty()) {
            tvWeightWarning.setVisibility(View.VISIBLE);
            return false;
        }
        else if(etPhoneNumber.getText().toString().isEmpty()) {
            tvPhoneNumberWarning.setVisibility(View.VISIBLE);
            return false;
        }

        else if(etDD.getText().toString().isEmpty()) {
            tvDateWarning.setVisibility(View.VISIBLE);
            return false;
        }
        else if(etMM.getText().toString().isEmpty()) {
            tvDateWarning.setVisibility(View.VISIBLE);
            return false;
        }
        else if(etYY.getText().toString().isEmpty()) {
            tvDateWarning.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    private void init() {
        tvHeightWarning = findViewById(R.id.tvHeightWarning);
        tvWeightWarning = findViewById(R.id.tvWeightWarning);
        tvNameWarning = findViewById(R.id.tvNameWarning);
        tvAgeWarning = findViewById(R.id.tvAgeWarning);
        tvDateWarning = findViewById(R.id.tvDateWarning);
        tvPhoneNumberWarning = findViewById(R.id.tvPhoneNumberWarning);

        etName = findViewById(R.id.etName);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        etAge = findViewById(R.id.etAge);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etDD = findViewById(R.id.etDD);
        etMM = findViewById(R.id.etMM);
        etYY = findViewById(R.id.etYY);

        spnGender = findViewById(R.id.spnGender);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
}