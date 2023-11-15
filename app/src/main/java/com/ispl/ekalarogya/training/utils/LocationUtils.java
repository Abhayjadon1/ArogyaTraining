package com.ispl.ekalarogya.training.utils;

import static android.content.Context.LOCATION_SERVICE;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by sonu on 03/02/18.
 */

public class LocationUtils {
    private static final String TAG = LocationUtils.class.getSimpleName();

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        if (locationManager != null && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            Logger.ErrorLog(TAG, "GPS Provider Enabled");
            return true;
        }
        return false;
    }
}
