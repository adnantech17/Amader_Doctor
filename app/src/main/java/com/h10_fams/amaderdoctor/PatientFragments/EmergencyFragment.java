package com.h10_fams.amaderdoctor.PatientFragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.h10_fams.amaderdoctor.R;

public class EmergencyFragment extends Fragment {
    CardView cvAppointment, cvNews, cvTips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);

        cvAppointment = view.findViewById(R.id.cvAppointment);
        cvNews = view.findViewById(R.id.cvNews);
        cvTips = view.findViewById(R.id.cvTips);

        cvAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cvNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cvTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}