
package com.example.serwissamochodowy.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serwissamochodowy.ui.adapter.NotificationAdapter;
import com.example.serwissamochodowy.model.NotificationItem;
import com.example.serwissamochodowy.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private NotificationAdapter notificationAdapter;
    private List<NotificationItem> allNotifications;
    private List<NotificationItem> displayedNotifications;

    private boolean showUnreadOnly = false;

    private RecyclerView rvNotifications;
    private View layoutEmptyNotifications;
    private TextView btnFilterAll;
    private TextView btnFilterUnread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        rvNotifications = findViewById(R.id.rvNotifications);
        layoutEmptyNotifications = findViewById(R.id.layoutEmptyNotifications);
        btnFilterAll = findViewById(R.id.btnFilterAll);
        btnFilterUnread = findViewById(R.id.btnFilterUnread);

        View btnClearNotifications =
                findViewById(R.id.btnClearNotifications);

        allNotifications = new ArrayList<>();
        displayedNotifications = new ArrayList<>();

        createSampleNotifications();

        rvNotifications.setLayoutManager(
                new LinearLayoutManager(this)
        );

        notificationAdapter = new NotificationAdapter(
                displayedNotifications,
                this::onNotificationClicked,
                this::onNotificationDeleted
        );

        rvNotifications.setAdapter(notificationAdapter);

        btnFilterAll.setOnClickListener(v -> {
            showUnreadOnly = false;
            updateNotifications();
        });

        btnFilterUnread.setOnClickListener(v -> {
            showUnreadOnly = true;
            updateNotifications();
        });

        btnClearNotifications.setOnClickListener(
                v -> confirmClearHistory()
        );

        updateNotifications();
    }

    private void createSampleNotifications() {

        allNotifications.add(new NotificationItem(
                "Pojazd gotowy do odbioru",
                "Twój pojazd jest już gotowy. Możesz odebrać go w godzinach pracy warsztatu.",
                "Dzisiaj, 14:32",
                R.drawable.ic_service_24,
                false
        ));

        allNotifications.add(new NotificationItem(
                "Potwierdzono wizytę",
                "Twoja wizyta została potwierdzona. Termin: 15 listopada, godz. 10:00.",
                "Dzisiaj, 10:15",
                R.drawable.ic_calendar_24,
                false
        ));

        allNotifications.add(new NotificationItem(
                "Przypomnienie o przeglądzie",
                "Zbliża się termin przeglądu technicznego Twojego pojazdu.",
                "Wczoraj, 09:20",
                R.drawable.ic_car_24,
                true
        ));

        allNotifications.add(new NotificationItem(
                "Aktualizacja statusu naprawy",
                "Warsztat rozpoczął prace przy Twoim pojeździe.",
                "6 listopada, 12:45",
                R.drawable.ic_service_24,
                true
        ));
    }

    private void updateNotifications() {

        displayedNotifications.clear();

        for (NotificationItem notification : allNotifications) {
            if (!showUnreadOnly || !notification.isRead()) {
                displayedNotifications.add(notification);
            }
        }

        notificationAdapter.notifyDataSetChanged();

        boolean isEmpty = displayedNotifications.isEmpty();

        rvNotifications.setVisibility(
                isEmpty ? View.GONE : View.VISIBLE
        );

        layoutEmptyNotifications.setVisibility(
                isEmpty ? View.VISIBLE : View.GONE
        );

        updateFilterButtons();
    }

    private void updateFilterButtons() {

        int selectedTextColor = getColor(R.color.white);
        int unselectedTextColor = getColor(R.color.secondary_text);

        if (showUnreadOnly) {

            btnFilterAll.setBackgroundResource(
                    R.drawable.bg_filter_unselected
            );

            btnFilterUnread.setBackgroundResource(
                    R.drawable.bg_filter_selected
            );

            btnFilterAll.setTextColor(unselectedTextColor);
            btnFilterUnread.setTextColor(selectedTextColor);

        } else {

            btnFilterAll.setBackgroundResource(
                    R.drawable.bg_filter_selected
            );

            btnFilterUnread.setBackgroundResource(
                    R.drawable.bg_filter_unselected
            );

            btnFilterAll.setTextColor(selectedTextColor);
            btnFilterUnread.setTextColor(unselectedTextColor);
        }
    }

    // Oznaczenie powiadomienia jako przeczytanego
    private void onNotificationClicked(NotificationItem notification) {

        notification.setRead(true);

        updateNotifications();

        Toast.makeText(
                this,
                "Powiadomienie przeczytane",
                Toast.LENGTH_SHORT
        ).show();
    }

    // Usunięcie pojedynczego powiadomienia
    private void onNotificationDeleted(NotificationItem notification) {

        allNotifications.remove(notification);

        updateNotifications();

        Toast.makeText(
                this,
                "Powiadomienie usunięte",
                Toast.LENGTH_SHORT
        ).show();
    }

    // Usunięcie całej historii
    private void confirmClearHistory() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.clear_history)
                .setMessage(R.string.clear_history_confirmation)
                .setNegativeButton(
                        R.string.cancel,
                        (dialog, which) -> dialog.dismiss()
                )
                .setPositiveButton(
                        R.string.clear_history,
                        (dialog, which) -> {
                            allNotifications.clear();

                            updateNotifications();

                            Toast.makeText(
                                    this,
                                    R.string.history_cleared,
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                )
                .show();
    }
}
