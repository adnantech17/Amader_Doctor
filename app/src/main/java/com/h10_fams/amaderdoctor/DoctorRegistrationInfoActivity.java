package com.h10_fams.amaderdoctor;

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

import java.util.HashMap;

public class DoctorRegistrationInfoActivity extends AppCompatActivity {

    TextView tvSpecialityWarning, tvNameWarning, tvAgeWarning, tvDateWarning, tvPhoneNumberWarning;
    EditText etName, etSpeciality, etAge, etPhoneNumber, etDD, etMM, etYY;
    Spinner spnGender;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration_info);

        init();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAllData()) {
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors").child(userID).child("infos");

                    HashMap<String, String> hashMap;
                    hashMap = getAllData();
                    ref.setValue(hashMap);
                    Intent intent = new Intent(DoctorRegistrationInfoActivity.this, DoctorDashboardActivity.class);
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
        hashMap.put("speciality", etSpeciality.getText().toString());
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
        else if(etSpeciality.getText().toString().isEmpty()) {
            tvSpecialityWarning.setVisibility(View.VISIBLE);
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
        tvSpecialityWarning = findViewById(R.id.tvHeightWarning);
        tvNameWarning = findViewById(R.id.tvNameWarning);
        tvAgeWarning = findViewById(R.id.tvAgeWarning);
        tvDateWarning = findViewById(R.id.tvDateWarning);
        tvPhoneNumberWarning = findViewById(R.id.tvPhoneNumberWarning);

        etName = findViewById(R.id.etName);
        etSpeciality = findViewById(R.id.etSpeciality);
        etAge = findViewById(R.id.etAge);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etDD = findViewById(R.id.etDD);
        etMM = findViewById(R.id.etMM);
        etYY = findViewById(R.id.etYY);

        spnGender = findViewById(R.id.spnGender);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
}