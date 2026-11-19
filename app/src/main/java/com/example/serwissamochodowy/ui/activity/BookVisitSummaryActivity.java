
package com.example.serwissamochodowy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.model.ServiceRequest;
import com.example.serwissamochodowy.dao.ServiceRequestDao;
import com.example.serwissamochodowy.model.Vehicle;
import com.example.serwissamochodowy.dao.VehicleDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookVisitSummaryActivity extends AppCompatActivity {

    private static final String EXTRA_CATEGORY = "category";
    private static final String EXTRA_SUBCATEGORY = "subcategory";
    private static final String EXTRA_SERVICE_NAME = "serviceName";
    private static final String EXTRA_PROBLEM_DESCRIPTION = "problemDescription";
    private static final String EXTRA_VEHICLE_ID = "vehicleId";
    private static final String EXTRA_DATE = "visitDate";
    private static final String EXTRA_TIME = "visitTime";

    private TextView tvSummaryServiceName;
    private TextView tvSummaryCategory;
    private TextView tvSummaryProblem;
    private TextView tvSummaryVehicle;
    private TextView tvSummaryDate;
    private TextView tvSummaryTime;

    private Button btnSubmitVisit;
    private Button btnCancelSummary;

    private VehicleDao vehicleDao;
    private ServiceRequestDao serviceRequestDao;

    private long vehicleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_visit_summary);

        initializeViews();

        vehicleDao = new VehicleDao(this);
        serviceRequestDao = new ServiceRequestDao(this);

        displaySummary();
        setupButtons();
    }

    private void initializeViews() {

        tvSummaryServiceName = findViewById(R.id.tvSummaryServiceName);
        tvSummaryCategory = findViewById(R.id.tvSummaryCategory);
        tvSummaryProblem = findViewById(R.id.tvSummaryProblem);

        tvSummaryVehicle = findViewById(R.id.tvSummaryVehicle);
        tvSummaryDate = findViewById(R.id.tvSummaryDate);
        tvSummaryTime = findViewById(R.id.tvSummaryTime);

        btnSubmitVisit = findViewById(R.id.btnSubmitVisit);
        btnCancelSummary = findViewById(R.id.btnCancelSummary);
    }

    private void displaySummary() {

        Intent intent = getIntent();

        String category = intent.getStringExtra(EXTRA_CATEGORY);
        String serviceName = intent.getStringExtra(EXTRA_SERVICE_NAME);
        String problemDescription =
                intent.getStringExtra(EXTRA_PROBLEM_DESCRIPTION);

        vehicleId = intent.getLongExtra(EXTRA_VEHICLE_ID, -1);

        String date = intent.getStringExtra(EXTRA_DATE);
        String time = intent.getStringExtra(EXTRA_TIME);

        // Nazwa usługi
        tvSummaryServiceName.setText(serviceName);

        // Sama nazwa kategorii, bez dodatkowego tekstu
        if (category == null || category.trim().isEmpty()) {
            tvSummaryCategory.setText(
                    R.string.book_visit_summary_no_category
            );
        } else {
            tvSummaryCategory.setText(category);
        }

        // Opis zgłoszenia
        tvSummaryProblem.setText(problemDescription);

        // Dane pojazdu
        Vehicle vehicle = vehicleDao.getVehicleById(vehicleId);

        if (vehicle != null) {
            String vehicleName = vehicle.getBrand() + " "
                    + vehicle.getModel()
                    + "\n" + vehicle.getRegistration();

            tvSummaryVehicle.setText(vehicleName);
        } else {
            tvSummaryVehicle.setText(
                    R.string.book_visit_summary_vehicle_error
            );
        }

        // Termin
        tvSummaryDate.setText(formatDate(date));

        tvSummaryTime.setText(
                getString(R.string.book_visit_summary_time, time)
        );
    }

    private String formatDate(String date) {

        if (date == null) {
            return "";
        }

        try {
            SimpleDateFormat inputFormat =
                    new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);

            SimpleDateFormat outputFormat =
                    new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

            Date parsedDate = inputFormat.parse(date);

            return getString(
                    R.string.book_visit_summary_date,
                    outputFormat.format(parsedDate)
            );

        } catch (ParseException e) {
            return date;
        }
    }

    private void setupButtons() {

        btnSubmitVisit.setOnClickListener(v -> submitVisit());

        btnCancelSummary.setOnClickListener(v -> finish());
    }

    private void submitVisit() {

        Intent intent = getIntent();

        String category = intent.getStringExtra(EXTRA_CATEGORY);
        String subcategory = intent.getStringExtra(EXTRA_SUBCATEGORY);
        String serviceName = intent.getStringExtra(EXTRA_SERVICE_NAME);
        String problemDescription =
                intent.getStringExtra(EXTRA_PROBLEM_DESCRIPTION);

        String date = intent.getStringExtra(EXTRA_DATE);
        String time = intent.getStringExtra(EXTRA_TIME);

        // Sprawdzenie, czy pojazd nadal istnieje
        Vehicle vehicle = vehicleDao.getVehicleById(vehicleId);

        if (vehicle == null) {
            Toast.makeText(
                    this,
                    R.string.book_visit_summary_vehicle_error,
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        // Utworzenie nowego zgłoszenia
        ServiceRequest request = new ServiceRequest(
                vehicleId,
                category,
                subcategory,
                serviceName,
                problemDescription,
                date,
                time,
                "OCZEKUJE_NA_POTWIERDZENIE",
                ""
        );

        // Zapis do SQLite
        long requestId = serviceRequestDao.insertRequest(request);

        if (requestId == -1) {
            Toast.makeText(
                    this,
                    R.string.book_visit_save_error,
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        Toast.makeText(
                this,
                R.string.book_visit_save_success,
                Toast.LENGTH_LONG
        ).show();

        // Powrót do ekranu głównego
        Intent mainIntent = new Intent(this, MainActivity.class);

        mainIntent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
        );

        startActivity(mainIntent);
        finish();
    }
}
