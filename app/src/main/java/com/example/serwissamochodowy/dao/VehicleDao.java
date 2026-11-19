package com.example.serwissamochodowy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.serwissamochodowy.model.Vehicle;
import com.example.serwissamochodowy.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class VehicleDao {

    private final DatabaseHelper dbHelper;

    public VehicleDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    // --- DODAWANIE POJAZDU (CREATE) ---
    public boolean insertVehicle(Vehicle vehicle) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_BRAND, vehicle.getBrand());
        values.put(DatabaseHelper.COLUMN_MODEL, vehicle.getModel());
        values.put(DatabaseHelper.COLUMN_REGISTRATION, vehicle.getRegistration());
        values.put(DatabaseHelper.COLUMN_VIN, vehicle.getVin());
        values.put(DatabaseHelper.COLUMN_COLOR, vehicle.getColor());
        values.put(DatabaseHelper.COLUMN_REG_DATE, vehicle.getRegistrationDate());

        long result = db.insert(DatabaseHelper.TABLE_VEHICLES, null, values);
        db.close(); // Zamykamy połączenie po operacji

        return result != -1; // Zwraca true, jeśli zapis się powiódł
    }

    // --- AKTUALIZACJA POJAZDU (UPDATE) ---
    public boolean updateVehicle(Vehicle vehicle) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_BRAND, vehicle.getBrand());
        values.put(DatabaseHelper.COLUMN_MODEL, vehicle.getModel());
        values.put(DatabaseHelper.COLUMN_REGISTRATION, vehicle.getRegistration());
        values.put(DatabaseHelper.COLUMN_VIN, vehicle.getVin());
        values.put(DatabaseHelper.COLUMN_COLOR, vehicle.getColor());
        values.put(DatabaseHelper.COLUMN_REG_DATE, vehicle.getRegistrationDate());

        int rowsAffected = db.update(
                DatabaseHelper.TABLE_VEHICLES,
                values,
                DatabaseHelper.COLUMN_VEHICLE_ID + " = ?",
                new String[]{String.valueOf(vehicle.getId())}
        );
        db.close();

        return rowsAffected > 0;
    }

    // --- USUWANIE POJAZDU (DELETE) ---
    public boolean deleteVehicle(long vehicleId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = db.delete(
                DatabaseHelper.TABLE_VEHICLES,
                DatabaseHelper.COLUMN_VEHICLE_ID + " = ?",
                new String[]{String.valueOf(vehicleId)}
        );
        db.close();

        return rowsDeleted > 0;
    }


    // --- POBIERANIE WSZYSTKICH POJAZDÓW (READ) ---
    public List<Vehicle> getAllVehicles() {

        List<Vehicle> vehicles = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_VEHICLES,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_VEHICLE_ID + " DESC"
        );

        try {
            while (cursor.moveToNext()) {

                long id = cursor.getLong(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VEHICLE_ID));

                String brand = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BRAND));

                String model = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MODEL));

                String registration = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REGISTRATION));

                String vin = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VIN));

                String color = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COLOR));

                String registrationDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REG_DATE));

                vehicles.add(new Vehicle(
                        id, brand, model, registration,
                        vin, color, registrationDate
                ));
            }
        } finally {
            cursor.close();
            db.close();
        }

        return vehicles;
    }
    public Vehicle getVehicleById(long vehicleId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_VEHICLES,
                null,
                DatabaseHelper.COLUMN_VEHICLE_ID + " = ?",
                new String[]{String.valueOf(vehicleId)},
                null,
                null,
                null
        );

        Vehicle vehicle = null;

        if (cursor.moveToFirst()) {
            vehicle = new Vehicle(
                    cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VEHICLE_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BRAND)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MODEL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REGISTRATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VIN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COLOR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REG_DATE))
            );
        }

        cursor.close();
        db.close();

        return vehicle;
    }

}
