
package com.example.serwissamochodowy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.serwissamochodowy.database.DatabaseHelper;

public class WorkshopPreferenceDao {

    private final DatabaseHelper dbHelper;

    public WorkshopPreferenceDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // =========================
    // USTAWIENIE PREFEROWANEGO WARSZTATU
    // =========================

    public void setPreferredWorkshop(int workshopId) {

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(
                    DatabaseHelper.COLUMN_PREFERRED_WORKSHOP_ID,
                    workshopId
            );

            db.insertWithOnConflict(
                    DatabaseHelper.TABLE_USER_PREFERENCES,
                    null,
                    createPreferenceValues(workshopId),
                    SQLiteDatabase.CONFLICT_REPLACE
            );

        }
    }

    private ContentValues createPreferenceValues(int workshopId) {

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_PREFERENCE_ID, 1);
        values.put(DatabaseHelper.COLUMN_PREFERRED_WORKSHOP_ID, workshopId);

        return values;
    }

    // =========================
    // POBRANIE PREFEROWANEGO WARSZTATU
    // =========================

    public int getPreferredWorkshopId() {

        try (SQLiteDatabase db = dbHelper.getReadableDatabase(); Cursor cursor = db.query(
                DatabaseHelper.TABLE_USER_PREFERENCES,
                new String[]{
                        DatabaseHelper.COLUMN_PREFERRED_WORKSHOP_ID
                },
                DatabaseHelper.COLUMN_PREFERENCE_ID + " = ?",
                new String[]{"1"},
                null,
                null,
                null
        )) {

            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }

            return -1;

        }
    }

    // =========================
    // USUNIĘCIE PREFERENCJI
    // =========================

    public void clearPreferredWorkshop() {

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.delete(
                    DatabaseHelper.TABLE_USER_PREFERENCES,
                    DatabaseHelper.COLUMN_PREFERENCE_ID + " = ?",
                    new String[]{"1"}
            );

        }
    }
}
