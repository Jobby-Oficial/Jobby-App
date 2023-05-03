/*
 * Created by Guilherme Cruz
 * Last modified: 13/01/22, 17:22
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USERNAME = "username";

    public SessionManager(Context _context) {
        context = _context;
        userSession = _context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String username) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        return userData;
    }

    public boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, false))
            return true;
        else
            return false;
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }
}
