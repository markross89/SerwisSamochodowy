
package com.example.serwissamochodowy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.model.Vehicle;
import com.example.serwissamochodowy.ui.adapter.VehicleAdapter;
import com.example.serwissamochodowy.dao.VehicleDao;

import java.util.List;

public class MyVehiclesActivity extends AppCompatActivity {

    private RecyclerView rvVehicles;
    private LinearLayout layoutEmptyVehicles;
    private Button btnAddVehicle;

    private VehicleDao vehicleDao;
    private VehicleAdapter vehicleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicles);

        initializeViews();

        vehicleDao = new VehicleDao(this);

        setupRecyclerView();
        setupListeners();
    }

    private void initializeViews() {
        rvVehicles = findViewById(R.id.rvVehicles);
        layoutEmptyVehicles = findViewById(R.id.layoutEmptyVehicles);
        btnAddVehicle = findViewById(R.id.btnAddVehicle);
    }

    private void setupRecyclerView() {
        rvVehicles.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupListeners() {
        btnAddVehicle.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MyVehiclesActivity.this,
                    AddVehicleActivity.class
            );

            startActivity(intent);
        });
    }

    private void loadVehicles() {

        List<Vehicle> vehicles = vehicleDao.getAllVehicles();

        if (vehicles.isEmpty()) {
            rvVehicles.setVisibility(View.GONE);
            layoutEmptyVehicles.setVisibility(View.VISIBLE);
        } else {
            layoutEmptyVehicles.setVisibility(View.GONE);
            rvVehicles.setVisibility(View.VISIBLE);

            vehicleAdapter = new VehicleAdapter(vehicles, this::showDeleteConfirmation);
            rvVehicles.setAdapter(vehicleAdapter);
        }
    }

    private void showDeleteConfirmation(Vehicle vehicle) {

        new AlertDialog.Builder(this)
                .setTitle("Usuń pojazd")
                .setMessage("Czy na pewno chcesz usunąć pojazd "
                        + vehicle.getBrand() + " " + vehicle.getModel() + "?")
                .setPositiveButton("Usuń", (dialog, which) -> {
                    deleteVehicle(vehicle);
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void deleteVehicle(Vehicle vehicle) {

        boolean deleted = vehicleDao.deleteVehicle(vehicle.getId());

        if (deleted) {
            Toast.makeText(
                    this,
                    "Pojazd został usunięty",
                    Toast.LENGTH_SHORT
            ).show();

            loadVehicles();
        } else {
            Toast.makeText(
                    this,
                    "Nie udało się usunąć pojazdu",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVehicles();
    }
}
