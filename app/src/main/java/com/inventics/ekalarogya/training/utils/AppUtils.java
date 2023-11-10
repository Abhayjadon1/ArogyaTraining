package com.inventics.ekalarogya.training.utils;

import static android.content.Context.LOCATION_SERVICE;

import android.content.Context;
import android.location.LocationManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by sonu on 20:42, 2019-06-13
 * Copyright (c) 2019 . All rights reserved.
 */
public class AppUtils {

    public static String returnStatus(int statusId) {
        switch (statusId) {
            case 1:
                return "Pending";
            case 2:
                return "Assigned";
            case 3:
                return "Confirmed";
            case 4:
                return "Pick Up Ready";
            case 5:
                return "Order Received";
            case 6:
                return "Order Created";
            case 7:
                return "Cancelled";
            case 8:
                return "Order Delivered";
            case 9:
                return "Pick Up Rescheduled";
            case 10:
                return "Pick Up Failed";
            default:
                return "";

        }
    }

    public static String orderStatus(int statusId) {
        switch (statusId) {
            case 1:
                return "Taken";
            case 2:
                return "Outlet Dispatched";
            case 3:
                return "Factory Received";
            case 4:
                return "Under Process";
            case 5:
                return "Processed";
            case 6:
                return "Factory Dispatched";
            case 7:
                return "Outlet Received";
            case 8:
                return "Delivered";
            case 9:
                return "Cancelled";
            case 10:
                return "Outlet Received";
            case 11:
                return "Out for Delivery";
            case 12:
                return "Delivery Failed";
            case 13:
                return "Delivery Rescheduled";
            case 14:
                return "Partially Delivered";
            case 15:
                return "Delivery Scheduled";
            default:
                return "";
        }
    }

    /**
     * check play services
     *
     * @return
     */


    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return locationManager != null && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

}
