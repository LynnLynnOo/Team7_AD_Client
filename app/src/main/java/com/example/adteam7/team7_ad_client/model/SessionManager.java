package com.example.adteam7.team7_ad_client.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.adteam7.team7_ad_client.Team7_AD_App;
import com.example.adteam7.team7_ad_client.activities.LoginActivity;

/**
 * Created by Kay Thi Swe Tun
 **/
public class SessionManager {

    private static final String KEY_NAME = "name";
    private static final String KEY_USERID = "id";
    private static final String KEY_PASSWORD = "pwd";
    private static final String KEY_TOKEN = "token";
    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String KEY_ROLE0 = "role0";
    private static final String KEY_ROLE1 = "role1";
    // Sharedpref file name
    private static final String PREF_NAME = "Team7ADPref";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    private static SessionManager sessionManager;

    public static SessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new SessionManager();
        }

        return sessionManager;
    }

    // Constructor
    private SessionManager() {
        int PRIVATE_MODE = 0;
        pref = PreferenceManager.getDefaultSharedPreferences
                (Team7_AD_App.getContext());
        editor = pref.edit();
        editor.apply();
    }

    public void createLoginSession(String name, String password,String id,String token) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_TOKEN,token);
        editor.commit();
    }

    public boolean checkLogin(Context context) {

        // Check login status
        boolean status = true;
        if (!this.isLoggedIn()) {

            // user is not logged in redirect him to LoginActivity Activity
            status = false;

            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring LoginActivity Activity
            context.startActivity(i);
        }
        return status;
    }

    /**
     * Quick check for login
     **/
    // Get LoginActivity State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);

    }

    public void setUserRole(String role0, String role1) {

        editor.putString(KEY_ROLE0, role0);

        if (!role1.equals(""))
            editor.putString(KEY_ROLE1, role1);

        editor.commit();

    }

    public String getUserRole0() {
        String s = pref.getString(KEY_ROLE0, null);
        return s;

    }

    public String getUserRole1() {

        String s = pref.getString(KEY_ROLE1, null);
        if (s != null) {
            return s;
        } else return "";


    }

    public String getUsername() {
        String s = pref.getString(KEY_NAME, null);
        Log.d("_context", "LOGIN NAME : " + s);
        return s;

    }
    public String getToken() {
        String s = pref.getString(KEY_TOKEN, null);
        Log.d("_context", "TOKEN: " + s);
        return s;

    }
    public String getUserid() {
        String s = pref.getString(KEY_USERID, null);
        Log.d("_context", "LOGIN ID : " + s);
        return s;

    }

    public void logoutUser(Context _context) {
        // Clearing all data from Shared Preferences
        String s = pref.getString(KEY_NAME, "");
        editor.clear();
        editor.putBoolean(IS_LOGIN, false);
        editor.putString(KEY_NAME, s);
        editor.commit();
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring LoginActivity Activity
        _context.startActivity(i);
    }
}
