package com.inventics.ekalarogya.training.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


/**
 * Created by sonu on 24/12/17.
 */

public class GoogleServiceUtils {
    /**
     * check play services
     *
     * @return
     */
    public static boolean checkPlayServices(Context context, int requestCode) {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int status = api.isGooglePlayServicesAvailable(context);
        if (status != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(status)) {
                api.getErrorDialog((AppCompatActivity) context, status,
                        requestCode).show();
            } else {
                ToastUtils.longToast("This device is not supported.");
            }
            return false;
        }
        return true;
    }

}
