
package com.example.serwissamochodowy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.model.ServiceRequest;
import com.example.serwissamochodowy.ui.adapter.ServiceRequestAdapter;
import com.example.serwissamochodowy.dao.ServiceRequestDao;
import com.example.serwissamochodowy.session.SessionManager;
import com.example.serwissamochodowy.dao.VehicleDao;


public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private ServiceRequestDao serviceRequestDao;
    private VehicleDao vehicleDao;

    private TextView tvNoServiceRequest;
    private RecyclerView rvServiceRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        serviceRequestDao = new ServiceRequestDao(this);
        vehicleDao = new VehicleDao(this);

        tvNoServiceRequest = findViewById(R.id.tvNoServiceRequest);
        tvNoServiceRequest = findViewById(R.id.tvNoServiceRequest);
        rvServiceRequests = findViewById(R.id.rvServiceRequests);

        rvServiceRequests.setLayoutManager(new LinearLayoutManager(this));
        rvServiceRequests.setNestedScrollingEnabled(false);

        ImageButton btnNotifications = findViewById(R.id.btnNotifications);
        ImageButton btnLogout = findViewById(R.id.btnLogout);
        ImageButton btnSettings = findViewById(R.id.btnSettings);

        View btnVehicles = findViewById(R.id.btnVehicles);
        View btnBookVisit = findViewById(R.id.btnBookVisit);

        View btnWorkshop = findViewById(R.id.btnWorkshop);


        btnVehicles.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyVehiclesActivity.class);
            startActivity(intent);
        });

        btnBookVisit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookVisitActivity.class);
            startActivity(intent);
        });

        btnNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            sessionManager.clearSession();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        btnWorkshop.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WorkshopActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadServiceRequests();
    }

    private void loadServiceRequests() {

        List<ServiceRequest> allRequests = serviceRequestDao.getAllRequests();
        List<ServiceRequest> activeRequests = new ArrayList<>();

        for (ServiceRequest request : allRequests) {
            if (!"ANULOWANA".equals(request.getStatus())) {
                activeRequests.add(request);
            }
        }

        if (activeRequests.isEmpty()) {
            tvNoServiceRequest.setVisibility(View.VISIBLE);
            rvServiceRequests.setVisibility(View.GONE);
            return;
        }

        tvNoServiceRequest.setVisibility(View.GONE);
        rvServiceRequests.setVisibility(View.VISIBLE);

        ServiceRequestAdapter serviceRequestAdapter = new ServiceRequestAdapter(
                this,
                activeRequests,
                vehicleDao,
                serviceRequestDao
        );

        rvServiceRequests.setAdapter(serviceRequestAdapter);
    }

    private String formatDate(String date) {

        try {
            SimpleDateFormat inputFormat =
                    new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            SimpleDateFormat outputFormat =
                    new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

            Date parsedDate = inputFormat.parse(date);

            if (parsedDate != null) {
                return outputFormat.format(parsedDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private String getStatusText(String status) {

        if ("OCZEKUJE_NA_POTWIERDZENIE".equals(status)) {
            return getString(R.string.request_status_pending);
        }

        if ("POTWIERDZONA".equals(status)) {
            return getString(R.string.request_status_confirmed);
        }

        if ("ANULOWANA".equals(status)) {
            return getString(R.string.request_status_cancelled);
        }

        return getString(R.string.request_status_unknown);
    }
}
