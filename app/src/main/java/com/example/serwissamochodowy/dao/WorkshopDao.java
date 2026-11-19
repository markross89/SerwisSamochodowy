
package com.example.serwissamochodowy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.serwissamochodowy.model.Address;
import com.example.serwissamochodowy.model.Workshop;
import com.example.serwissamochodowy.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class WorkshopDao {

    private final DatabaseHelper dbHelper;

    public WorkshopDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    // =========================
    // DODAWANIE WARSZTATU
    // =========================

    public long insertWorkshop(Workshop workshop) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            values.put(
                    DatabaseHelper.COLUMN_WORKSHOP_NAME,
                    workshop.getName()
            );

            values.put(
                    DatabaseHelper.COLUMN_WORKSHOP_PHONE,
                    workshop.getPhone()
            );

            values.put(
                    DatabaseHelper.COLUMN_WORKSHOP_OPENING_HOURS,
                    workshop.getOpeningHours()
            );

            // Zapis adresu
            if (workshop.getAddress() != null) {

                values.put(DatabaseHelper.COLUMN_WORKSHOP_STREET,
                        workshop.getAddress().getStreet());

                values.put(DatabaseHelper.COLUMN_WORKSHOP_HOUSE_NUMBER,
                        workshop.getAddress().getHouseNumber());

                values.put(DatabaseHelper.COLUMN_WORKSHOP_FLAT_NUMBER,
                        workshop.getAddress().getFlatNumber());

                values.put(DatabaseHelper.COLUMN_WORKSHOP_CITY,
                        workshop.getAddress().getCity());

                values.put(DatabaseHelper.COLUMN_WORKSHOP_POSTAL_CODE,
                        workshop.getAddress().getPostalCode());

                values.put(DatabaseHelper.COLUMN_WORKSHOP_COUNTRY,
                        workshop.getAddress().getCountry());
            }

            return db.insert(
                    DatabaseHelper.TABLE_WORKSHOPS,
                    null,
                    values
            );

        } finally {
            db.close();
        }
    }

    // =========================
    // POBIERANIE WSZYSTKICH WARSZTATÓW
    // =========================

    public List<Workshop> getAllWorkshops() {

        List<Workshop> workshopList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.query(
                DatabaseHelper.TABLE_WORKSHOPS,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_WORKSHOP_NAME + " ASC"
        )) {

            if (cursor.moveToFirst()) {

                do {
                    int id = cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_WORKSHOP_ID
                            )
                    );

                    String name = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_WORKSHOP_NAME
                            )
                    );

                    String phone = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_WORKSHOP_PHONE
                            )
                    );

                    String openingHours = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_WORKSHOP_OPENING_HOURS
                            )
                    );

                    // Odczyt adresu
                    String street = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_WORKSHOP_STREET
                            )
                    );

                    String houseNumber = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_WORKSHOP_HOUSE_NUMBER
                            )
                    );

                    String flatNumber = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_WORKSHOP_FLAT_NUMBER
                            )
                    );

                    String city = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_WORKSHOP_CITY
                            )
                    );

                    String postalCode = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_WORKSHOP_POSTAL_CODE
                            )
                    );

                    String country = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_WORKSHOP_COUNTRY
                            )
                    );

                    Address address = new Address(
                            street,
                            houseNumber,
                            flatNumber,
                            city,
                            postalCode,
                            country
                    );

                    Workshop workshop = new Workshop(
                            id,
                            name,
                            address,
                            phone,
                            openingHours
                    );

                    workshopList.add(workshop);

                } while (cursor.moveToNext());
            }

        } finally {
            db.close();
        }

        return workshopList;
    }

    // =========================
    // USUWANIE WARSZTATU
    // =========================

    public void deleteWorkshop(int workshopId) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.delete(
                    DatabaseHelper.TABLE_WORKSHOPS,
                    DatabaseHelper.COLUMN_WORKSHOP_ID + " = ?",
                    new String[]{String.valueOf(workshopId)}
            );

        } finally {
            db.close();
        }
    }

    public void insertSampleWorkshops() {

        if (!getAllWorkshops().isEmpty()) {
            return;
        }

        insertWorkshop(new Workshop(
                0,
                "Auto Serwis Premium",
                new Address(
                        "Warszawska",
                        "15",
                        "",
                        "Izbica Kujawska",
                        "87-865",
                        "Polska"
                ),
                "500600700",
                "Poniedziałek - Piątek: 08:00 - 17:00\nSobota: 09:00 - 13:00"
        ));

        insertWorkshop(new Workshop(
                0,
                "Mechanika Samochodowa",
                new Address(
                        "Toruńska",
                        "24",
                        "",
                        "Włocławek",
                        "87-800",
                        "Polska"
                ),
                "510620730",
                "Poniedziałek - Piątek: 07:00 - 16:00\nSobota: nieczynne"
        ));

        insertWorkshop(new Workshop(
                0,
                "Auto Centrum",
                new Address(
                        "Poznańska",
                        "8",
                        "",
                        "Kruszwica",
                        "88-150",
                        "Polska"
                ),
                "520630740",
                "Poniedziałek - Piątek: 08:00 - 18:00\nSobota: 08:00 - 14:00"
        ));
    }

}
