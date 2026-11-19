
package com.example.serwissamochodowy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "car_service.db";
    private static final int DATABASE_VERSION = 6;

    // =========================
    // TABELA POJAZDÓW
    // =========================

    public static final String TABLE_VEHICLES = "vehicles";
    public static final String COLUMN_VEHICLE_ID = "id";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_REGISTRATION = "registration";
    public static final String COLUMN_VIN = "vin";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_REG_DATE = "registration_date";

    // =========================
    // TABELA HISTORII NAPRAW
    // =========================

    public static final String TABLE_REPAIRS = "repairs";
    public static final String COLUMN_REPAIR_ID = "id";
    public static final String COLUMN_REPAIR_VEHICLE_ID = "vehicle_id";
    public static final String COLUMN_REPAIR_DATE = "repair_date";
    public static final String COLUMN_REPAIR_DESC = "description";
    public static final String COLUMN_REPAIR_COST = "cost";

    // =========================
    // TABELA WARSZTATÓW
    // =========================

    public static final String TABLE_WORKSHOPS = "workshops";
    public static final String COLUMN_WORKSHOP_ID = "id";
    public static final String COLUMN_WORKSHOP_NAME = "name";
    public static final String COLUMN_WORKSHOP_PHONE = "phone";
    public static final String COLUMN_WORKSHOP_STREET = "street";
    public static final String COLUMN_WORKSHOP_HOUSE_NUMBER = "house_number";
    public static final String COLUMN_WORKSHOP_FLAT_NUMBER = "flat_number";
    public static final String COLUMN_WORKSHOP_POSTAL_CODE = "postal_code";
    public static final String COLUMN_WORKSHOP_CITY = "city";
    public static final String COLUMN_WORKSHOP_COUNTRY = "country";
    public static final String COLUMN_WORKSHOP_OPENING_HOURS = "opening_hours";

    // =========================
    // TABELA UŻYTKOWNIKÓW
    // =========================

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_FIRST_NAME = "first_name";
    public static final String COLUMN_USER_LAST_NAME = "last_name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PHONE = "phone";

    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_HOUSE_NUMBER = "house_number";
    public static final String COLUMN_FLAT_NUMBER = "flat_number";
    public static final String COLUMN_POSTAL_CODE = "postal_code";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_COUNTRY = "country";

    // =========================
    // TABELA ZGŁOSZEŃ SERWISOWYCH
    // =========================

    public static final String TABLE_SERVICE_REQUESTS = "service_requests";
    public static final String COLUMN_REQUEST_ID = "id";
    public static final String COLUMN_REQUEST_VEHICLE_ID = "vehicle_id";
    public static final String COLUMN_REQUEST_CATEGORY = "category";
    public static final String COLUMN_REQUEST_SUBCATEGORY = "subcategory";
    public static final String COLUMN_REQUEST_SERVICE_NAME = "service_name";
    public static final String COLUMN_REQUEST_PROBLEM = "problem_description";
    public static final String COLUMN_REQUEST_DATE = "visit_date";
    public static final String COLUMN_REQUEST_TIME = "visit_time";
    public static final String COLUMN_REQUEST_STATUS = "status";
    public static final String COLUMN_REQUEST_CREATED_AT = "created_at";

    // =========================
    // TABELA PREFERENCJI
    // =========================

    public static final String TABLE_USER_PREFERENCES = "user_preferences";
    public static final String COLUMN_PREFERENCE_ID = "id";
    public static final String COLUMN_PREFERRED_WORKSHOP_ID = "preferred_workshop_id";

    // =========================
    // TWORZENIE TABEL
    // =========================

    private static final String CREATE_TABLE_VEHICLES =
            "CREATE TABLE " + TABLE_VEHICLES + " (" +
                    COLUMN_VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BRAND + " TEXT, " +
                    COLUMN_MODEL + " TEXT, " +
                    COLUMN_REGISTRATION + " TEXT, " +
                    COLUMN_VIN + " TEXT, " +
                    COLUMN_COLOR + " TEXT, " +
                    COLUMN_REG_DATE + " TEXT);";

    private static final String CREATE_TABLE_REPAIRS =
            "CREATE TABLE " + TABLE_REPAIRS + " (" +
                    COLUMN_REPAIR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_REPAIR_VEHICLE_ID + " INTEGER, " +
                    COLUMN_REPAIR_DATE + " TEXT, " +
                    COLUMN_REPAIR_DESC + " TEXT, " +
                    COLUMN_REPAIR_COST + " REAL, " +
                    "FOREIGN KEY(" + COLUMN_REPAIR_VEHICLE_ID + ") REFERENCES " +
                    TABLE_VEHICLES + "(" + COLUMN_VEHICLE_ID + "));";

    private static final String CREATE_TABLE_WORKSHOPS =
            "CREATE TABLE " + TABLE_WORKSHOPS + " (" +
                    COLUMN_WORKSHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_WORKSHOP_NAME + " TEXT, " +
                    COLUMN_WORKSHOP_PHONE + " TEXT, " +
                    COLUMN_WORKSHOP_STREET + " TEXT, " +
                    COLUMN_WORKSHOP_HOUSE_NUMBER + " TEXT, " +
                    COLUMN_WORKSHOP_FLAT_NUMBER + " TEXT, " +
                    COLUMN_WORKSHOP_POSTAL_CODE + " TEXT, " +
                    COLUMN_WORKSHOP_CITY + " TEXT, " +
                    COLUMN_WORKSHOP_COUNTRY + " TEXT, " +
                    COLUMN_WORKSHOP_OPENING_HOURS + " TEXT DEFAULT '');";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_USER_FIRST_NAME + " TEXT, " +
                    COLUMN_USER_LAST_NAME + " TEXT, " +
                    COLUMN_USER_EMAIL + " TEXT, " +
                    COLUMN_USER_PHONE + " TEXT, " +
                    COLUMN_STREET + " TEXT, " +
                    COLUMN_HOUSE_NUMBER + " TEXT, " +
                    COLUMN_FLAT_NUMBER + " TEXT, " +
                    COLUMN_POSTAL_CODE + " TEXT, " +
                    COLUMN_CITY + " TEXT, " +
                    COLUMN_COUNTRY + " TEXT);";

    private static final String CREATE_TABLE_SERVICE_REQUESTS =
            "CREATE TABLE " + TABLE_SERVICE_REQUESTS + " (" +
                    COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_REQUEST_VEHICLE_ID + " INTEGER NOT NULL, " +
                    COLUMN_REQUEST_CATEGORY + " TEXT, " +
                    COLUMN_REQUEST_SUBCATEGORY + " TEXT, " +
                    COLUMN_REQUEST_SERVICE_NAME + " TEXT NOT NULL, " +
                    COLUMN_REQUEST_PROBLEM + " TEXT NOT NULL, " +
                    COLUMN_REQUEST_DATE + " TEXT NOT NULL, " +
                    COLUMN_REQUEST_TIME + " TEXT NOT NULL, " +
                    COLUMN_REQUEST_STATUS + " TEXT NOT NULL, " +
                    COLUMN_REQUEST_CREATED_AT + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_USER_PREFERENCES =
            "CREATE TABLE " + TABLE_USER_PREFERENCES + " (" +
                    COLUMN_PREFERENCE_ID + " INTEGER PRIMARY KEY CHECK (" +
                    COLUMN_PREFERENCE_ID + " = 1), " +
                    COLUMN_PREFERRED_WORKSHOP_ID + " INTEGER);";

    // =========================
    // KONSTRUKTOR
    // =========================

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // =========================
    // TWORZENIE BAZY
    // =========================

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_VEHICLES);
        db.execSQL(CREATE_TABLE_REPAIRS);
        db.execSQL(CREATE_TABLE_WORKSHOPS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_SERVICE_REQUESTS);
        db.execSQL(CREATE_TABLE_USER_PREFERENCES);
    }

    // =========================
    // AKTUALIZACJA BAZY
    // =========================

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 5) {
            db.execSQL(CREATE_TABLE_SERVICE_REQUESTS);
        }

        if (oldVersion < 6) {
            db.execSQL(
                    "ALTER TABLE " + TABLE_WORKSHOPS +
                            " ADD COLUMN " + COLUMN_WORKSHOP_OPENING_HOURS +
                            " TEXT DEFAULT '';"
            );

            db.execSQL(CREATE_TABLE_USER_PREFERENCES);
        }
    }
}
