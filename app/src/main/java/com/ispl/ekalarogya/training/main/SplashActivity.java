package com.ispl.ekalarogya.training.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ispl.ekalarogya.training.BuildConfig;;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.app_base.BaseActivity;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.helper.DialogHelper;
import com.ispl.ekalarogya.training.helper.PreferenceManger;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.AppVersionResponse;
import com.ispl.ekalarogya.training.rest.response.GetConnection;
import com.ispl.ekalarogya.training.rest.service.UserService;
import com.ispl.ekalarogya.training.services.BgServ;
import com.ispl.ekalarogya.training.utils.IntentUtils;
import com.ispl.ekalarogya.training.utils.Logger;
import com.ispl.ekalarogya.training.utils.ToastUtils;
import com.ispl.ekalarogya.training.user_auth.LoginActivity;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Sonu on 14/08/17.
 */

public class SplashActivity extends BaseActivity implements RetroAPICallback {

    private static final long SPLASH_DELAY = 2000;
    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final int GET_CONNECTION_REQUEST_CODE = 1;
    private static final int CHECK_APP_UPDATE_REQUEST_CODE = 2;



    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getConnection();
        startService(new Intent( SplashActivity.this, BgServ.class ) );

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    private void startDelayHandler() {
        handler = new Handler();
        handler.postDelayed(runnable, SPLASH_DELAY);
    }

    Runnable runnable = () -> {
        Log.d(TAG, "runnnnble: ");
//        if(ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOCALE) == null){
//            Intent intent = new Intent(SplashActivity.this, LanguageChooserActivity.class);
//            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();

//        }else{
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS) != null) {
                MainActivity.openMainActivity(SplashActivity.this);
            } else {
                LoginActivity.openLoginActivity(SplashActivity.this);
            }
            finish();
//        }


    };

    private void getConnection() {
        if (!TextUtils.isEmpty(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID))) {
            checkAppVersion();
        } else {
            UserService.getConnection(this, this, GET_CONNECTION_REQUEST_CODE);
        }
    }

    private void checkAppVersion() {
        UserService.checkAppVersion(this, this, CHECK_APP_UPDATE_REQUEST_CODE);
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, @Nullable Object request) {
        switch (requestCode) {
            case GET_CONNECTION_REQUEST_CODE:
                Log.d(TAG, "onResponse: GET_CONNECTION_REQUEST_CODE");
                if (response.isSuccessful()) {

                    GetConnection getConnection = (GetConnection) response.body();
                    if (getConnection != null) {
                        if (getConnection.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            if (!TextUtils.isEmpty(getConnection.getConnectionId())) {
                                Log.d(TAG, "onResponse: GET_CONNECTION_ "+getConnection.getConnectionId());
                                ArogyaApplication.getPreferenceManager().putString(PreferenceManger.CONNECTION_ID, getConnection.getConnectionId());
                                checkAppVersion();
                                startDelayHandler();
                            } else {
                                ToastUtils.longToast("Oops!! Server error occurred. Please try again.");
                                finish();
                                Logger.ErrorLog(TAG, "Empty connection ID.");
                            }
                        } else {
                            Logger.ErrorLog(TAG, "Get connection failed : Response is not success.");
                            ToastUtils.longToast("Oops!! Server error occurred. Please try again.");
                            finish();
                        }
                    } else {
                        ToastUtils.longToast("Oops!! Server error occurred. Please try again.");
                        finish();
                        Logger.ErrorLog(TAG, "Get connection response is null.");
                    }
                } else {
                    ToastUtils.longToast("Oops!! Server error occurred. Please try again.");
                    finish();
                    Logger.ErrorLog(TAG, "Get Connection response is not successful.");
                }
                break;

            case CHECK_APP_UPDATE_REQUEST_CODE:
                if (response.isSuccessful()) {
                    AppVersionResponse appVersionResponse = (AppVersionResponse) response.body();
                    if (appVersionResponse != null) {
                        if (appVersionResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            if (appVersionResponse.getUpdateRequired() == 1) {

                                if (appVersionResponse.getForceUpdate() == 1) {
                                    showAppUpgradeAlertDialog(true);
                                } else {
                                    showAppUpgradeAlertDialog(false);
                                }
                            } else {
                                startDelayHandler();
                            }
                        } else {
                            startDelayHandler();
                        }
                    } else {
                        startDelayHandler();
                    }
                } else {
                    startDelayHandler();
                }
                break;
        }
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, @Nullable Object request) {
        switch (requestCode) {
            case GET_CONNECTION_REQUEST_CODE:
                ToastUtils.longToast("Oops!! Server error occurred. Please try again.");
                finish();
                Logger.ErrorLog(TAG, "CONNECTION ERROR : " + t.getLocalizedMessage());
                break;
            case CHECK_APP_UPDATE_REQUEST_CODE:
                Logger.ErrorLog(TAG, "UPDATE APP FAILED : " + t.getLocalizedMessage());
                startDelayHandler();
                break;
        }
    }

    @Override
    public void onNoNetwork(int requestCode) {
        switch (requestCode) {
            case GET_CONNECTION_REQUEST_CODE:
                ToastUtils.longToast(R.string.no_internet_connection);
                finish();
                break;
            case CHECK_APP_UPDATE_REQUEST_CODE:
                startDelayHandler();
                break;
        }
    }


    /*  Make the app full screen with transparent top bar */
    private void transparentToolbar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(AppCompatActivity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        super.onDestroy();
    }

    private void showAppUpgradeAlertDialog(boolean isForceUpgrade) {
        String negativeButton = "";
        if (!isForceUpgrade) {
            negativeButton = "Continue";
        }

        DialogHelper.showMessageOKCancel(this, "New version is available in play store. Please upgrade to latest version.", "Upgrade", negativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                IntentUtils.openPlayStore(SplashActivity.this, BuildConfig.APPLICATION_ID,true);

            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startDelayHandler();
            }
        });
    }


}
