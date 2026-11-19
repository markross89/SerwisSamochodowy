
package com.example.serwissamochodowy.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Intent;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.serwissamochodowy.R;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchDarkMode;
    private SwitchCompat switchNotifications;
    private SwitchCompat switchExternalNotifications;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences("app_settings", MODE_PRIVATE);

        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchNotifications = findViewById(R.id.switchNotifications);
        switchExternalNotifications =
                findViewById(R.id.switchExternalNotifications);

        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        Button btnChangePassword = findViewById(R.id.btnChangePassword);
        Button btnDeleteAccount = findViewById(R.id.btnDeleteAccount);


        loadSettings();
        setupSwitches();

        btnChangePassword.setOnClickListener(v ->
                Toast.makeText(this,
                        "Zmiana hasła – przygotujemy ją w kolejnym kroku",
                        Toast.LENGTH_SHORT).show()
        );

        btnDeleteAccount.setOnClickListener(v ->
                showDeleteAccountDialog()
        );

        btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this,
                    ChangePasswordActivity.class);
            startActivity(intent);
        });

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });
    }

    private void loadSettings() {
        switchDarkMode.setChecked(
                preferences.getBoolean("dark_mode", false)
        );

        switchNotifications.setChecked(
                preferences.getBoolean("notifications", true)
        );

        switchExternalNotifications.setChecked(
                preferences.getBoolean("external_notifications", true)
        );

        switchExternalNotifications.setEnabled(
                switchNotifications.isChecked()
        );
    }

    private void setupSwitches() {

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {

            preferences.edit()
                    .putBoolean("dark_mode", isChecked)
                    .apply();

            AppCompatDelegate.setDefaultNightMode(
                    isChecked
                            ? AppCompatDelegate.MODE_NIGHT_YES
                            : AppCompatDelegate.MODE_NIGHT_NO
            );
        });

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit()
                    .putBoolean("notifications", isChecked)
                    .apply();

            switchExternalNotifications.setEnabled(isChecked);

            if (!isChecked) {
                switchExternalNotifications.setChecked(false);
            }

            Toast.makeText(this,
                    isChecked ? "Powiadomienia włączone" : "Powiadomienia wyłączone",
                    Toast.LENGTH_SHORT).show();
        });

        switchExternalNotifications.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    preferences.edit()
                            .putBoolean("external_notifications", isChecked)
                            .apply();

                    Toast.makeText(this,
                            isChecked
                                    ? "Powiadomienia zewnętrzne włączone"
                                    : "Powiadomienia zewnętrzne wyłączone",
                            Toast.LENGTH_SHORT).show();
                }
        );
    }

    private void showDeleteAccountDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Usuwanie konta")
                .setMessage("Czy na pewno chcesz usunąć konto?")
                .setNegativeButton("Anuluj", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Usuń", (dialog, which) -> {
                    Toast.makeText(this,
                            "Usuwanie konta wymaga jeszcze podłączenia bazy danych",
                            Toast.LENGTH_LONG).show();
                })
                .show();
    }
}
