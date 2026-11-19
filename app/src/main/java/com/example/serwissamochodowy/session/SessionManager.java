
package com.example.serwissamochodowy.session;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SessionManager {

    private static final String PREF_NAME = "secure_car_service_prefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";

    private SharedPreferences encryptedPrefs;

    @SuppressWarnings("deprecation")
    public SessionManager(Context context) {

        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            encryptedPrefs = EncryptedSharedPreferences.create(
                    context,
                    PREF_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalStateException(
                    "Nie udało się zainicjalizować bezpiecznej sesji użytkownika.",
                    e
            );
        }
    }

    // Zapis tokena po udanym logowaniu
    public void saveAuthToken(String token) {
        encryptedPrefs.edit()
                .putString(KEY_AUTH_TOKEN, token)
                .apply();
    }

    // Pobieranie tokena sesji
    public String getAuthToken() {
        return encryptedPrefs.getString(KEY_AUTH_TOKEN, null);
    }

    // Wylogowanie
    public void clearSession() {
        encryptedPrefs.edit()
                .remove(KEY_AUTH_TOKEN)
                .apply();
    }
}
