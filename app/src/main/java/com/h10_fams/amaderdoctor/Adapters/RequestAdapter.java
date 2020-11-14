package com.h10_fams.amaderdoctor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h10_fams.amaderdoctor.Models.Patient;
import com.h10_fams.amaderdoctor.R;
import com.h10_fams.amaderdoctor.YourPatientsActivity;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    List<Patient> patients = new ArrayList<>();
    Context context;

    public RequestAdapter(List<Patient> patients, Context context) {
        this.patients = patients;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_item, parent, false);

        return new RequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(patients.get(position).getName());
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("requests").child(userID);
        DatabaseReference pat = FirebaseDatabase.getInstance().getReference().child("added").child(userID);
        DatabaseReference assign = FirebaseDatabase.getInstance().getReference().child("users").child(patients.get(position).getUid()).child("assigned");

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YourPatientsActivity.list.add(patients.get(position));
                pat.push().setValue(patients.get(position).getUid());
                assign.setValue(userID);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            String string = ds.getValue(String.class);
                            if(string.equals(patients.get(position).getUid())) {
                                ds.getRef().removeValue();
                                patients.remove(position);
                                notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        Button btnAdd;

        public ViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tvName);
            btnAdd = view.findViewById(R.id.btnAdd);
        }
    }
}