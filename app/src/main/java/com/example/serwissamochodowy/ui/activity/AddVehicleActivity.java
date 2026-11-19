
package com.example.serwissamochodowy.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.model.Vehicle;
import com.example.serwissamochodowy.dao.VehicleDao;

public class AddVehicleActivity extends AppCompatActivity {

    private EditText etBrand;
    private EditText etModel;
    private EditText etRegistration;
    private EditText etVin;
    private EditText etColor;
    private EditText etRegistrationDate;

    private Button btnSaveVehicle;

    private VehicleDao vehicleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        initializeViews();

        vehicleDao = new VehicleDao(this);

        setupListeners();
    }

    // Inicjalizacja widoków
    private void initializeViews() {
        etBrand = findViewById(R.id.etBrand);
        etModel = findViewById(R.id.etModel);
        etRegistration = findViewById(R.id.etRegistration);
        etVin = findViewById(R.id.etVin);
        etColor = findViewById(R.id.etColor);
        etRegistrationDate = findViewById(R.id.etRegistrationDate);

        btnSaveVehicle = findViewById(R.id.btnSaveVehicle);
    }

    // Obsługa przycisku zapisu
    private void setupListeners() {
        btnSaveVehicle.setOnClickListener(v -> saveVehicle());
    }

    // Zapis pojazdu
    private void saveVehicle() {

        String brand = etBrand.getText().toString().trim();
        String model = etModel.getText().toString().trim();
        String registration = etRegistration.getText().toString().trim();
        String vin = etVin.getText().toString().trim();
        String color = etColor.getText().toString().trim();
        String registrationDate = etRegistrationDate.getText().toString().trim();

        // Walidacja wymaganych pól
        if (brand.isEmpty()) {
            etBrand.setError("Podaj markę pojazdu");
            etBrand.requestFocus();
            return;
        }

        if (model.isEmpty()) {
            etModel.setError("Podaj model pojazdu");
            etModel.requestFocus();
            return;
        }

        if (registration.isEmpty()) {
            etRegistration.setError("Podaj numer rejestracyjny");
            etRegistration.requestFocus();
            return;
        }

        // Tworzenie obiektu pojazdu
        Vehicle vehicle = new Vehicle(
                brand,
                model,
                registration,
                vin,
                color,
                registrationDate
        );

        // Zapis do bazy danych
        boolean success = vehicleDao.insertVehicle(vehicle);

        if (success) {
            Toast.makeText(
                    this,
                    "Pojazd został dodany",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {
            Toast.makeText(
                    this,
                    "Nie udało się zapisać pojazdu",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
