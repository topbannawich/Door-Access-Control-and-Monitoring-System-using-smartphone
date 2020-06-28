package com.example.passagon.final402;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Created by delaroy on 3/26/18.
 */

public class PreferenceUtils {

    public PreferenceUtils(){

    }

    public static boolean saveUsername(String email, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_USERNAME, email);
        prefsEditor.apply();
        return true;
    }

    public static String getUsername(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_USERNAME, null);
    }

    public static boolean savePassword(String password, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PASSWORD, password);
        prefsEditor.apply();
        return true;
    }

    public static String getPassword(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PASSWORD, null);
    }
    public static boolean saveType(String type, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_TYPE, type);
        prefsEditor.apply();
        return true;
    }
    public static String getType(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_TYPE, null);
    }
    public static boolean savepasscode(String code, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PASSCODE, code);
        prefsEditor.apply();
        return true;
    }
    public static String getPasscode(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PASSCODE, null);
    }
}