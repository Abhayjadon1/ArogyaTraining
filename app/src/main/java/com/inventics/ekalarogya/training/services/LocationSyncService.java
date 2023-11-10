package com.inventics.ekalarogya.training.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.inventics.ekalarogya.training.BuildConfig;;
import com.inventics.ekalarogya.training.fcm.NotificationUtils;
import com.inventics.ekalarogya.training.utils.LocationUtils;
import com.inventics.ekalarogya.training.utils.Logger;

/**
 * Created by sonu on 03/02/18.
 */

public class LocationSyncService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private static final String TAG = LocationSyncService.class.getSimpleName();
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    public static final long INTERVAL = 1000 * 10;//10 sec
    public static final long FASTEST_INTERVAL = 1000 * 5;//5 sec

    public static final int SMALL_DISPLACEMENT = BuildConfig.DEBUG ? 500 : 100;// in meter

    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Logger.DebugLog(TAG, "Location service started");
        checkLocationPermission();
        startForeground(NotificationUtils.FOREGROUND_LOCATION_NOTIFICATION_ID, NotificationUtils.createForegroundNotification(this));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(this, new Intent(this, HideNotificationService.class));
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Logger.ErrorLog(TAG, "Runtime location permission is not granted..");
        } else {
            if (LocationUtils.isLocationEnabled(this)) {
                buildGoogleApiClient();
            } else {
                Logger.ErrorLog(TAG, "GPS location is off.");
            }
        }
    }

    private synchronized void buildGoogleApiClient() {
        if (context == null)
            return;
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();

    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(SMALL_DISPLACEMENT);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (context == null)
            return;
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
    }

    private void stopLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Logger.DebugLog(TAG, "Google API Client connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Logger.DebugLog(TAG, "Google API Client connection failed.");

    }

    @Override
    public void onLocationChanged(Location location) {
        Logger.DebugLog(TAG, "OnLocationChanged : " + location.getLatitude() + " - " + location.getLongitude());
        MyIntentService.sendLocationService(context, location);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.DebugLog(TAG, "Location service stopped");
        stopForeground(true);
        stopLocationUpdates();
    }

    public static class HideNotificationService extends Service {
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            startForeground(NotificationUtils.FOREGROUND_LOCATION_NOTIFICATION_ID, NotificationUtils.createForegroundNotification(this));
            stopForeground(true);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            stopSelfResult(startId);
            return START_NOT_STICKY;
        }
    }

}
