package com.ispl.ekalarogya.training.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.helper.PreferenceManger;
import com.ispl.ekalarogya.training.main.MainActivity;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.service.DriverService;
import com.ispl.ekalarogya.training.utils.KeyboardUtils;
import com.ispl.ekalarogya.training.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by sonu on 28/01/18.
 */

public class StartEndTripDialogFragment extends DialogFragment implements RetroAPICallback {

    private static final String ARG_IS_ONLINE = "is_online";
    private static final String ARG_IMAGE_PATH = "image_path";
    private static final String ARG_IMAGE_URI = "image_uri";
    private static final String ARG_LATITUDE = "latitude";
    private static final String ARG_LONGITUDE = "longitude";
    private static final int GO_ONLINE_REQUEST_CODE = 1;
    private static final int GO_ONLINE_IMAGEREQUEST_CODE = 3;
    private static final int GO_OFFLINE_REQUEST_CODE = 2;
    private static final int GO_OFFLINE_IMAGEREQUEST_CODE = 4;
    private static final String TAG = "StartEndTripDialogFragm";


    private static final int METER_READING_CAP = 300;//in KM
    @BindView(R.id.trip_dialog_label)
    TextView dialogTitle;
    @BindView(R.id.trip_meter_reading_input)
    EditText enterMeterReading;
    @BindView(R.id.trip_meter_reading_image_view)
    ImageView meterReadingImageView;
    @BindView(R.id.trip_dialog_start_trip_meter_reading_label)
    TextView startMeterReadingLabel;
    @BindView(R.id.instructions)
    TextView instructions;
    private Context context;
    private Unbinder unbinder;
    private String imagePath;
    private Bitmap imageBitmap;
    private boolean isOnline;
    private double lat, lng;

    private OnTripListener onStartTripListener;

    public StartEndTripDialogFragment() {
    }

    public static StartEndTripDialogFragment newInstance(Bitmap imageURI, String imagePath, boolean isOnline, double lat, double lng) {

        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_ONLINE, isOnline);
        args.putString(ARG_IMAGE_PATH, imagePath);
        args.putParcelable(ARG_IMAGE_URI, imageURI);
        args.putDouble(ARG_LATITUDE, lat);
        args.putDouble(ARG_LONGITUDE, lng);
        StartEndTripDialogFragment fragment = new StartEndTripDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void showTripDialogFragment(FragmentManager fragmentManager, Bitmap imageURI, String imagePath, boolean isOnline, double lat, double lng) {
        if (fragmentManager == null) {
            ToastUtils.shortToast("Oops!! Some error occurred. Please try again.");
            return;
        }
        Fragment tripFragment = fragmentManager.findFragmentByTag(StartEndTripDialogFragment.class.getSimpleName());

        if (tripFragment == null) {
            StartEndTripDialogFragment startEndTripDialogFragment = StartEndTripDialogFragment.newInstance(imageURI, imagePath, isOnline, lat, lng);
            startEndTripDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
            startEndTripDialogFragment.setCancelable(false);
            startEndTripDialogFragment.show(fragmentManager, StartEndTripDialogFragment.class.getSimpleName());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        try {
            onStartTripListener = (OnTripListener) context;

        } catch (Exception ignored) {

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isOnline = getArguments().getBoolean(ARG_IS_ONLINE, false);
            imagePath = getArguments().getString(ARG_IMAGE_PATH);
            imageBitmap = getArguments().getParcelable(ARG_IMAGE_URI);
            lat = getArguments().getDouble(ARG_LATITUDE);
            lng = getArguments().getDouble(ARG_LONGITUDE);
            Log.d(TAG, "onCreate: " + imagePath + "\n" + imageBitmap);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_end_trip_dialog_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        showHideViewForUsers();

        dialogTitle.setText(isOnline ? "Start Day" : "End Day");

        Log.d(TAG, "onViewCreated: "+imageBitmap+"\n"+imagePath);
        meterReadingImageView.setImageBitmap(imageBitmap);
    }

    private void fieldExecutiveInitViews() {
        enterMeterReading.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                doValidation(textView);
                return true;
            }
            return false;
        });
        if (isOnline) {
            startMeterReadingLabel.setVisibility(View.INVISIBLE);
            instructions.setVisibility(View.INVISIBLE);
        } else {
            String startMeterReading = ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.START_TRIP_METER_READING);
            if (!TextUtils.isEmpty(startMeterReading)) {
                startMeterReadingLabel.setText("Start Meter Reading : " + startMeterReading);
                startMeterReadingLabel.setVisibility(View.VISIBLE);
                instructions.setText("Warning : please note that only once you can check-in and check-out per day");
                instructions.setVisibility(View.VISIBLE);
            } else {
                startMeterReadingLabel.setVisibility(View.INVISIBLE);
                instructions.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void employeeInitViews() {
        if (isOnline) {
            startMeterReadingLabel.setVisibility(View.INVISIBLE);
            instructions.setVisibility(View.INVISIBLE);
        } else {
            instructions.setText("Warning : please note that only once you can check-in and check-out per day");
            instructions.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(context);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
        return dialog;
    }

    @OnClick({R.id.trip_dialog_submit_button, R.id.trip_dialog_cancel_button})
    void implementClickEvent(View view) {
        switch (view.getId()) {
            case R.id.trip_dialog_submit_button:
                doValidation(view);
                break;
            case R.id.trip_dialog_cancel_button:
                if (onStartTripListener != null) {
                    onStartTripListener.onTripCancelled();
                }
                ((MainActivity)getActivity()).checkOnline();
                dismiss();
                break;
        }
    }

    private void doValidation(View view) {
        KeyboardUtils.hideSoftKeyboard(view, context);

        if (isOnline) {
            Log.d(TAG, "doValidation: isOnline");


            DriverService.idUploadImageStartOnline(context, imagePath, this, GO_ONLINE_IMAGEREQUEST_CODE);
        } else {
            Log.d(TAG, "doValidation: isOffLine");
            Log.d(TAG, "doValidation: " + imagePath + " " + lat + " " + lng + " " + ArogyaApplication.getPreferenceManager().getUserId());
            Log.d(TAG, "doValidation: " + ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID) + " " + ArogyaApplication.getPreferenceManager().getAuthCode());
            DriverService.idUploadImageEndOnline(context, imagePath, this, GO_OFFLINE_IMAGEREQUEST_CODE);
        }
        //}

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode) {
            case GO_ONLINE_REQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();

                    if (baseResponse != null) {
                        ToastUtils.shortToast(baseResponse.getStatusMessage());

                        if (RestUtils.isUserSessionActive(getContext(), baseResponse) && baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            if (onStartTripListener != null) {
                                onStartTripListener.onTripSuccess();
                                ((MainActivity)getActivity()).checkOnline();
                            }
                            dismiss();
                        } else {
                            //when already started....
                            dismiss();
                        }
                    } else {
                        dismiss();
                        ToastUtils.shortToast("Oops!! Some error occurred. Please try again.base Response Null");
                    }
                } else {
                    dismiss();
                    ToastUtils.shortToast("Oops!! Some error occurred. Please try again.resonse is null");
                }
                break;
            case GO_OFFLINE_REQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();

                    if (baseResponse != null) {
                        ToastUtils.shortToast(baseResponse.getStatusMessage());

                        if (RestUtils.isUserSessionActive(getContext(), baseResponse) && baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            if (onStartTripListener != null) {
                                onStartTripListener.onTripSuccess();
                                ((MainActivity)getActivity()).checkOnline();
                            }
                            dismiss();
                        } else {
                            //when already started....
                            dismiss();
                        }
                    } else {
                        dismiss();
                        ToastUtils.shortToast("Oops!! Some error occurred. Please try again.base Response Null");
                    }
                } else {
                    dismiss();
                    ToastUtils.shortToast("Oops!! Some error occurred. Please try again.resonse is null");
                }
                break;

            case GO_ONLINE_IMAGEREQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();

                    if (baseResponse != null) {
                        ToastUtils.shortToast(baseResponse.getStatusMessage());

                        String imagePathUtils = baseResponse.getPath();
                        Log.d(TAG, "onResponse: " + imagePathUtils);

                        if (RestUtils.isUserSessionActive(getContext(), baseResponse) && baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            // int userType = FEApplication.getPreferenceManager().getIntegerValue(PreferenceManger.USER_TYPE_LOGIN_PREF);
                            //String user_type = SessionHandler.getInstance().get(getActivity(),ExtraUtils.USER_TYPE);

                            DriverService.goOnline(context, "", imagePathUtils, lat, lng, this, GO_ONLINE_REQUEST_CODE);
                        }
                    } else {
                        dismiss();
                        ToastUtils.shortToast("Oops!! Some error occurred. Please try again.base Response Null");
                    }
                } else {
                    dismiss();
                    ToastUtils.shortToast("Oops!! Some error occurred. Please try again.resonse is null");
                }
                break;

            case GO_OFFLINE_IMAGEREQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    ProgressDialogFragment.dismissProgress(getFragmentManager());
                    if (baseResponse != null) {
                        ToastUtils.shortToast(baseResponse.getStatusMessage());

                        String imagePathUtils = baseResponse.getPath();

                        if (RestUtils.isUserSessionActive(getContext(), baseResponse) && baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            // int userType = FEApplication.getPreferenceManager().getIntegerValue(PreferenceManger.USER_TYPE_LOGIN_PREF);
                            //String user_type = SessionHandler.getInstance().get(getActivity(),ExtraUtils.USER_TYPE);

                            DriverService.goOffline(context, "", imagePathUtils, lat, lng, this, GO_OFFLINE_REQUEST_CODE);
                        }

                    } else {
                        ToastUtils.shortToast("Oops!! Some error occurred. Please try again.base Response Null");
                    }
                } else {
                    ToastUtils.shortToast("Oops!! Some error occurred. Please try again.resonse is null");
                }
                break;
        }
        ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        switch (requestCode) {
            case GO_ONLINE_REQUEST_CODE:
            case GO_OFFLINE_REQUEST_CODE:
                Log.d(TAG, "StratTripDialog: " + t.getMessage());

                ToastUtils.shortToast("Oops!! Some error occurred. Please try again. faild response");
                break;
        }
    }

    @Override
    public void onNoNetwork(int requestCode) {
        Log.d(TAG, "StratTripDialog: " + requestCode);
    }

    private void showHideViewForUsers() {
//        //  int userType = FEApplication.getPreferenceManager().getIntegerValue(PreferenceManger.USER_TYPE_LOGIN_PREF);
//        String user_type = SessionHandler.getInstance().get(getActivity(), ExtraUtils.USER_TYPE);
//
//        if (user_type.equalsIgnoreCase(UserManager.USER_TYPE_EMPLOYEE)) {
//            enterMeterReading.setVisibility(View.INVISIBLE);
//            startMeterReadingLabel.setVisibility(View.INVISIBLE);
//            employeeInitViews();
////            instructions.setText("Warning : please note that only once you can check-in and check-out per day");
////            instructions.setVisibility(View.VISIBLE);
//        } else if (user_type.equalsIgnoreCase(UserManager.USER_TYPE_Sales_Executive)) {
//            enterMeterReading.setVisibility(View.VISIBLE);
//            startMeterReadingLabel.setVisibility(View.VISIBLE);
//            fieldExecutiveInitViews();
//        } else {
            enterMeterReading.setVisibility(View.INVISIBLE);
            startMeterReadingLabel.setVisibility(View.INVISIBLE);
            employeeInitViews();
//        }
    }

    public interface OnTripListener {
        void onTripSuccess();

        void onTripCancelled();
    }
}
