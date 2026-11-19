package com.example.serwissamochodowy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.serwissamochodowy.model.Address;
import com.example.serwissamochodowy.model.User;
import com.example.serwissamochodowy.database.DatabaseHelper;

public class UserDao {

    private final DatabaseHelper dbHelper;

    public UserDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public void saveUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_USER_ID, user.getId());
        values.put(DatabaseHelper.COLUMN_USER_FIRST_NAME, user.getFirstName());
        values.put(DatabaseHelper.COLUMN_USER_LAST_NAME, user.getLastName());
        values.put(DatabaseHelper.COLUMN_USER_EMAIL, user.getEmail());
        values.put(DatabaseHelper.COLUMN_USER_PHONE, user.getPhone());

        if (user.getAddress() != null) {
            values.put(DatabaseHelper.COLUMN_STREET, user.getAddress().getStreet());
            values.put(DatabaseHelper.COLUMN_HOUSE_NUMBER, user.getAddress().getHouseNumber());
            values.put(DatabaseHelper.COLUMN_FLAT_NUMBER, user.getAddress().getFlatNumber());
            values.put(DatabaseHelper.COLUMN_CITY, user.getAddress().getCity());
            values.put(DatabaseHelper.COLUMN_POSTAL_CODE, user.getAddress().getPostalCode());
            values.put(DatabaseHelper.COLUMN_COUNTRY, user.getAddress().getCountry());
        }

        db.insertWithOnConflict(DatabaseHelper.TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public User getUser() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                null, null, null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_LAST_NAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PHONE));

            String street = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STREET));
            String houseNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOUSE_NUMBER));
            String flatNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FLAT_NUMBER));
            String city = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CITY));
            String postalCode = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POSTAL_CODE));
            String country = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COUNTRY));

            Address address = new Address(street, houseNumber, flatNumber, city, postalCode, country);
            user = new User(id, firstName, lastName, email, phone, address);

            cursor.close();
        }
        db.close();
        return user;
    }

    public void clearUser() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_USERS, null, null);
        db.close();
    }
}