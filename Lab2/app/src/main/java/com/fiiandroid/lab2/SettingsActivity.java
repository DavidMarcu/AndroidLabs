package com.fiiandroid.lab2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class SettingsActivity extends FragmentActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("theme")){
            boolean isDark = sharedPreferences.getBoolean("theme", false);
            sharedPreferences.edit().putBoolean("theme", !isDark).apply();
            Log.d("THEME", sharedPreferences.getBoolean("theme", false) ? "true" : "false");
        }
    }
}
