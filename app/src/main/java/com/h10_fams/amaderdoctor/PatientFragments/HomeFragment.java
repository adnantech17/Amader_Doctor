package com.h10_fams.amaderdoctor.PatientFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h10_fams.amaderdoctor.PatientActivities.DoctorListActivity;
import com.h10_fams.amaderdoctor.PatientActivities.MyDoctorActivity;
import com.h10_fams.amaderdoctor.R;
import com.h10_fams.amaderdoctor.PatientActivities.RecordActivity;

public class HomeFragment extends Fragment {
    CardView cvDoctor, cvUpload, cvHospitals, cvRecord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        cvDoctor = view.findViewById(R.id.cvHistory);
        cvHospitals = view.findViewById(R.id.cvHospitals);
        cvRecord = view.findViewById(R.id.cvRecord);

        cvDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyDoctorActivity.class);
                startActivity(intent);
            }
        });

        cvHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DoctorListActivity.class));
            }
        });

        cvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RecordActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
}