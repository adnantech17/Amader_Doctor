package com.h10_fams.amaderdoctor.DoctorFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.Adapters.NotificationAdapter;
import com.h10_fams.amaderdoctor.Adapters.RequestAdapter;
import com.h10_fams.amaderdoctor.Models.NotificationItem;
import com.h10_fams.amaderdoctor.R;

import java.util.ArrayList;
import java.util.List;


public class DoctorNotificationFragment extends Fragment {

    RecyclerView recyclerViewNotifications;
    NotificationAdapter adapter;
    public static List<NotificationItem> lists = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_notification, container, false);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("notification").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lists.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    NotificationItem notification = ds.getValue(NotificationItem.class);
                    DoctorNotificationFragment.lists.add(notification);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerViewNotifications = view.findViewById(R.id.rercyclerViewNotifications);
        adapter = new NotificationAdapter(lists, getContext());
        recyclerViewNotifications.setAdapter(adapter);
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}