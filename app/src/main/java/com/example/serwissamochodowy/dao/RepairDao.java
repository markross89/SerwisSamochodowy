package com.example.serwissamochodowy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.serwissamochodowy.model.Repair;
import com.example.serwissamochodowy.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class RepairDao {

    private final DatabaseHelper dbHelper;

    public RepairDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    // --- 1. DODAWANIE NAPRAWY (CREATE) ---
    public boolean insertRepair(Repair repair) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_REPAIR_VEHICLE_ID, repair.getVehicleId());
        values.put(DatabaseHelper.COLUMN_REPAIR_DATE, repair.getRepairDate());
        values.put(DatabaseHelper.COLUMN_REPAIR_DESC, repair.getDescription());
        values.put(DatabaseHelper.COLUMN_REPAIR_COST, repair.getCost());

        long result = db.insert(DatabaseHelper.TABLE_REPAIRS, null, values);
        db.close();

        return result != -1;
    }

    // --- 2. POBIERANIE HISTORII NAPRAW DLA KONKRETNEGO AUTA (READ) ---
    public List<Repair> getRepairsForVehicle(long vehicleId) {
        List<Repair> repairList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_REPAIRS,
                null,
                DatabaseHelper.COLUMN_REPAIR_VEHICLE_ID + " = ?",
                new String[]{String.valueOf(vehicleId)},
                null, null,
                DatabaseHelper.COLUMN_REPAIR_DATE + " DESC" // Sortowanie: od najnowszych napraw
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPAIR_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPAIR_DATE));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPAIR_DESC));
                double cost = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPAIR_COST));

                Repair repair = new Repair(id, vehicleId, date, desc, cost);
                repairList.add(repair);
            }
            cursor.close();
        }
        db.close();
        return repairList;
    }

    // --- 3. AKTUALIZACJA KOSZTU / OPISU PO REWIZJI (UPDATE) ---
    public boolean updateRepair(Repair repair) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_REPAIR_DATE, repair.getRepairDate());
        values.put(DatabaseHelper.COLUMN_REPAIR_DESC, repair.getDescription());
        values.put(DatabaseHelper.COLUMN_REPAIR_COST, repair.getCost());

        int rowsAffected = db.update(
                DatabaseHelper.TABLE_REPAIRS,
                values,
                DatabaseHelper.COLUMN_REPAIR_ID + " = ?",
                new String[]{String.valueOf(repair.getId())}
        );
        db.close();

        return rowsAffected > 0;
    }

    // --- 4. USUWANIE NAPRAWY (DELETE) ---
    public boolean deleteRepair(long repairId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = db.delete(
                DatabaseHelper.TABLE_REPAIRS,
                DatabaseHelper.COLUMN_REPAIR_ID + " = ?",
                new String[]{String.valueOf(repairId)}
        );
        db.close();

        return rowsDeleted > 0;
    }
}