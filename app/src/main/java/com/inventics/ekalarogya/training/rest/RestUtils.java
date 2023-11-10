package com.inventics.ekalarogya.training.rest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.inventics.ekalarogya.training.BuildConfig;;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.dialogs.DialogHelperClass;
import com.inventics.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.response.UserManager;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.services.BackgroundDetectedActivitiesService;
import com.inventics.ekalarogya.training.utils.ToastUtils;
import com.inventics.ekalarogya.training.user_auth.LoginActivity;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by sonu on 08/01/18.
 */

public class RestUtils {
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";

    public static final String ARRIVED = "arrived";
    public static final String START_TRIP = "startTrip";
    public static final String STARTED = "STARTED";

    public static final String ENDED = "ended";
    public static final String COMPLETED = "completed";
    public static final String CANCELLED = "cancelled";
    public static final String COLLECT = "collect";

    public static String getEndPoint() {
        if (BuildConfig.DEBUG) {
            return BuildConfig.PROD_END_POINT;
        } else {
            return BuildConfig.PROD_END_POINT;
        }
    }

    public static String getImageBaseUrl() {
        return BuildConfig.PROD_END_POINT;

        // + "images/product/"
    }
    public static String getEkalImageBaseUrl() {
        return BuildConfig.PROD_END_POINT;

    }


    public static boolean isUserSessionActive(Context context, @NonNull BaseResponse response) {

        if (response.getStatus().equalsIgnoreCase(FAILED) && response.getErrorCode().equalsIgnoreCase("4444")) {
            DialogHelperClass.showMessageOKCancel(context, response.getStatusMessage(), "Ok", null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    UserService.doLogout(context, 0, 0, 0, 0, 0, "", 0, new RetroAPICallback() {
                        @Override
                        public void onResponse(Call<?> call, Response<?> response, int requestCode, @Nullable Object request) {

                            logoutSuccess(context);
                            ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
                        }

                        @Override
                        public void onFailure(Call<?> call, Throwable t, int requestCode, @Nullable Object request) {
                            logoutSuccess(context);
                        }

                        @Override
                        public void onNoNetwork(int requestCode) {
                            logoutSuccess(context);
                        }
                    }, 0);
                }
            }, null);
            return false;
        }

        return true;
    }

    private static void logoutSuccess(Context context) {
        Intent intent = new Intent(context, BackgroundDetectedActivitiesService.class);
        context.stopService(intent);

        UserManager userManager = ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS);
        if (userManager != null ) {
           /* for (String hubId : userManager.getHub_id()) {
                FCMUtils.doSubscriptionTopic("pickdel_" + hubId, false);
            }*/
        }
        ArogyaApplication.getPreferenceManager().logoutUser();
        ToastUtils.shortToast(context.getResources().getString(R.string.login_success));
        context.startActivity(new Intent(context, LoginActivity.class));
        ((AppCompatActivity) context).finish();
    }

    public static String getDirectionsAPIKey() {
        return BuildConfig.GOOGLE_MAPS_API_KEY_1.replace(":", "") + BuildConfig.GOOGLE_MAPS_API_KEY_2.replace(":", "");
    }
    public static String getFileProviderPath(){
        return BuildConfig.APPLICATION_ID + ".file_provider";
    }

}


