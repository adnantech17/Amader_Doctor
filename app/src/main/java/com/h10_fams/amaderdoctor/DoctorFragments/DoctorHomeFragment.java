package com.h10_fams.amaderdoctor.DoctorFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h10_fams.amaderdoctor.R;
import com.h10_fams.amaderdoctor.YourPatientsActivity;
import com.h10_fams.amaderdoctor.YourRequestActivity;

public class DoctorHomeFragment extends Fragment {
    CardView cvRequests, cvPatients;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_doctor_home, container, false);

        cvRequests = view.findViewById(R.id.cvRequest);
        cvPatients = view.findViewById(R.id.cvPatients);
        cvRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), YourRequestActivity.class));
            }
        });

        cvPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), YourPatientsActivity.class));
            }
        });

        return view;
    }
}