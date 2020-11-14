package com.h10_fams.amaderdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecordActivity extends AppCompatActivity {
    private Button btnLipid, btnHormone, btnBloodCell;
    private TextView tvName, tvAge, tvDob, tvHeight, tvWeight, tvTemp, tvSugar, tvCold, tvCough;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        btnLipid = findViewById(R.id.btnLipid);
        btnHormone = findViewById(R.id.btnHormone);
        btnBloodCell = findViewById(R.id.btnBloodCell);

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
    }
}