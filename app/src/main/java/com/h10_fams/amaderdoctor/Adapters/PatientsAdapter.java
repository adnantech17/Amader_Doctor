package com.h10_fams.amaderdoctor.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.Models.Patient;
import com.h10_fams.amaderdoctor.R;

import java.util.ArrayList;
import java.util.List;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.ViewHolder> {
    List<Patient> patients = new ArrayList<>();
    Context context;

    public PatientsAdapter(List<Patient> patients, Context context) {
        this.patients = patients;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.patient_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(patients.get(position).getName());
        holder.tvGender.setText(patients.get(position).getGender());
        holder.tvSituation.setText("");
        holder.tvEmail.setText(patients.get(position).getName());

        String drId[] = {""};

        FirebaseDatabase.getInstance().getReference().child("users").child(patients.get(position).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Log.d("LLLLL", ds.getKey());
                    if (ds.getKey().equals("condition")) {
                        drId[0] = ds.getValue(String.class);
                        holder.tvSituation.setText(drId[0]);

                        if(drId[0].equals("critical")) holder.tvSituation.setTextColor(Color.RED);
                        else if(drId[0].equals("mild")) holder.tvSituation.setTextColor(Color.GREEN);
                        else holder.tvSituation.setTextColor(Color.GRAY);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvGender, tvSituation;

        public ViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.tvName);
            tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            tvGender = (TextView) view.findViewById(R.id.tvGender);
            tvSituation = (TextView) view.findViewById(R.id.tvSituation);
        }
    }
}
