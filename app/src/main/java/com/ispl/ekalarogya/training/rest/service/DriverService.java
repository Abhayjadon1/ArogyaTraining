package com.ispl.ekalarogya.training.rest.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.ispl.ekalarogya.training.helper.PreferenceManger;
import com.ispl.ekalarogya.training.rest.ApiInterface;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.utils.ExtraUtils;
import com.ispl.ekalarogya.training.utils.ImageUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sonu on 07/01/18.
 */

public final class DriverService {

    private static final String TAG = "DriverService";
    private static final ApiInterface apiService = BaseService.getAPIClient().create(ApiInterface.class);

    public DriverService(Context context) {
    }

    public static void checkOnline(final Context context, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(false)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        int userType = ArogyaApplication.getPreferenceManager().getIntegerValue(PreferenceManger.USER_TYPE_LOGIN_PREF);
        Call<BaseResponse> call = apiService.checkOnline(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID),
                ArogyaApplication.getPreferenceManager().getAuthCode());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }
        });
    }
    public static void goOffline(final Context context, String meterReading, String image, double latitude, double longitude, final RetroAPICallback retroAPICallback, final int requestCode) {
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }

        String user_type = ArogyaApplication.getPreferenceManager().getStringValue( ExtraUtils.USER_TYPE);

        Call<BaseResponse> call;
        if (user_type != null) {
            call = apiService.goOffline(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID),
                    ArogyaApplication.getPreferenceManager().getAuthCode(), ArogyaApplication.getPreferenceManager().getUserId(),
                    image, meterReading, latitude, longitude);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                    retroAPICallback.onResponse(call, response, requestCode, null);
                    ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
                }

                @Override
                public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                    retroAPICallback.onFailure(call, t, requestCode, null);
                    ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
                }
            });
        }

    }
    public static void checkAttendance(final Context context, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(false)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        int userType = ArogyaApplication.getPreferenceManager().getIntegerValue(PreferenceManger.USER_TYPE_LOGIN_PREF);
        Call<BaseResponse> call = apiService.checkAttendance(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }
        });
    }

    public static void onlineCheck(final Context context,String punch_in_image, String latitude,String longitude, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(false)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        int userType = ArogyaApplication.getPreferenceManager().getIntegerValue(PreferenceManger.USER_TYPE_LOGIN_PREF);
        Call<BaseResponse> call = apiService.online(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),punch_in_image,latitude,longitude);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }
        });
    }

    public static void offlineCheck(final Context context,String punch_in_image, String latitude,String longitude, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(false)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        int userType = ArogyaApplication.getPreferenceManager().getIntegerValue(PreferenceManger.USER_TYPE_LOGIN_PREF);
        Call<BaseResponse> call = apiService.offline(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),punch_in_image,latitude,longitude);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }
        });
    }


    public static void idUploadImageStartOnline(final Context context, String image, final RetroAPICallback retroAPICallback, final int requestCode) {
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        String base64Image = ImageUtils.resizeBase64Image(image);

        Call<BaseResponse> call;
        Log.d(TAG, "goOnline: Simple" + base64Image);

        call = apiService.goUploadImage(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID),
                ArogyaApplication.getPreferenceManager().getAuthCode(),
                base64Image);


        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }
        });
    }

    public static void goOnline(final Context context, String meterReading, String image, double latitude, double longitude, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        String user_type = ArogyaApplication.getPreferenceManager().getStringValue(ExtraUtils.USER_TYPE);
        Call<BaseResponse> call;
        if (user_type != null) {
            call = apiService.goOnline(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID),
                    ArogyaApplication.getPreferenceManager().getAuthCode(),
                    ArogyaApplication.getPreferenceManager().getUserId(),
                    image, meterReading, latitude, longitude);

            Log.v("online ", ArogyaApplication.getPreferenceManager().getAuthCode() + "\n" + ArogyaApplication.getPreferenceManager().getUserId() + "\n" + latitude);

            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                    retroAPICallback.onResponse(call, response, requestCode, null);
                    ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
                }

                @Override
                public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                    retroAPICallback.onFailure(call, t, requestCode, null);
                    ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
                }
            });
        }
    }

    public static void sendLocationUpdate(final Context context, double lat, double lng, double speed, double accuracy, double battery, String motion, int signalStrength, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(false)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }

        Call<BaseResponse> call = apiService.sendLocationUpdate(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getUserId(), lat, lng, ArogyaApplication.getPreferenceManager().getAuthCode(), speed, accuracy, battery, motion, signalStrength);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
            }
        });
    }


    public static void idUploadImageEndOnline(final Context context, String image, final RetroAPICallback retroAPICallback, final int requestCode) {
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        String base64Image = ImageUtils.resizeBase64Image(image);

        Call<BaseResponse> call;
        Log.d(TAG, "goOnline: Simple" + base64Image);

        call = apiService.goUploadImage(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID),
                ArogyaApplication.getPreferenceManager().getAuthCode(),
                base64Image);


        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }
        });
    }


}

