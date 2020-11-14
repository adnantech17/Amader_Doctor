package com.h10_fams.amaderdoctor.PatientFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.h10_fams.amaderdoctor.R;

public class ProfileFragment extends Fragment {

    EditText etEmail, etPass, etNewPass, etName;
    Button btnLoc, btnSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPass = view.findViewById(R.id.etPass);
        etNewPass = view.findViewById(R.id.etNewPass);
        etName = view.findViewById(R.id.etName);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnLoc = view.findViewById(R.id.btnLoc);

        btnSubmit.setEnabled(false);

        etNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(etNewPass.getText().toString().length() >= 6) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;
    }
}