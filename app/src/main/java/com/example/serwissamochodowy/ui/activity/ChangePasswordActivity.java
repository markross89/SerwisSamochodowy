
package com.example.serwissamochodowy.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serwissamochodowy.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etCurrentPassword;
    private EditText etNewPassword;
    private EditText etConfirmNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);

        Button btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(v -> changePassword());
    }

    private void changePassword() {

        String currentPassword = etCurrentPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmNewPassword.getText().toString().trim();

        if (currentPassword.isEmpty() || newPassword.isEmpty()
                || confirmPassword.isEmpty()) {
            Toast.makeText(this,
                    "Uzupełnij wszystkie pola",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 8) {
            etNewPassword.setError("Hasło musi mieć minimum 8 znaków");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            etConfirmNewPassword.setError("Hasła nie są identyczne");
            return;
        }

        Toast.makeText(this,
                "Formularz poprawny. Weryfikacja aktualnego hasła wymaga podłączenia bazy danych.",
                Toast.LENGTH_LONG).show();
    }
}
