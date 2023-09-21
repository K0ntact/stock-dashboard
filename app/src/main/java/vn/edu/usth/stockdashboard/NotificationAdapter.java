package vn.edu.usth.stockdashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationItem>  notificationList;

    public NotificationAdapter(List<NotificationItem> notificationList){
        this.notificationList = notificationList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationItem notificationItem = notificationList.get(position);
        holder.titleTextView.setText(notificationItem.getTitle());
        holder.descriptionTextView.setText(notificationItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleNotification);
            descriptionTextView = view.findViewById(R.id.descriptionNotification);
        }
    }
}
