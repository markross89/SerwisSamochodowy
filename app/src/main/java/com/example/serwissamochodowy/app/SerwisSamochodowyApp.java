
package com.example.serwissamochodowy.app;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class SerwisSamochodowyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        boolean darkMode = getSharedPreferences(
                "app_settings",
                MODE_PRIVATE
        ).getBoolean("dark_mode", false);

        AppCompatDelegate.setDefaultNightMode(
                darkMode
                        ? AppCompatDelegate.MODE_NIGHT_YES
                        : AppCompatDelegate.MODE_NIGHT_NO
        );
    }
}
