package com.h10_fams.amaderdoctor.PatientFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h10_fams.amaderdoctor.R;
import com.h10_fams.amaderdoctor.RecordActivity;

public class HomeFragment extends Fragment {
    CardView cvHistory, cvUpload, cvHospitals, cvRecord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        cvHistory = view.findViewById(R.id.cvHistory);
        cvUpload = view.findViewById(R.id.cvUpload);
        cvHospitals = view.findViewById(R.id.cvHospitals);
        cvRecord = view.findViewById(R.id.cvRecord);

        cvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cvHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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