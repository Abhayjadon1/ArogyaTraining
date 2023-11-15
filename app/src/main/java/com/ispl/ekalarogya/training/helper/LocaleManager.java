package com.ispl.ekalarogya.training.helper;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import java.util.Locale;

/**
 * Created by sonu on 19:02, 2020-01-22
 * Copyright (c) 2020 . All rights reserved.
 */
public class LocaleManager {
    public static final String LOCALE = "locale";
    public static final String DEFAULT_LOCALE = "en";
    public static final String HINDI_LOCALE = "hi";
    public static final String KANNADA_LOCALE = "kn";
    public static final String TAMIL_LOCALE = "ta";
    public static final String TELUGU_LOCALE = "te";
    public static final String GUJARATI_LOCALE = "gu";
    public static final String MALAYALAM_LOCALE = "ml";
    public static final String BANGLA_LOCALE = "bn";
    public static final String ORIYA_LOCALE = "or";
    public static final String URDU_LOCALE = "ur";
    public static final String MARATHI_LOCALE = "mr";
    public static final String PANJABHI_LOCALE = "pa";
    public static final String ASSAMESE_LOCALE = "as";
    SharedPreferences sharedPreferences;
    public LocaleManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Constant.LOGIN_SHARED, Context.MODE_PRIVATE);
    }

    public Context setLocale(Context context) {
        return setNewLocale(context, getLanguage());
    }

    public Context setNewLocale(Context context, String language) {
        persistLanguage(language);
        Log.d(TAG, "setNewLocale: "+language);
        return updateResources(context, language);
    }

    public String getLanguage() {
        return sharedPreferences.getString(LOCALE, DEFAULT_LOCALE);
    }

    @SuppressLint("ApplySharedPref")
    private void persistLanguage(String language) {
        // use commit() instead of apply(), because sometimes we kill the application process
        // immediately that prevents apply() from finishing
        sharedPreferences.edit().putString(LOCALE, language).commit();
    }

    private Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Log.d(TAG, "updateResources: "+locale);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
        return context;
    }
}
