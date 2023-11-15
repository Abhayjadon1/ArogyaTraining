package com.ispl.ekalarogya.training.services;

import static android.content.Context.LOCATION_SERVICE;

import android.content.Context;
import android.location.LocationManager;

import com.ispl.ekalarogya.training.utils.LocationUtils;
import com.ispl.ekalarogya.training.utils.Logger;

public class LoactionFetch {
    private static final String TAG = LocationUtils.class.getSimpleName();

    public static boolean getLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        if (locationManager != null && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {

            Logger.ErrorLog(TAG, "GPS Provider Enabled");
            return true;
        }
        return false;
    }
}
