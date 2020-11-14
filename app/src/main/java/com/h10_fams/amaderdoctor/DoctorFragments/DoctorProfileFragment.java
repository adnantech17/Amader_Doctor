package com.h10_fams.amaderdoctor.DoctorFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.h10_fams.amaderdoctor.DoctorDashboardActivity;
import com.h10_fams.amaderdoctor.PatientActivities.PatientDashboardActivity;
import com.h10_fams.amaderdoctor.R;

public class DoctorProfileFragment extends Fragment {

    EditText etEmail, etPass, etNewPass, etName;
    Button btnLoc, btnSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPass = view.findViewById(R.id.etPass);
        etNewPass = view.findViewById(R.id.etNewPass);
        etName = view.findViewById(R.id.etName);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnLoc = view.findViewById(R.id.btnLoc);

        etName.setText(DoctorDashboardActivity.doctor.getName());
        etEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), etPass.getText().toString());

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(etNewPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getContext(), "Password not Updated", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getContext(), "Error Auth", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        return view;
    }
}