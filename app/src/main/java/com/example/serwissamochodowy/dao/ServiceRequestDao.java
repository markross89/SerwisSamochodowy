package com.example.serwissamochodowy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.serwissamochodowy.model.ServiceRequest;
import com.example.serwissamochodowy.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ServiceRequestDao {

    private final DatabaseHelper dbHelper;

    public ServiceRequestDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Zapis nowego zgłoszenia
    public long insertRequest(ServiceRequest request) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_REQUEST_VEHICLE_ID, request.getVehicleId());
        values.put(DatabaseHelper.COLUMN_REQUEST_CATEGORY, request.getCategory());
        values.put(DatabaseHelper.COLUMN_REQUEST_SUBCATEGORY, request.getSubcategory());
        values.put(DatabaseHelper.COLUMN_REQUEST_SERVICE_NAME, request.getServiceName());
        values.put(DatabaseHelper.COLUMN_REQUEST_PROBLEM, request.getProblemDescription());
        values.put(DatabaseHelper.COLUMN_REQUEST_DATE, request.getVisitDate());
        values.put(DatabaseHelper.COLUMN_REQUEST_TIME, request.getVisitTime());
        values.put(DatabaseHelper.COLUMN_REQUEST_STATUS, request.getStatus());
        values.put(
                DatabaseHelper.COLUMN_REQUEST_CREATED_AT,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .format(new Date())
        );

        return db.insert(DatabaseHelper.TABLE_SERVICE_REQUESTS, null, values);
    }

    // Pobranie wszystkich zgłoszeń
    public List<ServiceRequest> getAllRequests() {

        List<ServiceRequest> requests = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_SERVICE_REQUESTS,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_REQUEST_CREATED_AT + " DESC"
        );

        try {
            while (cursor.moveToNext()) {

                ServiceRequest request = new ServiceRequest(
                        cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_VEHICLE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_SUBCATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_SERVICE_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_PROBLEM)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_STATUS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_CREATED_AT))
                );

                requests.add(request);
            }
        } finally {
            cursor.close();
        }

        return requests;
    }

    // Pobranie pojedynczego zgłoszenia
    public ServiceRequest getRequestById(long requestId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_SERVICE_REQUESTS,
                null,
                DatabaseHelper.COLUMN_REQUEST_ID + " = ?",
                new String[]{String.valueOf(requestId)},
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()) {

                return new ServiceRequest(
                        cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_VEHICLE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_SUBCATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_SERVICE_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_PROBLEM)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_STATUS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REQUEST_CREATED_AT))
                );
            }
        } finally {
            cursor.close();
        }

        return null;
    }

    // Aktualizacja statusu zgłoszenia
    public int updateStatus(long requestId, String newStatus) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_REQUEST_STATUS, newStatus);

        return db.update(
                DatabaseHelper.TABLE_SERVICE_REQUESTS,
                values,
                DatabaseHelper.COLUMN_REQUEST_ID + " = ?",
                new String[]{String.valueOf(requestId)}
        );
    }

    // Anulowanie zgłoszenia
    // Anulowanie zgłoszenia
    public int cancelRequest(long requestId) {

        ServiceRequest request = getRequestById(requestId);

        if (request == null) {
            return 0;
        }

        // Potwierdzone zgłoszenie wymaga kontaktu z warsztatem.
        if ("POTWIERDZONA".equals(request.getStatus())) {
            return 0;
        }

        // Nie anulujemy ponownie anulowanego zgłoszenia.
        if ("ANULOWANA".equals(request.getStatus())) {
            return 0;
        }

        return updateStatus(requestId, "ANULOWANA");
    }

    // Usuwanie zgłoszenia
    public int deleteRequest(long requestId) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(
                DatabaseHelper.TABLE_SERVICE_REQUESTS,
                DatabaseHelper.COLUMN_REQUEST_ID + " = ?",
                new String[]{String.valueOf(requestId)}
        );
    }
}