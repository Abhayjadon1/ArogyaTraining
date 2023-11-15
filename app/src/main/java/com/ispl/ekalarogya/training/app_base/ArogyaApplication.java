package com.ispl.ekalarogya.training.app_base;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDexApplication;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.helper.LocaleManager;
import com.ispl.ekalarogya.training.helper.PreferenceManger;

import com.ispl.ekalarogya.training.fcm.NotificationUtils;
import com.ispl.ekalarogya.training.helper.CheckInternetConnection;

/**
 * Created by Sonu on 16/08/17.
 */

public class ArogyaApplication extends MultiDexApplication {

    private static ArogyaApplication mInstance;
    private static PreferenceManger preferenceManger;
    public static LocaleManager localeManager;


    public static synchronized ArogyaApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

    }
    @Override
    protected void attachBaseContext(Context base) {
        if (localeManager==null) {
            localeManager = new LocaleManager(base);
        }
        super.attachBaseContext(localeManager.setLocale(base));
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (localeManager!=null) {
            localeManager.setLocale(this);
        }
    }


    public boolean isInternetConnected(boolean showToast) {
        if (CheckInternetConnection.checkInternetConnection(this))
            return true;
        else {
            if (showToast)

                Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static PreferenceManger getPreferenceManager() {
        if (preferenceManger == null) {
            return new PreferenceManger();
        }
        return preferenceManger;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// The user-visible name of the channel.
        CharSequence name = "App Updates";
// The user-visible description of the channel.
        String description = "This channel is use to send you updates regarding this app.";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(NotificationUtils.UPDATES_CHANNEL_ID, name, importance);
// Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.enableLights(true);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        //mChannel.setShowBadge(false);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mNotificationManager.createNotificationChannel(mChannel);
    }


//    public static String getTranslate(Context mContaxt,String actualdata) {
//        String tranlate=null;
//
//        Resources res = mContaxt.getResources();
//        Configuration conf = res.getConfiguration();
//        Locale savedLocale = conf.locale;
//        conf.locale = new Locale("en"); // whatever you want here
//        res.updateConfiguration(conf, null);
////        int id = mContaxt.getResources().getIdentifier("Family List", "strings", mContaxt.getPackageName());
////        Log.d("TAG", "getTranslate: "+actualdata+"  "+id);
//////
////        if (actualdata.equalsIgnoreCase(res.getString(R.string.village_list))){
////            // trans(R.string.dash1);
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.village_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.family_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.family_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.medicine_garden))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.medicine_garden);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.family_children_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.family_children_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.personal_hygeine_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.personal_hygeine_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.social_sanitation_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.social_sanitation_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.personal_hygiene_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.personal_hygiene_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.animea_checkup_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.animea_checkup_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.disease_prevention_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.disease_prevention_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.malnutrition_checkup_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.malnutrition_checkup_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.ayurvedic_treatment_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.ayurvedic_treatment_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.samiti_meeting_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.samiti_meeting_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.search_coordinator))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.search_coordinator);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.village_visit_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.village_visit_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.arogya_camp_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.arogya_camp_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.referred))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.referred);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.inspection_stay_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.inspection_stay_list);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.title))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.title);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.update_owner))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.update_owner);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.add_owner_details))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.add_owner_details);
////        }else if (actualdata.equalsIgnoreCase(res.getString(R.string.children_list))){
////            conf.locale = savedLocale;
////            res.updateConfiguration(conf, null);
////            tranlate=mContaxt.getResources().getString(R.string.children_list);
////        }
//
//
//        conf.locale = savedLocale;
//        res.updateConfiguration(conf, null);
//        SharedPreferences sharedPreferences = mContaxt.getSharedPreferences(Constant.LOGIN_SHARED, Context.MODE_PRIVATE);
//        conf.locale = new Locale(sharedPreferences.getString(LOCALE, LocaleManager.DEFAULT_LOCALE));
//        res.updateConfiguration(conf, null);
//
//        Log.d("TAG", "getTranslated: "+tranlate);
//
//        return tranlate;
//    }

    public static String getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return String.valueOf(context.getResources().getConfiguration().getLocales().get(0));
        } else{
            //noinspection deprecation
            return String.valueOf(context.getResources().getConfiguration().locale);
        }
    }


}
