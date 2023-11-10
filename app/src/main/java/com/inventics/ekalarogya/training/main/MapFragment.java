package com.inventics.ekalarogya.training.main;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.inventics.ekalarogya.training.services.LocationSyncService.FASTEST_INTERVAL;
import static com.inventics.ekalarogya.training.services.LocationSyncService.INTERVAL;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_base.BaseFragment;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.dialogs.DialogHelperClass;
import com.inventics.ekalarogya.training.helper.MarshMallowPermission;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.services.LocationSyncService;
import com.inventics.ekalarogya.training.services.MyIntentService;
import com.inventics.ekalarogya.training.utils.GoogleServiceUtils;
import com.inventics.ekalarogya.training.utils.Logger;
import com.inventics.ekalarogya.training.utils.ToastUtils;

/**
 * Created by sonu on 13:38, 29/08/18
 * Copyright (c) 2018 . All rights reserved.
 */
public class MapFragment extends BaseFragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {


    public static final int LOCATION_PERMISSION_CODE = 443;
    /**
     * map related variables
     */
    public static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    public static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final String TAG = MapFragment.class.getSimpleName();
    private static final float MAP_ZOOM = 12.0f;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap googleMap;
    private Marker mPickUpMarker, mDropOffMarker, mDriverMarker;
    private Location mLastKnownLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng mCurrentLatLng;

    public static MapFragment newInstance() {

        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.map_fragment;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpMapIfNeeded();
    }

    /**
     * check if play services are enabled or not
     */
    private void triggerPlayServiceCheck() {
        if (GoogleServiceUtils.checkPlayServices(getContext(), REQUEST_CODE_RECOVER_PLAY_SERVICES)) {
            GoogleApiAvailability api = GoogleApiAvailability.getInstance();
            int status = api.isGooglePlayServicesAvailable(getContext());
            if (status == ConnectionResult.SUCCESS) {

                showSettingDialog();

            } else if (status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                DialogHelperClass.showMessageOKCancel(getContext(), "Please Update Your Google Play Service to Continue", "Update Play Service", "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms&hl=en"));
                                startActivity(browserIntent);
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
            } else if (status == ConnectionResult.CANCELED) {

            }
        }
    }

    /**
     * show GPS Allow dialog if GPS is off
     */
    private void showSettingDialog() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        //getOriginLatLng();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }


    /*****
     * Set up the map if it is needed
     * before loading map again first check if map is null or not if it null then load map else don't do anything
     *****/
    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            SupportMapFragment supportMapFragment = ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map_view));
            if (supportMapFragment != null)
                supportMapFragment.getMapAsync(this);
        }
    }

    /**
     * Map Delegate Methods starts
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
//On API Client Connection check if location permission is granted or not
        fetchLastKnownLocation(false);

        //Start Location Update on successful connection
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            this.mLastKnownLocation = location;
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), MAP_ZOOM));
            onLocationFetched();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        //enable and disable the google map UI Settings
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setCompassEnabled(false);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        //Implement click events over google map
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMarkerClickListener(this);
    }

    /**
     * method to check location permission
     */
    private void checkRequiredAppPermissions() {
        if (MarshMallowPermission.checkMashMallowPermissions((AppCompatActivity) getActivity(), new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE)) {
            //Permission Granted
            Logger.DebugLog(TAG, "checkRequiredAppPermissions Granted");
            triggerPlayServiceCheck();
        }
    }

    /**
     * method to fetch last known location
     *
     * @param isCurrentLocation check if call is from current location, if it is current location call then update marker
     */
    private void fetchLastKnownLocation(boolean isCurrentLocation) {
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            checkRequiredAppPermissions();
        else {
            if (googleMap != null)
                googleMap.setMyLocationEnabled(true);

            //If granted then get last know location and update the location label if last know location is not null
            Logger.DebugLog(TAG, "onConnected get last know location");
            mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastKnownLocation != null && isCurrentLocation) {
                Logger.DebugLog(TAG, "onConnected get last know location not null");
                onLocationFetched();
            } else
                Logger.DebugLog(TAG, "onConnected get last know location null");
        }

    }

    /**
     * on location fetched successfully
     */
    private void onLocationFetched() {
        LatLng currentLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
        ///  if (mCurrentLatLng!=null)
        //animateMarker(mCurrentLatLng,false);
        mCurrentLatLng = currentLatLng;
        //updateMapMarker(currentLatLng);
        //  fetchLocationAddress();

        //send location to server
        MyIntentService.sendLocationService(getContext(), mLastKnownLocation);

    }


    /**
     * start location updates
     */
    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            checkRequiredAppPermissions();
        else
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
    }

    /**
     * stop location updates
     */
    protected void stopLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startLocationBGService(false);

        //On Resume check if google api is not null and is connected so that location update should start again
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Logger.DebugLog(TAG, "onResume mGoogleApiClient is NOT null");
            startLocationUpdates();
        } else {
            //else if not connected or null initiate api client
            initGoogleAPIClient();
            Logger.DebugLog(TAG, "onResume mGoogleApiClient is null");

        }

        //If location is not null set the last know location
        if (mLastKnownLocation != null) {
            Logger.DebugLog(TAG, "onResume mLastKnownLocation is NOT null");
            LatLng currentLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
            mCurrentLatLng = currentLatLng;
        }
        checkRequiredAppPermissions();
    }

    /**
     * initiate google api client
     */
    private void initGoogleAPIClient() {
        //Without Google API Client Auto Location Dialog will not work
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            if (!mGoogleApiClient.isConnected())
                mGoogleApiClient.connect();
        }
        createLocationRequest();
    }

    /**
     * create location request
     * here we have to declare what should be out Location Priority
     * and what should be the interval delay for location update
     */
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        /*mLocationRequest.setInterval(30 * 1000);//after 1min the location will update
        mLocationRequest.setFastestInterval(5 * 1000);*/
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        mLocationRequest.setSmallestDisplacement(LocationSyncService.SMALL_DISPLACEMENT);//100 meters
    }


    @Override
    public void onPause() {
        super.onPause();
        //stop location update when fragment is not visible
        stopLocationUpdates();
        if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS) != null)
            startLocationBGService(true);
    }

    private void startLocationBGService(boolean isPause) {
        Intent intent = new Intent(getContext(), LocationSyncService.class);
        if (isPause) {
            getContext().startService(intent);
        } else {
            getContext().stopService(intent);
        }
    }

    /**
     * Map Delegate method ends
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    int counter = 0;
                    for (int result : grantResults) {
                        if (result != 0) {
                            DialogHelperClass.showMessageOKCancel(getContext(), getContext().getResources().getString(R.string.all_permission_required), getResources().getString(android.R.string.ok), getResources().getString(android.R.string.cancel), (dialogInterface, i) -> onPermissionDenied(), (dialogInterface, i) -> ToastUtils.longToast("You can't use app without this permission."));
                            break;
                        }
                        counter++;
                        if (counter == permissions.length) {
                            onPermissionGranted();
                        }
                    }
                    break;
                }
                break;
        }
    }

    /**
     * if permission for location denied
     */
    public void onPermissionDenied() {
        Logger.DebugLog(TAG, "onPermissionDenied");

    }

    /**
     * when permission granted for Location
     */
    public void onPermissionGranted() {
        if (mGoogleApiClient == null) {
            Logger.DebugLog(TAG, "onPermissionGranted : mGoogleApiClient is null");
            initGoogleAPIClient();
            showSettingDialog();
        } else {
            Logger.DebugLog(TAG, "onPermissionGranted : mGoogleApiClient is not null");
            showSettingDialog();
        }

    }
}
