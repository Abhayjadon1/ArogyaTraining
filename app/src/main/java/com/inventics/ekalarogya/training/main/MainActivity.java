package com.inventics.ekalarogya.training.main;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.inventics.ekalarogya.training.services.LocationSyncService.FASTEST_INTERVAL;
import static com.inventics.ekalarogya.training.services.LocationSyncService.INTERVAL;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.inventics.ekalarogya.training.BuildConfig;;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_base.BaseActivity;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.dialogs.DialogHelperClass;
import com.inventics.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.inventics.ekalarogya.training.dialogs.StartEndTripDialogFragment;
import com.inventics.ekalarogya.training.helper.MarshMallowPermission;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.language_change.LanguageChooserActivity;
import com.inventics.ekalarogya.training.main.dashboardFragments.HomeFragment;
import com.inventics.ekalarogya.training.main.dashboardFragments.ProfileFrag;
import com.inventics.ekalarogya.training.main.dashboardFragments.TaskActivity;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.response.UserManager;
import com.inventics.ekalarogya.training.rest.service.DriverService;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.services.BackgroundDetectedActivitiesService;
import com.inventics.ekalarogya.training.services.LocationSyncService;
import com.inventics.ekalarogya.training.utils.DeviceUtils;
import com.inventics.ekalarogya.training.utils.FragmentUtils;
import com.inventics.ekalarogya.training.utils.GPSTracker;
import com.inventics.ekalarogya.training.utils.GoogleServiceUtils;
import com.inventics.ekalarogya.training.utils.ImageUtils;
import com.inventics.ekalarogya.training.utils.Logger;
import com.inventics.ekalarogya.training.utils.PermissionUtils;
import com.inventics.ekalarogya.training.utils.ToastUtils;
import com.inventics.ekalarogya.training.user_auth.LoginActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements RetroAPICallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        StartEndTripDialogFragment.OnTripListener,
        View.OnClickListener{

    File photoFile = null;

    public static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int LOCATION_PERMISSION_CODE = 101;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int NEW_REQUEST_CODE = 22;
    private static final int CHECK_ONLINE_REQUEST_CODE = 223;
    private static final int CHECK_ATTENDANCE_REQUEST_CODE = 224;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private static final int LOGOUT_REQUEST_CODE = 136;
    private static final int PIC_ID_MEMBER_DIALOG = 121;
    private static final int PIC_ID_HEAD_FAMILY = 111;
    double longitude,latitude;
    Bitmap photoHead,photoMember;
    String photoHeadString,photoMemberString;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
//    @BindView(R.id.btnNavigation)
//    BottomNavigationView btnNavigation;
    boolean doubleBackToExitPressedOnce = false;

    private Uri cameraFileURI;
    private String imagePath = "";
    Bitmap bitmap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private boolean isLogoutCall;
    @BindView(R.id.common_toolbar)
    Toolbar common_toolbar;

    @BindView(R.id.online_offline_mode_switch)
    SwitchCompat onlineOfflineSwitch;
    private static final int REQUEST_PERMISSIONS = 1;

    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    public static void openMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((AppCompatActivity) context).finish();
    }
    private ReviewManager reviewManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configFireBaseRemote();
        common_toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(common_toolbar);


        loadFragment(HomeFragment.newInstance(), FragmentUtils.HOME_FRAGMENT, "");

        PermissionUtils.checkAndRequestPermissions(this);
        checkForAppUpdate();
        checkOnlineStatus();


        onlineOfflineSwitchMode();
    }

    private void online() {
        DriverService.onlineCheck(this,photoHeadString,String.valueOf(latitude),String.valueOf(longitude),this,CHECK_ATTENDANCE_REQUEST_CODE);
    }

    private void offline() {
        DriverService.offlineCheck(this,photoHeadString,String.valueOf(latitude),String.valueOf(longitude),this,CHECK_ATTENDANCE_REQUEST_CODE);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.bottom_nav_menu, menu);
        menu.findItem(R.id.action_app_version).setTitle("App Version : "+ BuildConfig.VERSION_NAME).setEnabled(false);
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                loadFragment(HomeFragment.newInstance(), FragmentUtils.HOME_FRAGMENT, "");
                return true;
            case R.id.profile:
                loadFragment(ProfileFrag.newInstance(), FragmentUtils.PROFILE_FRAGMENT, "");
                return (true);
            case R.id.language_choose_button:
                startActivity(new Intent(MainActivity.this, LanguageChooserActivity.class));
                return (true);
            case R.id.navigation_gkv:
                startActivity(new Intent(MainActivity.this, GKVReport.class));
                return (true);

            case R.id.logout:
                ArogyaApplication.getPreferenceManager().remove("user_signin");
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                return (true);
            case R.id.task_list:
                startActivity(new Intent(MainActivity.this, TaskActivity.class));
                return (true);

            case R.id.action_app_version:
                ToastUtils.shortToast(getResources().getString(R.string.app_version)+ BuildConfig.VERSION_NAME+"\n"+"Version Code : "+BuildConfig.VERSION_CODE);
                return (true);
//            case R.id.onlineSwitch:
//                ProfileFrag.openProfile(this);
//                return (true);
        }
        return (super.onOptionsItemSelected(item));

    }

    public void showRateApp() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
                // show native rate app dialog on error
                showRateAppFallbackDialog();
            }
        });
    }
    /**
     * Showing native dialog with three buttons to review the app
     * Redirect user to playstore to review the app
     */
    private void showRateAppFallbackDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.rate_app_title)
                .setMessage(R.string.rate_app_message)
                .setPositiveButton(R.string.rate_btn_pos, (dialog, which) -> {

                })
                .setNegativeButton(R.string.rate_btn_neg,
                        (dialog, which) -> {
                        })
                .setNeutralButton(R.string.rate_btn_nut,
                        (dialog, which) -> {
                        })
                .setOnDismissListener(dialog -> {
                })
                .show();
    }


    private void loadFragment(Fragment fragment, String tag, @NonNull String title) {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.main_frame_container, fragment, tag, false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = this.getMenuInflater();
//        inflater.inflate(R.menu.bottom_nav_menu, menu);
//
//        MenuItem home_menu, profile_menu, version_menu;
//
//        home_menu = menu.findItem(R.id.home);
//        profile_menu = menu.findItem(R.id.profile);
//        version_menu = menu.findItem(R.id.version);
//     return true;
//    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }



    private void doBackPress() {
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(FragmentUtils.HOME_FRAGMENT);
        if (homeFragment == null) {
            loadFragment(HomeFragment.newInstance(), FragmentUtils.HOME_FRAGMENT, "Schedule Wash");
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode) {
            case LOGOUT_REQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        ToastUtils.longToast(baseResponse.getStatusMessage());
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            ArogyaApplication.getPreferenceManager().logoutUser();
                            LoginActivity.openLoginActivity(MainActivity.this);
                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                        }
                    } else {
                        ToastUtils.longToast(getResources().getString(R.string.opps_try_again));
                    }
                } else {
                    ToastUtils.longToast(getResources().getString(R.string.opps_try_again));
                }
                break;
            case CHECK_ONLINE_REQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (RestUtils.isUserSessionActive(MainActivity.this, baseResponse) && baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
//                            try {
                            Log.d(TAG, "onResponse: 0"+baseResponse.getProfileModelDetails().toString());
                            if (baseResponse.getProfileModelDetails().getOnline() != null && baseResponse.getProfileModelDetails().getOnline().equalsIgnoreCase("yes")){
                                onlineOfflineSwitch.setChecked(true);
                            }else{
                                onlineOfflineSwitch.setChecked(false);
                            }

//                            }catch (Exception e){
//                                Log.d(TAG, "onResponse: ttry "+e.getMessage());
//                            }
                            updateSwitchUI();
                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                        }
                    }
                }
                break;
            case CHECK_ATTENDANCE_REQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            if(baseResponse.getStatusMessage().equalsIgnoreCase("Start Day")){
                                onlineOfflineSwitch.setChecked(true);
                                ArogyaApplication.getPreferenceManager().putBoolean(PreferenceManger.ONLINE_MODE, true);
                            }
                            if(baseResponse.getStatusMessage().equalsIgnoreCase("End Day")){
                                onlineOfflineSwitch.setChecked(false);

                                ArogyaApplication.getPreferenceManager().putBoolean(PreferenceManger.ONLINE_MODE, false);
                            }
                        } else {
                            Toast.makeText(this, "" + baseResponse.getStatusMessage(), Toast.LENGTH_SHORT).show();
                            onlineOfflineSwitch.setChecked(false);
                            ArogyaApplication.getPreferenceManager().putBoolean(PreferenceManger.ONLINE_MODE, false);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        switch (requestCode) {
            case LOGOUT_REQUEST_CODE:
                Logger.ErrorLog(TAG, "Logout failed : " + t.getLocalizedMessage());
                ToastUtils.longToast(getResources().getString(R.string.opps_try_again));
                break;
        }
    }

    @Override
    public void onNoNetwork(int requestCode) {
        switch (requestCode) {
            case LOGOUT_REQUEST_CODE:
                break;
        }
    }

    private void updateSwitchUI() {
        if (onlineOfflineSwitch.isChecked()) {
            ArogyaApplication.getPreferenceManager().putBoolean(PreferenceManger.ONLINE_MODE, true);
        } else {
//            onlineOfflineSwitch.setText(getResources().getString(R.string.absent));
            ArogyaApplication.getPreferenceManager().putBoolean(PreferenceManger.ONLINE_MODE, false);
        }
    }

    private void toggleOfflineOnlineSwitch() {
        onlineOfflineSwitch.setChecked(!onlineOfflineSwitch.isChecked());
        updateSwitchUI();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    int counter = 0;
                    for (int result : grantResults) {
                        if (result != 0) {
                            DialogHelperClass.showMessageOKCancel(this, getResources().getString(R.string.storage_permission_required), getResources().getString(android.R.string.ok), getResources().getString(android.R.string.cancel), (dialogInterface, i) -> checkStoragePermission(), (dialogInterface, i) -> {
                                ToastUtils.shortToast(getResources().getString(R.string.storage_permission_deny));
                                toggleOfflineOnlineSwitch();
                            });
                            return;
                        }

                        counter++;
                        if (counter == permissions.length) {
                            captureImageFormCamera();
                        }
                    }
                }
                break;

            default:
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragmentList) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
                break;

        }

    }

    /*  ==================      FIREBASE REMOTE CONFIG       ==================  */
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    /**
     * config firebase
     */
    private void configFireBaseRemote() {
        // Get Remote Config instance.
        // [START get_remote_config_instance]
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // [END get_remote_config_instance]

        // Create a Remote Config Setting to enable developer mode, which you can use to increase
        // the number of fetches available per hour during development. See Best Practices in the
        // README for more information.
        // [START enable_dev_mode]

       /* FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);*/


        // [END enable_dev_mode]

        // [START set_default_values]
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        // [END set_default_values]

        fetchRemoteConfig();


    }

    /**
     * fetches latest remote config
     */
    private void fetchRemoteConfig() {

        long cacheExpiration = 6 * 3600; // 6 hour in seconds.
        // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
        // will use fetch data from the Remote Config service, rather than cached parameter values,
        // if cached parameter values are more than cacheExpiration seconds old.
        // See Best Practices in the README for more information.
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {

                        }
                    }
                });
        // [END fetch_config_with_callback]
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onTripSuccess() {

    }

    @Override
    public void onTripCancelled() {

    }

//    @Override
//    public void onFragmentChange(Fragment fragment, String tag, String title) {
//        loadFragment(fragment, tag, title);
//    }

    public interface OnReferralBackPressListener {
        public boolean onBackPressed();
    }

    @Override
    protected void onDestroy() {
        unregisterInstallStateUpdListener();
        super.onDestroy();
    }

    private void checkForAppUpdate() {
        Log.d(TAG, "checkForAppUpdate: ");
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
// Create a listener to track request state updates.
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState installState) {
                // Show module progress, log state, or install the update.
                if (installState.installStatus() == InstallStatus.DOWNLOADED)
                    // After the update is downloaded, show a notification
                    // and request user confirmation to restart the app.
                    popupSnackbarForCompleteUpdateAndUnregister();
            }
        };
// Checks that the platform will allow the specified type of update.
//        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                    // This example applies an immediate update. To apply a flexible update
//                    // instead, pass in AppUpdateType.FLEXIBLE
//                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
//                Log.d(TAG, "checkForAppUpdate: iff");
//                // Request the update.
//            }
//        });
//        // Returns an intent object that you use to check for an update.
//        com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
//
//

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                    // Before starting an update, register a listener for updates.
                    appUpdateManager.registerListener(installStateUpdatedListener);
                    // Start an update.
                    startAppUpdateFlexible(appUpdateInfo);
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) ) {
                    // Start an update.
                    startAppUpdateImmediate(appUpdateInfo);
                }

            } else{
                // Toast.makeText(this, "Not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // The current activity making the update request.
                    this,
                    // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                    // flexible updates.
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
                            .setAllowAssetPackDeletion(true)
                            .build(),
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void startAppUpdateFlexible(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // The current activity making the update request.
                    this,
                    // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                    // flexible updates.
                    AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE)
                            .setAllowAssetPackDeletion(true)
                            .build(),
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }

    /**
     * Displays the snackbar notification and call to action.
     * Needed only for Flexible app update
     */
    private void popupSnackbarForCompleteUpdateAndUnregister() {
        Snackbar snackbar =
                Snackbar.make(findViewById(R.id.main_frame_container), "An update has just been downloaded.", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Restart", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();

        unregisterInstallStateUpdListener();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        checkNewAppVersionState();
//    }

    /**
     * Checks that the update is not stalled during 'onResume()'.
     * However, you should execute this check at all app entry points.
     */
    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            //FLEXIBLE:
                            // If the update is downloaded but not installed,
                            // notify the user to complete the update.
                            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                popupSnackbarForCompleteUpdateAndUnregister();
                            }
                            //IMMEDIATE:
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                startAppUpdateImmediate(appUpdateInfo);
                            }
                        });


    }

    /**
     * Needed only for FLEXIBLE update
     */
    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }
    private void onlineOfflineSwitchMode() {
//        onlineOfflineSwitch.setChecked(ArogyaApplication.getPreferenceManager().getBooleanValue(PreferenceManger.ONLINE_MODE));

        onlineOfflineSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSwitchUI();
                dexterPerm(0);
                checkOnlineStatus();
            }
        });

        onlineOfflineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // buttonView.setBackgroundColor(getResources().getColor(R.color.green));
                    onlineOfflineSwitch.setHighlightColor(getResources().getColor(R.color.indigo));

                } else {
                    //buttonView.setBackgroundColor(getResources().getColor(R.color.red));
                    onlineOfflineSwitch.setHighlightColor(getResources().getColor(R.color.red));
                    //BottomColorChange.changeSwitchColor(onlineOfflineSwitch,R.color.red);
                }
            }
        });
    }
    private void openCameraToClick(int pic_id) {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (camera_intent.resolveActivity(getPackageManager()) != null) {
        // Create the File where the photo should go
        try {

            photoFile = createImageFile();
//                displayMessage(getBaseContext(),photoFile.getAbsolutePath());
//                Log.i("Tag",photoFile.getAbsolutePath());

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.inventics.ekalarogya.training.file_provider",
                        photoFile);
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                camera_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                camera_intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(camera_intent, pic_id);
            }
        } catch (Exception ex) {
            // Error occurred while creating the File
            ToastUtils.shortToast(ex.getMessage());
        }


//        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoHeadString = image.getAbsolutePath();
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                    myBitmap= ImageUtils.getResizedBitmap(myBitmap,480);
                    imagePath=ImageUtils.convertBitmapIntoBase64(myBitmap);
                    Logger.DebugLog(TAG, "image_data_from_camera " + imagePath+"\n"+myBitmap);
                    if (imagePath!=null) {
                        isLogoutCall = false;
                        fetchLastKnownLocation();
                    } else {
                        ToastUtils.longToast("Oops!! Some error occurred. Please try again.");
                        toggleOfflineOnlineSwitch();
                    }
                } else {
                    ToastUtils.longToast("Failed to capture image.");
                    toggleOfflineOnlineSwitch();
                }
                break;
            case NEW_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
//                    refreshTaskFragment();
                }
                break;
            case PIC_ID_MEMBER_DIALOG:
                if(resultCode == Activity.RESULT_OK)  {
                    photoMember = (Bitmap) data.getExtras().get("data");
                    photoMemberString = ImageUtils.convertBitmapIntoBase64(photoMember);
                    Log.d("TAG", "onActivityResult:photoMemberString " + photoMemberString);
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                }
                // click_image_id.setImageBitmap(photo);
                break;
            case PIC_ID_HEAD_FAMILY:
                if(resultCode == Activity.RESULT_OK)  {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                    myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                    photoHeadString=ImageUtils.convertBitmapIntoBase64(myBitmap);
                    Log.d("TAG", "onActivityResult:photoHeadString " + photoHeadString);
                Log.d(TAG, "onActivityResult: latitude "+String.valueOf(latitude)+ "  "+String.valueOf(longitude));
                if (!onlineOfflineSwitch.isChecked()){
                    Log.d(TAG, "onCheckedChanged: isChecked");

                    online();
                }else{
                    Log.d(TAG, "onCheckedChanged: noChecked");

                    offline();
                }
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                }
//                iv_attach_adhar.setImageBitmap(photoHead);
                // click_image_id.setImageBitmap(photo);
                break;
            default:
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragmentList) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
                break;
        }

    }
    /**
     * method to fetch last known location
     **/

    private void fetchLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            checkLocationPermission();
        else {
            //If granted then get last know location and update the location label if last know location is not null
            Logger.DebugLog(TAG, "onConnected get last know location");
            Location mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            double lat = 0, lng = 0;
            float speed = 0, accuracy = 0;
            if (mLastKnownLocation != null) {
                Logger.DebugLog(TAG, "onConnected get last know location not null");
                //send location to server
                lat = mLastKnownLocation.getLatitude();
                lng = mLastKnownLocation.getLongitude();
                speed = mLastKnownLocation.getSpeed();
                accuracy = mLastKnownLocation.getAccuracy();
                Log.d(TAG, "fetchLastKnownLocation: " + mLastKnownLocation.getLatitude() + "," + mLastKnownLocation.getLongitude());
            } else {
                Logger.DebugLog(TAG, "onConnected get last know location null");
                if (!isLogoutCall) {
                    toggleOfflineOnlineSwitch();
                    ToastUtils.longToast("Failed to fetch location. Please try again.");
                }
            }

            if (isLogoutCall) {
                double batteryLevel = DeviceUtils.getBatteryLevel(this);
                String motion = ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.MOTION);
                if (!TextUtils.isEmpty(motion))
                    motion = "Y";
                int signalStrength = ArogyaApplication.getPreferenceManager().getIntegerValue(PreferenceManger.SIGNAL_STRENGTH);

                UserService.doLogout(MainActivity.this, lat, lng, speed, accuracy, batteryLevel, motion, signalStrength, new RetroAPICallback() {
                    @Override
                    public void onResponse(Call<?> call, Response<?> response, int requestCode, @Nullable Object request) {

                        if (response.isSuccessful()) {

                            BaseResponse baseResponse = (BaseResponse) response.body();
                            if (baseResponse != null) {
                                if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {

                                    stopActivityTracking();

                                    UserManager userManager = ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS);
                                    System.gc();
                                    //ToastUtils.shortToast("Logout Success done.");
                                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    ArogyaApplication.getPreferenceManager().logoutUser();
                                    startActivity(i);
                                    finish();
                                } else {
                                    ToastUtils.shortToast(baseResponse.getStatusMessage());
                                }
                            } else {
                                ToastUtils.shortToast(getResources().getString(R.string.try_again));
                            }
                        } else {
                            ToastUtils.shortToast(getResources().getString(R.string.try_again));
                        }
                        ProgressDialogFragment.dismissProgress(getSupportFragmentManager());
                    }

                    @Override
                    public void onFailure(Call<?> call, Throwable t, int requestCode, @Nullable Object request) {
                        ToastUtils.shortToast(getResources().getString(R.string.try_again));
                    }

                    @Override
                    public void onNoNetwork(int requestCode) {
                    }
                }, 0);
            } else {
                if (lat != 0 && lng != 0) {
                    showTripDialog(lat, lng);
                } else {
                    toggleOfflineOnlineSwitch();
                    ToastUtils.longToast("Failed to fetch location. Please try again.");
                }
            }
        }
    }

    /**
     * method to check location permission
     */
    private void checkLocationPermission() {
        if (MarshMallowPermission.checkMashMallowPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE)) {
            //Permission Granted
            Logger.DebugLog(TAG, "checkLocationPermission Granted");
            triggerPlayServiceCheck();
        }
    }

    /**
     * check if play services are enabled or not
     */
    private void triggerPlayServiceCheck() {
        if (GoogleServiceUtils.checkPlayServices(MainActivity.this, MapFragment.REQUEST_CODE_RECOVER_PLAY_SERVICES)) {
            GoogleApiAvailability api = GoogleApiAvailability.getInstance();
            int status = api.isGooglePlayServicesAvailable(this);
            if (status == ConnectionResult.SUCCESS) {

                showSettingDialog();

            } else if (status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                DialogHelperClass.showMessageOKCancel(this, "Please Update Your Google Play Service to Continue", "Update Play Service", "Cancel",
                        (dialogInterface, i) -> {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms&hl=en"));
                            startActivity(browserIntent);
                        },
                        (dialogInterface, i) -> {

                        });
            } else if (status == ConnectionResult.CANCELED) {

            }
        }
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

    /**
     * show GPS Allow dialog if GPS is off
     */
    private void showSettingDialog() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            final LocationSettingsStates state = result1.getLocationSettingsStates();
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
                        status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
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
        });
    }

    /**
     * initiate google api client
     */
    private void initGoogleAPIClient() {
        //Without Google API Client Auto Location Dialog will not work
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            if (!mGoogleApiClient.isConnected())
                mGoogleApiClient.connect();
        }
        createLocationRequest();
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.MyAlertDialogStyle))



                .setTitle(getResources().getString(R.string.close))
                .setMessage(getResources().getString(R.string.logout_mmsg))

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        doubleBackToExitPressedOnce = false;
                        finish();
                        System.exit(0);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.mipmap.ic_launcher)
                .show();


    }
    @Override
    public void onResume() {
        super.onResume();
        //On Resume check if google api is not null and is connected so that location update should start again
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            Logger.DebugLog(TAG, "onResume mGoogleApiClient is NOT null");
            initGoogleAPIClient();
        }
        checkNewAppVersionState();
        checkLocationPermission();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleNotificationClick();
    }

    private void handleNotificationClick() {
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra(ExtraUtils.PUSH_TYPE)) {
//            String notificationType = intent.getStringExtra(ExtraUtils.PUSH_TYPE);
//            switch (notificationType) {
//                case FCMUtils.PUSH_TYPE_CHAT_NOTIFICATION:
//                    try {
//                        Chat chat = intent.getParcelableExtra(ExtraUtils.CHAT_MODEL);
//                        if (chat != null) {
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//
//                case FCMUtils.APPROVAL_PUSH_TYPE:
//                    // NewRequestActivity.openNewRequestActivity(this, NEW_REQUEST_CODE);
//                    break;
//            }
//        }
    }

    private void startActivityTracking() {
        Intent intent1 = new Intent(MainActivity.this, BackgroundDetectedActivitiesService.class);
        startService(intent1);
    }

    private void stopActivityTracking() {
        Intent intent = new Intent(MainActivity.this, BackgroundDetectedActivitiesService.class);
        stopService(intent);
    }

    private void checkStoragePermission() {
        if (MarshMallowPermission.checkMashMallowPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA}, STORAGE_PERMISSION_REQUEST_CODE)) {
            captureImageFormCamera();
        }
    }

    private void checkOnlineStatus() {
        UserService.getProfile(MainActivity.this, this, CHECK_ONLINE_REQUEST_CODE);
    }

    private void checkAttendance() {
        DriverService.checkAttendance(MainActivity.this, this, CHECK_ATTENDANCE_REQUEST_CODE);
    }

    private void captureImageFormCamera() {
        if (!DeviceUtils.isDeviceSupportCamera(this)) {
            toggleOfflineOnlineSwitch();
            return;
        }

    /*    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File createImageFile = FileUtils.createImageFile(this);
        cameraFileURI = FileProvider.getUriForFile(this, BuildConfig.FILES_AUTHORITY, createImageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileURI);
        imagePath = createImageFile.getPath();
        for (ResolveInfo resolveInfo : getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)) {
            grantUriPermission(resolveInfo.activityInfo.packageName, cameraFileURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
        intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
        intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } else {

          try {
              startActivityForResult(intent, CAMERA_REQUEST_CODE);
          }catch (Exception e)
          {
              ToastUtils.longToast("No app to capture image.");
              toggleOfflineOnlineSwitch();
          }
        }
*/

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
//        intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
//        intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
        try {
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }

    }
    private void resetImageParams() {
        imagePath = "";
        cameraFileURI = null;
    }

    private void showTripDialog(double lat, double lng) {
        Logger.DebugLog(TAG, "IS CHECKED : " + onlineOfflineSwitch.isChecked());

        Log.d(TAG, "showTripDialog: " + cameraFileURI +" " +bitmap+ " " + imagePath + " " + lat + " " + lng);
        StartEndTripDialogFragment.showTripDialogFragment(getSupportFragmentManager(), bitmap, imagePath, onlineOfflineSwitch.isChecked(), lat, lng);
    }

//    @Override
//    public void onTripSuccess() {
//
//        resetImageParams(); //image variables null...
//        updateSwitchUI();
//
//        int userType = ArogyaApplication.getPreferenceManager().getIntegerValue(PreferenceManger.USER_TYPE_LOGIN_PREF);
//
//        if (userType == UserManager.FE_USER) {
//            //refreshTaskFragment();
//            refeshTaskFragment();
//            //  showHideViewForUsers();
//        } else {
//            Log.d(TAG, "onTripSuccess: FeUser");
//        }
//    }
    public  void checkOnline() {
        DriverService.checkOnline(MainActivity.this, this, CHECK_ONLINE_REQUEST_CODE);
    }
    private void getLatitude() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();

        } else {
            getLocation();
        }


    }
    private void OnGPS() {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//            ToastUtils.shortToast("Permission Done");
        } else {
//            ToastUtils.shortToast("Permission else");

            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = lat;
                longitude = longi;
//                ToastUtils.shortToast("Permission else if");
                Log.d("Your Location: " ,"\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                GPSTracker gpsTracker=new GPSTracker(this);
                latitude=gpsTracker.getLatitude();
                longitude=gpsTracker.getLongitude();
                Log.d("Your Location: " ,"\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
//                ToastUtils.shortToast("Permission else else");
//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void dexterPerm(int PERM){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION
//                        ,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            if(PERM==0){
                                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                    OnGPS();

                                } else {
//                        ToastUtils.shortToast("Please Grant Location Permission");
                                    getLocation();
                                }

                                if (String.valueOf(longitude)!=null&& !(String.valueOf(longitude).equalsIgnoreCase("0.0"))&& !(String.valueOf(longitude).equalsIgnoreCase("null"))){

                                }else{

                                    Log.d(TAG, "onClick: location is null");
                                }
                                dexterPerm(PIC_ID_HEAD_FAMILY);
                            }else{
                                Log.d(TAG, "onClick: openCameraToClick(PERM)");
                                openCameraToClick(PERM);
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


}
