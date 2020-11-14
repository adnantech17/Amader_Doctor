package com.h10_fams.amaderdoctor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.Models.Doctor;
import com.h10_fams.amaderdoctor.R;

import java.util.ArrayList;
import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {
    List<Doctor> doctors = new ArrayList<>();
    Context context;

    public DoctorsAdapter(List<Doctor> doctors, Context context) {
        this.doctors = doctors;
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_item, parent, false);

        return new DoctorsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorsAdapter.ViewHolder holder, int position) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("requests").child(doctors.get(position).getUid());
        holder.tvName.setText(doctors.get(position).getName());
        holder.tvGender.setText(doctors.get(position).getGender());
        holder.tvSpeciality.setText(doctors.get(position).getSpeciality());
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.push().setValue(userID).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();
                            holder.btnAdd.setEnabled(false);
                        }
                    }
                });
            }
        });

        FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("assigned").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    holder.btnAdd.setEnabled(false);
                    removeData(userID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void removeData(String userID) {
        FirebaseDatabase.getInstance().getReference().child("requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    ds.getRef().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dss : snapshot.getChildren()) {
                                String string = dss.getValue(String.class);
                                if (string.equals(userID)) {
                                    dss.getRef().removeValue();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvGender, tvSpeciality;
        Button btnAdd;

        public ViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.tvName);
            btnAdd =  view.findViewById(R.id.btnAdd);
            tvGender = (TextView) view.findViewById(R.id.tvGender);
            tvSpeciality = (TextView) view.findViewById(R.id.tvSpeciality);
        }
    }
}

