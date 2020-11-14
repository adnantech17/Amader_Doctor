package com.h10_fams.amaderdoctor.PatientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.R;

public class PatientLoginSignupActivity extends AppCompatActivity {

    private TextView tvLink;
    private Button login, register;
    private EditText etEmail, etPassword;
    private ProgressDialog progressDialog;
    private DatabaseReference patientRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login_signup);

        init();

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvLink.getText().toString().equals("Don't have an account")){
                    login.setVisibility(View.INVISIBLE);
                    register.setVisibility(View.VISIBLE);
                    tvLink.setText("Already have an account");
                }

                else {
                    login.setVisibility(View.VISIBLE);
                    register.setVisibility(View.INVISIBLE);
                    tvLink.setText("Don't have an account");
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(PatientLoginSignupActivity.this);
                progressDialog.setTitle("Login in progress");
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();

                if(email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(PatientLoginSignupActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();

                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();

                                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("infos");
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()) {
                                            signIn();
                                        }

                                        else {
                                            Intent intent = new Intent(PatientLoginSignupActivity.this, PatientRegisterInfoActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            else {
                                progressDialog.dismiss();
                                Toast.makeText(PatientLoginSignupActivity.this, "Sorry, Email or Password doesn't match.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(PatientLoginSignupActivity.this);
                progressDialog.setTitle("Register in progress");
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();

                if(email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(PatientLoginSignupActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();

                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(PatientLoginSignupActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PatientLoginSignupActivity.this, PatientRegisterInfoActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(PatientLoginSignupActivity.this, "Sorry, Already have an account with this email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void signIn() {
        Toast.makeText(PatientLoginSignupActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(PatientLoginSignupActivity.this, PatientDashboardActivity.class);
        startActivity(intent);
        finish();

    }

    private void init() {
        tvLink = findViewById(R.id.tvLinkText);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        patientRef = FirebaseDatabase.getInstance().getReference().child("users").child("patients");
    }
}