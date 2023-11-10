package com.inventics.ekalarogya.training.user_auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_base.BaseActivity;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.KeyboardUtils;
import com.inventics.ekalarogya.training.utils.Logger;
import com.inventics.ekalarogya.training.utils.TextUtils;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import butterknife.BindString;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by sonu on 20:10, 28/10/18
 * Copyright (c) 2018 . All rights reserved.
 */
public class LoginActivity extends BaseActivity implements RetroAPICallback {

    private static final int SEND_OTP_REQUEST_CODE = 1;
    private static final String TAG = LoginActivity.class.getSimpleName();

    public static void openLoginActivity(Context context) {
        Log.d(TAG, "openLoginActivity: ");
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @BindView(R.id.et_phone)
    EditText et_phone;
  
    @BindView(R.id.btnnext)
    Button btnnext;
    
    @BindString(R.string.dont_have_account)
    String signUpString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData(view);
            }
        });
      
    }




    private void validateData(View view) {
        KeyboardUtils.hideSoftKeyboard(view, this);
        String getMobileNumber = TextUtils.getText(et_phone);

        if (android.text.TextUtils.isEmpty(getMobileNumber)) {
            et_phone.setError("Please enter mobile number.");
        }else if (getMobileNumber.length()!=10) {
            et_phone.setError("Please enter valid mobile number.");
        } else {
            Log.d(TAG, "validateData: "+getMobileNumber);
            sendLoginOTP(getMobileNumber);
        }

    }

    public void sendLoginOTP(String mobileNumber) {
        UserService.getLoginOtp(this, mobileNumber, this, SEND_OTP_REQUEST_CODE);
    }


    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, @Nullable Object request) {
        switch (requestCode) {
            case SEND_OTP_REQUEST_CODE:
                if (response != null && response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        ToastUtils.shortToast(baseResponse.getStatusMessage());
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            OTPActivity.openOTPActivity(this, AuthType.LOGIN_TYPE, baseResponse.getStatusMessage(),TextUtils.getText(et_phone));
                        }
                        //else
//                        {
//                            SignUpActivity.openSignUpActivity(this,true,et_phone.getText().toString().trim());
//                        }
                    } else {
                        ToastUtils.shortToast(R.string.login_failed_message);
                       }
                } else {
                    ToastUtils.shortToast(R.string.login_failed_message);

                }
                break;
        }
        ProgressDialogFragment.dismissProgress(getSupportFragmentManager());
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, @Nullable Object request) {
        switch (requestCode) {
            case SEND_OTP_REQUEST_CODE:
                Logger.ErrorLog(TAG, "SEND OTP FAILED : " + t.getLocalizedMessage());
                ToastUtils.shortToast(R.string.login_failed_message);
                break;
        }
    }

    @Override
    public void onNoNetwork(int requestCode) {
        switch (requestCode) {
            case SEND_OTP_REQUEST_CODE:
                break;

        }
    }
}
