package com.h10_fams.amaderdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.DoctorFragments.DoctorNotificationFragment;
import com.h10_fams.amaderdoctor.DoctorFragments.DoctorHomeFragment;
import com.h10_fams.amaderdoctor.DoctorFragments.DoctorProfileFragment;
import com.h10_fams.amaderdoctor.Models.Doctor;
import com.h10_fams.amaderdoctor.Models.NotificationItem;

public class DoctorDashboardActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    public static Doctor doctor;
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        updateDoctor();
        createNotificationChannels();
        notificationManager = NotificationManagerCompat.from(this);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectorFragment = new DoctorHomeFragment();
                        break;
                    case R.id.nav_profile:
                        selectorFragment = new DoctorProfileFragment();
                        break;
                    case R.id.nav_emergency:
                        selectorFragment = new DoctorNotificationFragment();
                        break;
                }
                if(selectorFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectorFragment).commit();
                }
                return true;
            }

        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DoctorHomeFragment()).commit();

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("notification").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    NotificationItem notification = ds.getValue(NotificationItem.class);
                    sendOnChannel1(notification.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateDoctor() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("doctors").child(userID).child("infos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctor = snapshot.getValue(Doctor.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendOnChannel1(String message) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Alert")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is Channel 2");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}