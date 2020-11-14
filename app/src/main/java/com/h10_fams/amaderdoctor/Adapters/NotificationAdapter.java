package com.h10_fams.amaderdoctor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.h10_fams.amaderdoctor.Models.NotificationItem;
import com.h10_fams.amaderdoctor.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    List<NotificationItem> notificationItems = new ArrayList<>();
    Context context;

    public NotificationAdapter(List<NotificationItem> notificationItems, Context context) {
        this.notificationItems = notificationItems;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);

        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(notificationItems.get(position).getName());
        holder.tvTime.setText(notificationItems.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTime;

        public ViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.tvName);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
        }
    }

}
