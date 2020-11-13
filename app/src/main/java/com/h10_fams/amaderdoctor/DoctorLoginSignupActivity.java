package com.h10_fams.amaderdoctor;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorLoginSignupActivity extends AppCompatActivity {
    private TextView tvLink;
    private Button login, register;
    private EditText etEmail, etPassword;
    private ProgressDialog progressDialog;
    private DatabaseReference doctorRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login_signup);

        init();

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.INVISIBLE);
                register.setVisibility(View.VISIBLE);
                tvLink.setVisibility(View.INVISIBLE);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(DoctorLoginSignupActivity.this);
                progressDialog.setTitle("Login.");
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();

                if(email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(DoctorLoginSignupActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();

                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                signIn();
                            }
                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(DoctorLoginSignupActivity.this);
                progressDialog.setTitle("Login.");
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();

                if(email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(DoctorLoginSignupActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();

                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(DoctorLoginSignupActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                                signIn();
                            }
                        }
                    });
                }
            }
        });
    }

    private void init() {
        tvLink = findViewById(R.id.tvLinkText);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        doctorRef = FirebaseDatabase.getInstance().getReference().child("users").child("drivers");
    }

    private void signIn() {
        Toast.makeText(DoctorLoginSignupActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        doctorRef.child(userID).setValue(true);

        Intent intent = new Intent(DoctorLoginSignupActivity.this, DoctorDashboardActivity.class);
        startActivity(intent);
        finish();
    }
}