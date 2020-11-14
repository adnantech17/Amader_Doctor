package com.h10_fams.amaderdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.Adapters.PatientsAdapter;
import com.h10_fams.amaderdoctor.Models.Doctor;
import com.h10_fams.amaderdoctor.Models.Patient;

import java.util.ArrayList;

public class YourPatientsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PatientsAdapter adapter;
    public static ArrayList<Patient> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_patients);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("added").child(userID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String uid = ds.getValue(String.class);
                    DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("infos");
                    r.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Patient patient = snapshot.getValue(Patient.class);
                            list.add(patient);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.rercyclerViewAllPatients);
        adapter = new PatientsAdapter(list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}