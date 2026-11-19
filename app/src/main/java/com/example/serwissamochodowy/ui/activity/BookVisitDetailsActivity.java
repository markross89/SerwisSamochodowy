
package com.example.serwissamochodowy.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.model.Vehicle;
import com.example.serwissamochodowy.dao.VehicleDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BookVisitDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_CATEGORY = "category";
    private static final String EXTRA_SUBCATEGORY = "subcategory";
    private static final String EXTRA_SERVICE_NAME = "serviceName";
    private static final String EXTRA_PROBLEM_DESCRIPTION = "problemDescription";

    private static final String EXTRA_VEHICLE_ID = "vehicleId";
    private static final String EXTRA_DATE = "visitDate";
    private static final String EXTRA_TIME = "visitTime";

    private Spinner spinnerVehicle;
    private TextView tvNoVehicles;

    private Button btnChooseDate;
    private Button btnChooseTime;
    private Button btnNextDetails;

    private VehicleDao vehicleDao;
    private List<Vehicle> vehicles;

    private Calendar selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_visit_details);

        initializeViews();

        vehicleDao = new VehicleDao(this);

        loadVehicles();
        setupDatePicker();
        setupTimePicker();
        setupNextButton();
    }

    private void initializeViews() {
        spinnerVehicle = findViewById(R.id.spinnerVehicle);
        tvNoVehicles = findViewById(R.id.tvNoVehicles);

        btnChooseDate = findViewById(R.id.btnChooseDate);
        btnChooseTime = findViewById(R.id.btnChooseTime);
        btnNextDetails = findViewById(R.id.btnNextDetails);
    }

    private void loadVehicles() {

        vehicles = vehicleDao.getAllVehicles();

        if (vehicles == null || vehicles.isEmpty()) {

            spinnerVehicle.setVisibility(View.GONE);
            tvNoVehicles.setVisibility(View.VISIBLE);

            btnNextDetails.setEnabled(false);
            btnNextDetails.setAlpha(0.5f);

            return;
        }

        spinnerVehicle.setVisibility(View.VISIBLE);
        tvNoVehicles.setVisibility(View.GONE);

        List<String> vehicleNames = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {

            String vehicleName = vehicle.getBrand() + " "
                    + vehicle.getModel()
                    + " (" + vehicle.getRegistration() + ")";

            vehicleNames.add(vehicleName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                vehicleNames
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerVehicle.setAdapter(adapter);
    }

    private void setupDatePicker() {

        btnChooseDate.setOnClickListener(v -> {

            Calendar today = Calendar.getInstance();

            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH);
            int day = today.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {

                        selectedDate = Calendar.getInstance();

                        selectedDate.set(
                                selectedYear,
                                selectedMonth,
                                selectedDay
                        );

                        String date = String.format(
                                Locale.ROOT,
                                "%02d.%02d.%04d",
                                selectedDay,
                                selectedMonth + 1,
                                selectedYear
                        );

                        btnChooseDate.setText(date);
                    },
                    year,
                    month,
                    day
            );

            datePickerDialog.getDatePicker().setMinDate(
                    today.getTimeInMillis()
            );

            datePickerDialog.show();
        });
    }

    private void setupTimePicker() {

        btnChooseTime.setOnClickListener(v -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    (view, hourOfDay, minute) -> {

                        selectedTime = String.format(
                                Locale.ROOT,
                                "%02d:%02d",
                                hourOfDay,
                                minute
                        );

                        btnChooseTime.setText(selectedTime);
                    },
                    9,
                    0,
                    true
            );

            timePickerDialog.show();
        });
    }

    private void setupNextButton() {
        btnNextDetails.setOnClickListener(v -> validateForm());
    }

    private void validateForm() {

        if (vehicles == null || vehicles.isEmpty()) {
            showError(R.string.book_visit_vehicle_error);
            return;
        }

        if (spinnerVehicle.getSelectedItemPosition() < 0) {
            showError(R.string.book_visit_vehicle_error);
            return;
        }

        if (selectedDate == null) {
            showError(R.string.book_visit_date_error);
            return;
        }

        if (selectedTime == null) {
            showError(R.string.book_visit_time_error);
            return;
        }

        goToSummaryActivity();
    }

    private void showError(int messageResId) {
        android.widget.Toast.makeText(
                this,
                messageResId,
                android.widget.Toast.LENGTH_SHORT
        ).show();
    }

    private void goToSummaryActivity() {

        Vehicle selectedVehicle =
                vehicles.get(spinnerVehicle.getSelectedItemPosition());

        String date = String.format(
                Locale.ROOT,
                "%04d-%02d-%02d",
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH) + 1,
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );

        Intent intent = new Intent(
                BookVisitDetailsActivity.this,
                BookVisitSummaryActivity.class
        );

        // Dane z pierwszego ekranu
        intent.putExtra(
                EXTRA_CATEGORY,
                getIntent().getStringExtra(EXTRA_CATEGORY)
        );

        intent.putExtra(
                EXTRA_SUBCATEGORY,
                getIntent().getStringExtra(EXTRA_SUBCATEGORY)
        );

        intent.putExtra(
                EXTRA_SERVICE_NAME,
                getIntent().getStringExtra(EXTRA_SERVICE_NAME)
        );

        intent.putExtra(
                EXTRA_PROBLEM_DESCRIPTION,
                getIntent().getStringExtra(EXTRA_PROBLEM_DESCRIPTION)
        );

        // Dane z drugiego ekranu
        intent.putExtra(EXTRA_VEHICLE_ID, selectedVehicle.getId());
        intent.putExtra(EXTRA_DATE, date);
        intent.putExtra(EXTRA_TIME, selectedTime);

        startActivity(intent);
    }
}
