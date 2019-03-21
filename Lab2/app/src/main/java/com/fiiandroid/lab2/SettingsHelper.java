package com.fiiandroid.lab2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsHelper {
    private static boolean isDark;

    public static void changeToTheme(Activity activity)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        isDark = prefs.getBoolean("theme", false);
        if (isDark) {
            activity.setTheme(R.style.LightTheme);
        }
        else {
            activity.setTheme(R.style.DarkTheme);
        }
    }

    public static void onActivityCreateSetTheme(Activity activity)
    {
       if (isDark){
           activity.setTheme(R.style.DarkTheme);
       }
       else{
           activity.setTheme(R.style.LightTheme);
       }

    }
}

