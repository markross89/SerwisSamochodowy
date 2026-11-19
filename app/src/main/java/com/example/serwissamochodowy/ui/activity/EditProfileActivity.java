
package com.example.serwissamochodowy.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serwissamochodowy.model.Address;
import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.model.User;
import com.example.serwissamochodowy.dao.UserDao;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPhone;
    private EditText etStreet, etHouseNumber, etFlatNumber;
    private EditText etCity, etPostalCode, etCountry;

    private Button btnSaveProfile;

    private UserDao userDao;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userDao = new UserDao(this);

        // Inicjalizacja pól formularza
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);

        etStreet = findViewById(R.id.etStreet);
        etHouseNumber = findViewById(R.id.etHouseNumber);
        etFlatNumber = findViewById(R.id.etFlatNumber);
        etCity = findViewById(R.id.etCity);
        etPostalCode = findViewById(R.id.etPostalCode);
        etCountry = findViewById(R.id.etCountry);

        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        // Pobranie danych użytkownika z SQLite
        loadUserData();

        // Obsługa zapisywania zmian
        btnSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void loadUserData() {

        currentUser = userDao.getUser();

        if (currentUser == null) {
            Toast.makeText(
                    this,
                    "Nie znaleziono danych użytkownika",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        etFirstName.setText(currentUser.getFirstName());
        etLastName.setText(currentUser.getLastName());
        etEmail.setText(currentUser.getEmail());
        etPhone.setText(currentUser.getPhone());

        Address address = currentUser.getAddress();

        if (address != null) {
            etStreet.setText(address.getStreet());
            etHouseNumber.setText(address.getHouseNumber());
            etFlatNumber.setText(address.getFlatNumber());
            etCity.setText(address.getCity());
            etPostalCode.setText(address.getPostalCode());
            etCountry.setText(address.getCountry());
        }
    }

    private void saveProfile() {

        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        String street = etStreet.getText().toString().trim();
        String houseNumber = etHouseNumber.getText().toString().trim();
        String flatNumber = etFlatNumber.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String postalCode = etPostalCode.getText().toString().trim();
        String country = etCountry.getText().toString().trim();

        // Podstawowa walidacja
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            Toast.makeText(
                    this,
                    "Imię, nazwisko i e-mail są wymagane",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Podaj poprawny adres e-mail");
            etEmail.requestFocus();
            return;
        }

        // Tworzymy zaktualizowany adres
        Address updatedAddress = new Address(
                street,
                houseNumber,
                flatNumber,
                city,
                postalCode,
                country
        );

        // Aktualizujemy istniejącego użytkownika
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setAddress(updatedAddress);

        // Zapis do SQLite
        userDao.saveUser(currentUser);

        Toast.makeText(
                this,
                "Dane profilu zostały zapisane",
                Toast.LENGTH_SHORT
        ).show();

        finish();
    }
}
