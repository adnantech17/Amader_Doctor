package com.h10_fams.amaderdoctor.PatientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.Adapters.DoctorsAdapter;
import com.h10_fams.amaderdoctor.Adapters.RequestAdapter;
import com.h10_fams.amaderdoctor.Models.Doctor;
import com.h10_fams.amaderdoctor.Models.Patient;
import com.h10_fams.amaderdoctor.R;

import java.util.ArrayList;

public class DoctorListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DoctorsAdapter adapter;
    public static ArrayList<Doctor> list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    Doctor doctor = ds.child("infos").getValue(Doctor.class);
                    // Log.d("LLLLL", "LLLLL");
                    list.add(doctor);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.rercyclerViewAllPatients);
        adapter = new DoctorsAdapter(list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}