package com.example.serwissamochodowy.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serwissamochodowy.model.NotificationItem;
import com.example.serwissamochodowy.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final List<NotificationItem> notifications;
    private final OnNotificationClickListener clickListener;
    private final OnNotificationDeleteListener deleteListener;

    public NotificationAdapter(
            List<NotificationItem> notifications,
            OnNotificationClickListener clickListener,
            OnNotificationDeleteListener deleteListener
    ) {
        this.notifications = notifications;
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
    }

    public interface OnNotificationClickListener {
        void onNotificationClick(NotificationItem notification);
    }

    public interface OnNotificationDeleteListener {
        void onNotificationDelete(NotificationItem notification);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull NotificationViewHolder holder,
            int position
    ) {
        NotificationItem notification = notifications.get(position);

        holder.title.setText(notification.getTitle());
        holder.description.setText(notification.getDescription());
        holder.date.setText(notification.getDate());
        holder.icon.setImageResource(notification.getIconRes());

        int backgroundColor;

        if (notification.isRead()) {
            backgroundColor = ContextCompat.getColor(
                    holder.itemView.getContext(),
                    R.color.card_background
            );
        } else {
            backgroundColor = ContextCompat.getColor(
                    holder.itemView.getContext(),
                    R.color.notification_unread_background
            );
        }

        holder.card.setCardBackgroundColor(backgroundColor);

        holder.container.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onNotificationClick(notification);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onNotificationDelete(notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        LinearLayout container;
        ImageView icon;
        TextView title;
        TextView description;
        TextView date;
        ImageButton btnDelete;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.cardNotification);
            container = itemView.findViewById(R.id.layoutNotification);
            icon = itemView.findViewById(R.id.ivNotificationIcon);
            title = itemView.findViewById(R.id.tvNotificationTitle);
            description = itemView.findViewById(R.id.tvNotificationDescription);
            date = itemView.findViewById(R.id.tvNotificationDate);
            btnDelete = itemView.findViewById(R.id.btnDeleteNotification);
        }
    }
}