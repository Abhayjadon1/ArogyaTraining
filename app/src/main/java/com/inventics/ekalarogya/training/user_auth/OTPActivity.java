package com.inventics.ekalarogya.training.user_auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.goodiebag.pinview.Pinview;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_base.BaseActivity;
import com.inventics.ekalarogya.training.app_interfaces.OnTimerChangeListener;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.inventics.ekalarogya.training.helper.MyTimerTask;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.response.UserManagerResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.Logger;
import com.inventics.ekalarogya.training.utils.TextUtils;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.Timer;

import butterknife.BindString;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by sonu on 20:10, 28/10/18
 * Copyright (c) 2018 . All rights reserved.
 */
public class OTPActivity extends BaseActivity implements RetroAPICallback {

    private static final String TAG = OTPActivity.class.getSimpleName();

    private static final String EXTRA_CALL_TYPE = "call_type";
    private static final String EXTRA_NAME = "user_name";
    private static final String EXTRA_MOBILE_NUMBER = "mobile_number";
    private static final String EXTRA_EMAIL_ID = "email_id";
    private static final String EXTRA_MESSAGE = "message";
    private static final String EXTRA_PASSWORD = "password";
    private static final String EXTRA_REFER_CODE = "refer_code";

    private static final int OTP_LOGIN_REQUEST_CODE = 1;
//    private static final int REGISTRATION_REQUEST_CODE = 2;
    private static final int RESEND_LOGIN_OTP_REQUEST_CODE = 3;
//    private static final int RESEND_REGISTER_OTP_REQUEST_CODE = 4;
//private Pinview otpView;
    public static void openOTPActivity(Context context, int callType, String message,String mobileNumber) {
        Intent intent = new Intent(context, OTPActivity.class);
        intent.putExtra(EXTRA_CALL_TYPE, callType);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_MOBILE_NUMBER, mobileNumber);
        context.startActivity(intent);
    }

    public static void openOTPActivity(Context context, int callType,String message, String name, String mobileNumber, String emailId,String referCode) {
        Intent intent = new Intent(context, OTPActivity.class);
        intent.putExtra(EXTRA_CALL_TYPE, callType);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_MOBILE_NUMBER, mobileNumber);
        intent.putExtra(EXTRA_EMAIL_ID, emailId);
        intent.putExtra(EXTRA_REFER_CODE,referCode);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_otp;
    }

    @BindView(R.id.et_otp)
    Pinview enterOTP;
    @BindView(R.id.btnnext)
    Button btnnext;
    @BindView(R.id.resendotp)
    TextView resendotp;

    @BindString(R.string.didn_t_received_otp)
    String resendString;
    @BindString(R.string.resend_in)
    String resendInString;
//    @BindString(R.string.otp_received_action)
//    String otpReceivedAction;
//    @BindString(R.string.verify_number_message_label)
//    String verifyNumberMessageLabel;
//    @BindString(R.string.verify_email_message_label)
//    String verifyEmailMessageLabel;

    private Timer timer;
    private int callType;
    private String mobileNumber, userName, userEmail,referCode,userMessage;
    private String strOtp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        startTimer();

        enterOTP.setTextColor(this.getResources().getColor(R.color.black));
        enterOTP.setBackgroundColor(this.getResources().getColor(R.color.black_transparent_80));
        enterOTP.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                strOtp = pinview.getValue();
                Log.d(TAG, "onCreate: otp" + strOtp);
                validateOTP(strOtp);
            }
        });


        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateOTP(strOtp);
            }
        });
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOTP();
            }
        });
    }

    private void getIntentData() {
        callType = getIntent().getIntExtra(EXTRA_CALL_TYPE, AuthType.LOGIN_TYPE);
        mobileNumber = getIntent().getStringExtra(EXTRA_MOBILE_NUMBER);
        userMessage = getIntent().getStringExtra(EXTRA_MESSAGE);
//        if (callType == AuthType.SIGNUP_TYPE) {
//            userName = getIntent().getStringExtra(EXTRA_NAME);
//            userEmail = getIntent().getStringExtra(EXTRA_EMAIL_ID);
//            referCode = getIntent().getStringExtra(EXTRA_REFER_CODE);
//        }
    }



    public void resendOTP() {
        if (callType == AuthType.LOGIN_TYPE) {
            UserService.getLoginOtp(this, mobileNumber, this, RESEND_LOGIN_OTP_REQUEST_CODE);
        }
//        else if (callType == AuthType.SIGNUP_TYPE) {
//            UserService.sendSignUpOTP(this,userEmail, mobileNumber, this, RESEND_REGISTER_OTP_REQUEST_CODE);
//        }
    }

    /**
     * start timer
     */
    public synchronized void startTimer() {
//        enterOTP.setText("");

        if (timer == null)
            timer = new Timer();
        MyTimerTask skipTimerTask = new MyTimerTask(180, new OnTimerChangeListener() {
            @Override
            public void updateTimerProgress(int timeCounter) {
                runOnUiThread(() -> {
                    resendotp.setText(String.format(resendInString, timeCounter));
                    if (timeCounter == 0) {
                        cancelTimer();
                        updateResentOTPLabel();
                    }
                });

            }

            @Override
            public void stopTimer() {
                cancelTimer();
            }
        });
        timer.scheduleAtFixedRate(skipTimerTask, 0, 1000);
    }

    private void updateResentOTPLabel() {
        resendotp.setText(TextUtils.changeColorOfString(this, resendString, resendString.length() - 6, resendString.length()));
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    private void validateOTP(String otp) {
//        KeyboardUtils.hideSoftKeyboard();
        String getOTP = otp;
        if (android.text.TextUtils.isEmpty(getOTP) || getOTP.length() != 4) {
//            ToastUtils.shortToast("");
            ToastUtils.shortToast(getResources().getString(R.string.otp_reneter));
        } else {
            //do verification of otp
            verifyOTP(getOTP);
        }
    }

    private void verifyOTP(String otp) {
        if (callType == AuthType.LOGIN_TYPE) {
            UserService.verifyLoginOTP(this, mobileNumber, otp, this, OTP_LOGIN_REQUEST_CODE);
        }
//        else if (callType == AuthType.SIGNUP_TYPE) {
//            UserService.doCustomerRegister(this, userName, mobileNumber, userEmail, otp,referCode, this, REGISTRATION_REQUEST_CODE);
//        }
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, @Nullable Object request) {
        switch (requestCode) {
            case OTP_LOGIN_REQUEST_CODE:
                if (response != null && response.isSuccessful()) {
                    UserManagerResponse userManagerResponse = (UserManagerResponse) response.body();
                    if (userManagerResponse != null) {
                        if (userManagerResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
//                            ToastUtils.shortToast("Login successful." +userManagerResponse.getUserManager());
                            PreferenceManger.onLoginRegisterSuccess(this, userManagerResponse.getUserManager());
                            ArogyaApplication.getPreferenceManager().putString(PreferenceManger.PHONE_NUMBER,userManagerResponse.getUserManager().getUserPhoneNumber());
//                            ArogyaApplication.getPreferenceManager().putString(PreferenceManger.PHONE_NUMBER,userManagerResponse.getUserManager().getUserPhoneNumber());
//                            ArogyaApplication.getPreferenceManager().putString(PreferenceManger.PHONE_NUMBER,userManagerResponse.getUserManager().getUserPhoneNumber());
//                            ArogyaApplication.getPreferenceManager().putString(PreferenceManger.PHONE_NUMBER,userManagerResponse.getUserManager().getUserPhoneNumber());
                        } else {
                            ToastUtils.shortToast(userManagerResponse.getStatusMessage());
                        }
                    } else {
                        ToastUtils.shortToast(R.string.login_failed_message);
                    }

                } else {
                    ToastUtils.shortToast(R.string.login_failed_message);

                }
                break;
//            case REGISTRATION_REQUEST_CODE:
//                if (response != null && response.isSuccessful()) {
//                    UserManagerResponse userManagerResponse = (UserManagerResponse) response.body();
//                    if (userManagerResponse != null) {
//                        if (userManagerResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
//                            ToastUtils.shortToast("Registration successful.");
//                            PreferenceManger.onLoginRegisterSuccess(this, userManagerResponse.getUserManager());
//                        } else {
//                            ToastUtils.shortToast(userManagerResponse.getStatusMessage());
//                        }
//                    } else {
//                        ToastUtils.shortToast(R.string.register_failed_message);
//                    }
//
//                } else {
//                    ToastUtils.shortToast(R.string.register_failed_message);
//                }
//                break;
//            case RESEND_REGISTER_OTP_REQUEST_CODE:
            case RESEND_LOGIN_OTP_REQUEST_CODE:
                if (response != null && response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        ToastUtils.shortToast(baseResponse.getStatusMessage());
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            cancelTimer();
                            startTimer();
                        }
                    } else {
                        ToastUtils.shortToast(R.string.resend_otp_failed);
                    }
                } else {
                    ToastUtils.shortToast(R.string.resend_otp_failed);
                }
                break;

        }
        ProgressDialogFragment.dismissProgress(getSupportFragmentManager());
    }


    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, @Nullable Object request) {
        switch (requestCode) {
            case OTP_LOGIN_REQUEST_CODE:
                Logger.ErrorLog(TAG, "OTP LOGIN FAILED : " + t.getLocalizedMessage());
                ToastUtils.shortToast(R.string.login_failed_message);
                break;
//            case REGISTRATION_REQUEST_CODE:
//                Logger.ErrorLog(TAG, "REGISTER FAILED : " + t.getLocalizedMessage());
//                ToastUtils.shortToast(R.string.register_failed_message);
//                break;
//            case RESEND_LOGIN_OTP_REQUEST_CODE:
//            case RESEND_REGISTER_OTP_REQUEST_CODE:
//                Logger.ErrorLog(TAG, "RESEND OTP FAILED : " + t.getLocalizedMessage());
//                ToastUtils.shortToast(R.string.resend_otp_failed);
//                break;
        }
    }

    @Override
    public void onNoNetwork(int requestCode) {

    }

}
