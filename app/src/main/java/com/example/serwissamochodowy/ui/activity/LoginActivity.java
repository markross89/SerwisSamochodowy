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

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;

    private SessionManager sessionManager;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Sprawdzenie na starcie: jeśli użytkownik jest już zalogowany, pomijamy logowanie
        sessionManager = new SessionManager(this);
        if (sessionManager.getAuthToken() != null) {
            navigateToMain();
            return;
        }

        setContentView(R.layout.activity_login);

        userDao = new UserDao(this);

        // Inicjalizacja widoków
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        // 2. Obsługa przycisku logowania
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                return;
            }

            // Symulacja poprawnego uwierzytelniania
            handleSuccessfulLogin(email);
        });

        // 3. Przejście do ekranu rejestracji po kliknięciu linku
        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void handleSuccessfulLogin(String email) {
        // A. Zapisujemy bezpieczny token sesji
        String fakeToken = "bearer_token_xyz_12345";
        sessionManager.saveAuthToken(fakeToken);

        // B. Zapisujemy/aktualizujemy przykładowe dane użytkownika w SQLite
        Address address = new Address("Prosta", "15", "4", "Warszawa", "00-838", "Polska");
        User user = new User(1, "Jan", "Kowalski", email, "123456789", address);
        userDao.saveUser(user);

        Toast.makeText(this, "Zalogowano pomyślnie!", Toast.LENGTH_SHORT).show();

        // C. Przechodzimy do MainActivity
        navigateToMain();
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        // Czyszczenie stosu, żeby przycisk "Wstecz" nie wracał do logowania
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}