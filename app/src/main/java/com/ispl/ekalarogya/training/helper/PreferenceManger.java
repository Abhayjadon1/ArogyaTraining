package com.ispl.ekalarogya.training.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.main.MainActivity;
import com.ispl.ekalarogya.training.rest.response.UserManager;


public class PreferenceManger {

    /**
     * preference keys
     */
//    public static final String COLLAB_USER_PREFS = "collab_user_prefs";

    public static final String USER_PREFS = "user_prefs";
    public static final String LOGIN_DETAILS = "user_signin";
    public static final String FCM_TOKEN = "fcm_token";
    public static final String ONLINE_MODE = "online_mode";
    public static final String PHONE_NUMBER = "mobile_no";
    public static final String NAME = "user_name";
    public static final String VILL_ID = "village_id";
    public static final String LOCALE = "locale";

    public static final String CONNECTION_ID = "c_id";

    public static final String SIGNAL_STRENGTH = "signal_strength";
    public static final String APP_VERSION = "app_version";
    public static final String MOTION = "motion";
    public static final String START_TRIP_METER_READING = "start_trip_meter_reading";

    public static final String USER_TYPE_LOGIN_PREF = "collab_user_type_login_pref";

    /**
     * preference saving keys
     */


    private static SharedPreferences preferences;

    public PreferenceManger() {
    }

    private static synchronized SharedPreferences getInstance() {
        if (preferences == null) {
            preferences = ArogyaApplication.getInstance().getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    private SharedPreferences.Editor getEditor() {
        return getInstance().edit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(key);
        editor.apply();
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.apply();
    }


    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        return getInstance().getString(key, "");
    }

    public boolean getBooleanValue(String key) {
        return getInstance().getBoolean(key, false);
    }

    public int getIntegerValue(String key) {
        return getInstance().getInt(key, 0);
    }


    public void logoutUser() {
        saveUserManager(null);
        putBoolean(ONLINE_MODE, false);
    }

    public void saveUserManager(UserManager userManager) {
        Gson gson = new Gson();
        String json = gson.toJson(userManager);
        Log.d("TAG", "saveUserManager: "+json);
        putString(LOGIN_DETAILS, json);
    }

    public UserManager getUserManager(String loginDetails) {
        Gson gson = new Gson();
        String json = getStringValue(LOGIN_DETAILS);
        return gson.fromJson(json, UserManager.class);
    }

    public String getUserId() {
        UserManager userManager = getUserManager(PreferenceManger.LOGIN_DETAILS);
        if (userManager != null)
            return userManager.getUserId();
        return "";
    }

    public String getAuthCode() {
        UserManager userManager = getUserManager(PreferenceManger.LOGIN_DETAILS);
        if (userManager != null)
            return userManager.getAuthCode();
        return "";
    }
//    public void putString(String key, String value) {
//        SharedPreferences.Editor editor = getEditor();
//        editor.putString(key, value);
//        editor.apply();
//    }


    public static void onLoginRegisterSuccess(Context context, UserManager userManager) {
        ArogyaApplication.getPreferenceManager().saveUserManager(userManager);
        MainActivity.openMainActivity(context);
    }


}
