/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SettingsManager {

    SharedPreferences appSettings;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_NIGHT_MODE = "IsNightMode";

    public static final String DAYNIGHT = "day_night";

    public SettingsManager(Context _context) {
        context = _context;
        appSettings = _context.getSharedPreferences("appUserSettings", Context.MODE_PRIVATE);
        editor = appSettings.edit();
    }

    public void createLoginSession(boolean switchOnOff_DayNight) {
        editor.putBoolean(IS_NIGHT_MODE, true);
        editor.putBoolean(DAYNIGHT, switchOnOff_DayNight);
        editor.commit();
    }

    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(DAYNIGHT, appSettings.getString(DAYNIGHT, null));
        return userData;
    }

    public boolean checkLogin() {
        if (appSettings.getBoolean(IS_NIGHT_MODE, false))
            return true;
        else
            return false;
    }
}
