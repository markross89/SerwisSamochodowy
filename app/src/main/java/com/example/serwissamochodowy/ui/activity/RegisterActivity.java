package com.example.serwissamochodowy.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.serwissamochodowy.model.Address;
import com.example.serwissamochodowy.R;
import com.example.serwissamochodowy.session.SessionManager;
import com.example.serwissamochodowy.model.User;
import com.example.serwissamochodowy.dao.UserDao;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPhone, etPassword;
    private EditText etStreet, etHouseNumber, etFlatNumber, etCity, etPostalCode, etCountry;
    private Button btnRegister;
    private TextView tvGoToLogin;

    private SessionManager sessionManager;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionManager = new SessionManager(this);
        userDao = new UserDao(this);

        // Inicjalizacja pól widoku
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);

        // Pola adresu
        etStreet = findViewById(R.id.etStreet);
        etHouseNumber = findViewById(R.id.etHouseNumber);
        etFlatNumber = findViewById(R.id.etFlatNumber);
        etCity = findViewById(R.id.etCity);
        etPostalCode = findViewById(R.id.etPostalCode);
        etCountry = findViewById(R.id.etCountry);

        btnRegister = findViewById(R.id.btnRegister);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);

        // Obsługa kliknięcia przycisku rejestracji
        btnRegister.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            String street = etStreet.getText().toString().trim();
            String houseNumber = etHouseNumber.getText().toString().trim();
            String flatNumber = etFlatNumber.getText().toString().trim();
            String city = etCity.getText().toString().trim();
            String postalCode = etPostalCode.getText().toString().trim();
            String country = etCountry.getText().toString().trim();

            // Walidacja wymaganych pól
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || city.isEmpty()) {
                Toast.makeText(this, "Wypełnij wymagane pola (Imię, Nazwisko, E-mail, Hasło, Miasto)", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- Symulacja wysłania danych do API (np. Retrofit POST /register) ---
            // Po udanej rejestracji serwer zwraca token i dane użytkownika:
            registerUserSuccess(firstName, lastName, email, phone, street, houseNumber, flatNumber, city, postalCode, country);
        });

        // Powrót do ekranu logowania
        tvGoToLogin.setOnClickListener(v -> {
            finish(); // Zamykamy rejestrację i wracamy do logowania
        });
    }

    private void registerUserSuccess(String firstName, String lastName, String email, String phone,
                                     String street, String houseNumber, String flatNumber, String city, String postalCode, String country) {

        // 1. Zapisujemy token sesji w bezpiecznym schowku
        String serverToken = "bearer_token_registered_xyz";
        sessionManager.saveAuthToken(serverToken);

        // 2. Tworzymy obiekt Address uwzględniający nowe pola
        Address address = new Address(street, houseNumber, flatNumber, city, postalCode, country);

        // 3. Tworzymy obiekt User i zapisujemy go w lokalnej bazie SQLite przez UserDao
        User newUser = new User(1, firstName, lastName, email, phone, address);
        userDao.saveUser(newUser);

        Toast.makeText(this, "Konto zostało pomyślnie utworzone!", Toast.LENGTH_SHORT).show();

        // 4. Przechodzimy do MainActivity i czyszczymy stos aktywności
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}