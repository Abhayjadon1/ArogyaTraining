package com.inventics.ekalarogya.training.rest.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.inventics.ekalarogya.training.BuildConfig;;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.main.support_ticket.another_client.ApiInterfaceSupport;
import com.inventics.ekalarogya.training.main.support_ticket.another_client.BaseServiceSupportModule;
import com.inventics.ekalarogya.training.models.FamilyMembersModelResponse;
import com.inventics.ekalarogya.training.models.SocialSanitisationModel;
import com.inventics.ekalarogya.training.rest.ApiInterface;
import com.inventics.ekalarogya.training.rest.response.AppVersionResponse;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.response.GetConnection;
import com.inventics.ekalarogya.training.rest.response.HerbalRemediesResponse;
import com.inventics.ekalarogya.training.rest.response.GetAllListResponse;
import com.inventics.ekalarogya.training.rest.response.SocialSanitisationDetailResponse;
import com.inventics.ekalarogya.training.rest.response.UserManagerResponse;
import com.inventics.ekalarogya.training.rest.response.VanAushadhiDeialResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sonu on 07/01/18.
 */

public final class UserService {
    private static final ApiInterface apiService =
            BaseService.getAPIClient().create(ApiInterface.class);
    private static final ApiInterfaceSupport apiService1 = BaseServiceSupportModule.getAPISupportClient().create(ApiInterfaceSupport.class);

    public UserService() {
    }

    public static void getConnection(final Context context, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(false)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<GetConnection> call = apiService.getConnection(BuildConfig.API_KEY);
        call.enqueue(new Callback<GetConnection>() {
            @Override
            public void onResponse(@NonNull Call<GetConnection> call, @NonNull Response<GetConnection> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }

            @Override
            public void onFailure(@NonNull Call<GetConnection> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }

    public static void checkAppVersion(final Context context, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(false)) {
                       retroAPICallback.onNoNetwork(requestCode);
            return;
        }
//        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());


        int appVersion = BuildConfig.VERSION_CODE;

        Call<AppVersionResponse> call = apiService.checkAppVersion(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), appVersion);
        call.enqueue(new Callback<AppVersionResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppVersionResponse> call, @NonNull Response<AppVersionResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
//                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }

            @Override
            public void onFailure(@NonNull Call<AppVersionResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
//                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });

    }

    public static void getLoginOtp(final Context context, final String mobileNumber, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
                       retroAPICallback.onNoNetwork(requestCode);
            return;
        }

        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.getLoginOtp(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), mobileNumber);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, mobileNumber);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, mobileNumber);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
            }
        });
    }

    public static void verifyLoginOTP(final Context context, String mobileNumber, String otp, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
//        String fcm = FCMUtils.getFCMToken();
//
//        if (TextUtils.isEmpty(fcm)) {
//            ToastUtils.longToast("Failed to do login. Please try again.");
//            return;
//        }
        Call<UserManagerResponse> call = apiService.verifyLoginOTP(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), mobileNumber, otp);
        call.enqueue(new Callback<UserManagerResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserManagerResponse> call, @NonNull Response<UserManagerResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }

            @Override
            public void onFailure(@NonNull Call<UserManagerResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }

//    public static void doLogout(final Context context, final RetroAPICallback retroAPICallback, final int requestCode) {
//        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
//            retroAPICallback.onNoNetwork(requestCode);
//            return;
//        }
//ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
//
//        Call<BaseResponse> call = apiService.logout(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
//        call.enqueue(new Callback<BaseResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
//                retroAPICallback.onResponse(call, response, requestCode, null);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
//                retroAPICallback.onFailure(call, t, requestCode, null);
//                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());
//
//            }
//        });
//    }

    public static void getDashboard(final Context context,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
//        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.dashboard(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
//                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
//               ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }

    public static void getProfile(final Context context,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
         ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.profile(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
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

    public static void addProfile(final Context context,String gender, String aadhar_photo,String aadharNumber,String panPhoto,String panNumber,String accountNumber,
                                  String accHolderName,String bankName,String bankBranch,String ifscCode,String bankRegisteredMobile, String name,String mobile, String profile_img,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
         ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.profileUpdate(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),gender,aadhar_photo,aadharNumber,panPhoto,panNumber,accountNumber,accHolderName,bankName,bankBranch,ifscCode,bankRegisteredMobile,name,mobile,profile_img);
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



    public static void getSanyojikaList(final Context context,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.sanyojikaList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
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


    public static void getOccupationList(final Context context,String lang,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
//        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<GetAllListResponse> call = apiService.occupationList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID),
                ArogyaApplication.getPreferenceManager().getAuthCode(),lang);
        call.enqueue(new Callback<GetAllListResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAllListResponse> call, @NonNull Response<GetAllListResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<GetAllListResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
               ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }




    public static void getVillageList(final Context context,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
//        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.villageList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
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

    public static void getVillageListGkv(final Context context,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.gkvVillage(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }
    public static void getGkvReport(final Context context,String village_id,String month,String year,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.gkvReport(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,month,year);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }

    public static void downloadGkvReport(final Context context,String pdflink,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<ResponseBody> call = apiService.downloadInvoice(pdflink);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }

    public static void getTaskList(final Context context,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.taskList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
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


    public static void getTaskDetails(final Context context,String taskID,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.taskDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),taskID);
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


    public static void updateTask(final Context context,String taskID,String taskStatus,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.taskUpdate(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),taskID,taskStatus);
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


    public static void getVillageHeadList(final Context context,String village_id,String search_field,int page,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.villageHeadList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,search_field,page);
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

    public static void getVillageFamilyMembersList(final Context context,String village_id,String head_id,String status,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<FamilyMembersModelResponse> call = apiService.villageFamilyMembersList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,head_id,status);
        call.enqueue(new Callback<FamilyMembersModelResponse>() {
            @Override
            public void onResponse(@NonNull Call<FamilyMembersModelResponse> call, @NonNull Response<FamilyMembersModelResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<FamilyMembersModelResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }

    public static void getVillageFamilyMembersListNew(final Context context,String head_id,String status,int page,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.villageMemberList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),head_id,status, String.valueOf(page));
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

    public static void getchildWomenList(final Context context,String village_id,String head_id,String page,String status,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<FamilyMembersModelResponse> call = apiService.childWomenList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,head_id,page,status);
        call.enqueue(new Callback<FamilyMembersModelResponse>() {
            @Override
            public void onResponse(@NonNull Call<FamilyMembersModelResponse> call, @NonNull Response<FamilyMembersModelResponse> response) {
                retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<FamilyMembersModelResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }



    public static void addFamilyHead(final Context context,String status,String head_name, String gender,String age,String phone_num,String occupation, String address,String latitude,String longitude,String adhar_no,String adhar_photo, String village_id, String headId,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
       ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.addFamilyHeadNew(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),status,head_name,gender,age,phone_num,occupation,address,latitude,longitude,adhar_no,adhar_photo,village_id,headId);
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

    public static void getFamilyHeadDetails(final Context context,String head_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.getFamilyHead(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),head_id);
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

    public static void addFamilyMember(final Context context,String status,String head_id,String head_code,String member_name,String marital_status,String gender,String age,String occupation,String relation_with_head,
                                      String adhar_no,String is_pregnant,String village_id,String member_id,String adhar_photo,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
       ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.addFamilyMemberNew(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),status,head_id,head_code,member_name,marital_status,gender,age,occupation,relation_with_head,adhar_no,is_pregnant,
                village_id,member_id,adhar_photo);
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


    public static void getFamilyMemberDetails(final Context context,String head_id,String member_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.getFamilyMember(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),head_id,member_id);
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



    public static void getPersonalHygineList(final Context context,String village_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.personalHygineList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id);
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


    public static void getPersonalHygineChildList(final Context context,int page,String head_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

        Call<BaseResponse> call = apiService.personalHygineChild(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),head_id,page);
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


    public static void addPersonalHygineChild(final Context context,String head_id,String village_id,String member_id,String nail_check,String health_check,String health_image,String inspection_date,String status,String hygiene_id,String nail_image,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.addHygineChild(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),head_id,village_id,member_id,nail_check,health_check,health_image,inspection_date,status,hygiene_id,nail_image);
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


    public static void getDetailsHygineCheckup(final Context context,String hygiene_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.viewDetailsHygineCheckup(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),hygiene_id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }


    public static void getSocialSanitisationList(final Context context,String status,String village_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.socialSanitisationList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),status,village_id);
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


    public static void getSocialSanitisationDetails(final Context context,String sanitation_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<SocialSanitisationDetailResponse> call = apiService.socialSanitisationDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),sanitation_id);
        call.enqueue(new Callback<SocialSanitisationDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<SocialSanitisationDetailResponse> call, @NonNull Response<SocialSanitisationDetailResponse> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<SocialSanitisationDetailResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }



    public static void addSocialSanitisation(final Context context,String village_id,String head_id,String soakpit_status,String soakpit_image,String compost_status,String toilet_status,String water_treatment,String toilet_image,String wall_writing,String people_use_toilet,String medicine_plantation,String nutrition_plantation,String filter_uses,String tree_planting,String wall_writing_photo,String people_use_toilet_photo,String medicine_plantation_photo,String nutrition_plantation_photo,String filter_uses_photo,String tree_planting_photo,String water_treatment_image,String composit_image,String inspection_date,String sanitation_id,String status,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<SocialSanitisationModel> call = apiService.socialSanitisationAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,head_id,soakpit_status,soakpit_image,compost_status,toilet_status,water_treatment,toilet_image,wall_writing,people_use_toilet,medicine_plantation,nutrition_plantation,filter_uses,tree_planting,wall_writing_photo,people_use_toilet_photo,medicine_plantation_photo,nutrition_plantation_photo,filter_uses_photo,tree_planting_photo,water_treatment_image,composit_image,inspection_date,sanitation_id,status);
        call.enqueue(new Callback<SocialSanitisationModel>() {
            @Override
            public void onResponse(@NonNull Call<SocialSanitisationModel> call, @NonNull Response<SocialSanitisationModel> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<SocialSanitisationModel> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }



    public static void getDiseasePreventionList(final Context context,int page,String search_village,String months,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.diseasePreventionList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),page,search_village,months);
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

    public static void addDiseasePrevention(final Context context, String village_id, String inspection_date, String cleanliness, String malaria, String nutrition, String vaccination, String maternal_safety, String child_safety,String other, String family_present, String male_present, String female_present, String children_present, String total_present, final RetroAPICallback retroAPICallback, final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.diseasePreventionAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,inspection_date,cleanliness,malaria,nutrition,vaccination,maternal_safety,child_safety,other,family_present,male_present,female_present,children_present,total_present);
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

    public static void getAnemiaCheckupList(final Context context,String village_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.anemiaCheckupList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id);
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


    public static void getAnemiaCheckupDetails(final Context context,String anemia_checkup_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.anemiaCheckupDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),anemia_checkup_id);
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



    public static void addAnemiaCheckup(final Context context,String village_id,String head_id,String member_id,String inspection_date,String is_pregnant,
                                        String anemia_test,String is_anemic,String anemia_type,String status,String age,String anemic_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.anemiaCheckupAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,head_id,member_id,inspection_date,is_pregnant,anemia_test,is_anemic,anemia_type,status,age,anemic_id);
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




    public static void getMalnutritionCheckupList(final Context context,String village_id,String page,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.malnutrionCheckupList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,page);
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
    public static void getMalnutritionCheckupDetails(final Context context,String malnutrition_checkup_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.malnutrionCheckupDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),malnutrition_checkup_id);
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

    public static void addMalnurishData(final Context context,String status,String age,String gender,String village_id,String head_id,String member_id,String inspection_date,String checkup,String checkup_id,String mid_arm_measurement,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.malnutrionAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),status,age,gender,village_id,head_id,member_id,inspection_date,checkup,checkup_id,mid_arm_measurement);
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


    public static void getayurvedicTreatmentList(final Context context,String page,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<HerbalRemediesResponse> call = apiService.ayurvedicTreatment(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),page);
        call.enqueue(new Callback<HerbalRemediesResponse>() {
            @Override
            public void onResponse(@NonNull Call<HerbalRemediesResponse> call, @NonNull Response<HerbalRemediesResponse> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<HerbalRemediesResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }


    public static void getfamilyvisitList(final Context context,String page,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.familyvisitlist(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),page);
//        Call<BaseResponse> call = apiService.familyvisitlist("x1dYvMAaxt", "SnkRloYC0K",page);
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


    public static void getAyurvedicTreatmentDetail(final Context context,String treatment_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<HerbalRemediesResponse> call = apiService.ayurvedicTreatmentDetail(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),treatment_id);
        call.enqueue(new Callback<HerbalRemediesResponse>() {
            @Override
            public void onResponse(@NonNull Call<HerbalRemediesResponse> call, @NonNull Response<HerbalRemediesResponse> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<HerbalRemediesResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }


    public static void addAyurvedicTreatment(final Context context,String village_id,String camp_month,String male,String female,String children,String total,String treatment_type,String total_cured,String status,String herbal_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<HerbalRemediesResponse> call = apiService.ayurvedicTreatmentadd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,camp_month,male,female,children,total,treatment_type,total_cured,status,herbal_id);
        call.enqueue(new Callback<HerbalRemediesResponse>() {
            @Override
            public void onResponse(@NonNull Call<HerbalRemediesResponse> call, @NonNull Response<HerbalRemediesResponse> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<HerbalRemediesResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }

    public static void addFamilyVisit(final Context context,String village_id,String camp_month,String male,String female,String children,String total,String family,String status,String subject,String headId,String visit_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.familyVisitadd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,camp_month,male,female,children,total,family,status,subject,headId,visit_id);
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

    public static void getMedicineGardenList(final Context context,String village_id,String page,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.medicineGardenList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,page);
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


    public static void getSingleMedicineGardenList(final Context context,String head_id,String page,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.singlemedicineGardenList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),head_id,page);
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


    public static void addMedicineGarden(final Context context,String status,String village_id,String head_id,String plant_id,String plant_count,String date,String garden_id,String plant_img,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.medicineGardenAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),status,village_id,head_id,plant_id,plant_count,date,garden_id,plant_img);
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


    //Medicine garden more api ____


    public static void getvanAushadhiList(final Context context,String village_id,String status,String page,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.vanAushadhiList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,status);
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

    public static void getvanAushadhiDetails(final Context context,String patientId,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<VanAushadhiDeialResponse> call = apiService.vanAushadhiDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),patientId);
        call.enqueue(new Callback<VanAushadhiDeialResponse>() {
            @Override
            public void onResponse(@NonNull Call<VanAushadhiDeialResponse> call, @NonNull Response<VanAushadhiDeialResponse> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<VanAushadhiDeialResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }

    public static void getvanAushadhiAdd(final Context context,String village_id,String date,String head_id,String member_name,String guest_patient_name,String pat_phone,String age,String problem,String prescription,String fees,String treatment_status,String status,String patient_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<VanAushadhiDeialResponse> call = apiService.vanAushadhiAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,date,head_id,member_name,guest_patient_name,pat_phone,age,problem,prescription,fees,treatment_status,status,patient_id);
        call.enqueue(new Callback<VanAushadhiDeialResponse>() {
            @Override
            public void onResponse(@NonNull Call<VanAushadhiDeialResponse> call, @NonNull Response<VanAushadhiDeialResponse> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<VanAushadhiDeialResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }


    public static void getFirstAidList(final Context context,String village_id,String page,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.firstAidList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id);
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

    public static void addFirstAiddetails(final Context context,String village_id,String head_id,String member_name,String guestname,String date,String cuts,String heart_attacks,String unconscious,String snake_bites,String other,String training,String record,String remarks,String eh_id,String status,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.firstAidAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,head_id,member_name,guestname,date,cuts,heart_attacks,unconscious,snake_bites,other,training,record,remarks,eh_id,status);
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

    public static void getSamitiList(final Context context,String page,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.samitiList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
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

    public static void getSamitiDetails(final Context context,String meeting_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.samitiListDeatils(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),meeting_id);
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

    public static void uploadImageApi(final Context context,String path,String base64,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.uploadImage(path,base64);
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


    public static void addSamitiDetails(final Context context,String status,String samiti_id,String village_id,String date,String samiti_member,String sevika_member,String sanyojika,String sevavriti,String sanch_coordinator,String total,String image,String remarks,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.samitiListadd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),status,samiti_id,village_id,date,samiti_member,sevika_member,sanyojika,sevavriti,sanch_coordinator,total,image,remarks);
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



    public static void getEyeScreeningList(final Context context,String village_id,String flag,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.eyeScreeningList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,flag);
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

    public static void addEyeScreening(final Context context,String status,String village_id,String patient_id,String member_id,String head_id,String guest_patient_name,String member_name,String patient_mobile,String gender,String test_date,String vision_defect,String age,String es_id,String remarks,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.eyeScreeningAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),status,village_id,patient_id,member_id,head_id,guest_patient_name,member_name,patient_mobile,gender,test_date,vision_defect,age,es_id,remarks);
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


    public static void getEyeVanList(final Context context,String village_id,String flag,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.eyeVanList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,flag);
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

    public static void addEyeVanDetails(final Context context,String status,String eap_id,String test_date,String referred_hospital,String glasses_fee,String glasses_issue,String medicine_issue,String medicine_fee,
                                        String problem_code,String r_dist_sph,String r_dist_cyl,String r_dist_axis,String l_dist_sph,String l_dist_cyl,String l_dist_axis,String r_reading_sph,String r_reading_cyl,String r_reading_axis,String l_reading_sph,String l_reading_cyl,String l_reading_axis,String right_power,String left_power,String photo,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.eyeVanAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),status,eap_id,test_date,referred_hospital,glasses_fee,glasses_issue,medicine_issue,medicine_fee,problem_code,r_dist_sph,r_dist_cyl,r_dist_axis,l_dist_sph,l_dist_cyl,l_dist_axis,r_reading_sph,r_reading_cyl,r_reading_axis,l_reading_sph,l_reading_cyl,l_reading_axis,right_power,left_power,photo);
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


    public static void getPosterDisplayList(final Context context,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.posterDisplayList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
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

    public static void getPosterDisplayDetails(final Context context,String poster_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
//ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.posterDisplayDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),poster_id);
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

    public static void addPosterDisplay(final Context context, String village_id, String arogya_topic, String week, String poster_count, String total_present, String posters, String status, String poster_display_id, final RetroAPICallback retroAPICallback, final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.posterDisplayAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,arogya_topic,week,poster_count,total_present,posters,status,poster_display_id);
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

    public static void getVillagevisitList(final Context context,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.villagevisitList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
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

    public static void getVillagevisitDetails(final Context context,String visit_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.getVillagevisitDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),visit_id);
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

    public static void addVillagevisitData(final Context context,String village_id,String sanyojika_name,String visit,String date,String responsibility,String purpose,String status,String visit_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.addVillagevisit(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id,sanyojika_name,visit,date,responsibility,purpose,status,visit_id);
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


    public static void getArogyaCampList(final Context context,String village_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.arogyaCampList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id);
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

    public static void getArogyaCampDetails(final Context context,String camp_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.arogyaCampDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),camp_id);
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

    public static void addArogyaCampData(final Context context,String status,String arogya_camp_id,String village_id,String date,String patient_boy,String patient_girl,String patient_male,String patient_female,String total_child,String total, String doctor,String ref_govt,String ref_pvt,String camp_photo,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.addArogyaCampDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),status,arogya_camp_id,village_id,date,patient_boy,patient_girl,patient_male,patient_female,total_child,total,doctor,ref_pvt,ref_govt,camp_photo);
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


    public static void getJagranList(final Context context,String village_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.jagaranList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),village_id);
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

    public static void getJagranDetails(final Context context,String jagaran_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.jagaranDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),jagaran_id);
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

    public static void addJagranDetail(final Context context,String status,String jagran_id,String village_id,String program_date,String program_data,final RetroAPICallback retroAPICallback,final int requestCode){
        //String soak_this,String waste_this,String wall_this,String toilet_this,String medicine_this,String nutrition_this,String filter_this,String tree_this,String total,
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.addJagaranDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),status,jagran_id,village_id,program_date,program_data);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
//                 ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }


    public static void getInspectionList(final Context context,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.inspectionList(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode());
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

    public static void getInspectionDetails(final Context context,String inspection_id,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.inspectionDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),inspection_id);
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

    public static void addInspectionDetails(final Context context,String sanyojak,String inspection_date,String vidyalay_grade, String jagran_grade,String family_grade,String poster_grade,String vanaushadhi_grade,String remark,final RetroAPICallback retroAPICallback,final int requestCode){
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        Call<BaseResponse> call = apiService.inspectionAdd(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(),sanyojak,inspection_date,vidyalay_grade,jagran_grade,family_grade,poster_grade,vanaushadhi_grade,remark);
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


    public static void doLogout(final Context context, double lat, double lng, double speed, double accuracy, double battery, String motion, int signalStrength, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        Call<BaseResponse> call;
        call = apiService.logout(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID), ArogyaApplication.getPreferenceManager().getAuthCode(), lat, lng, speed, accuracy, battery, motion, signalStrength);
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());

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



    public static void createTicket(final Context context,String photo,String sub,String msg,String app_id, final RetroAPICallback retroAPICallback, final int requestCode) {
        if (!ArogyaApplication.getInstance().isInternetConnected(true)) {
            retroAPICallback.onNoNetwork(requestCode);
            return;
        }
        Call<BaseResponse> call;
        call = apiService1.createTicket(sub,ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.PHONE_NUMBER),"JADS7289ASDDB72389BASDBA89",ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.NAME),ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.VILL_ID),msg,app_id,photo);
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }

    public static void uploadImage(final Context context,String photo, final RetroAPICallback retroAPICallback, final int requestCode) {
        Call<BaseResponse> call;
        call = apiService1.uploadImageTicket(photo,"JADS7289ASDDB72389BASDBA89");
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                                 retroAPICallback.onResponse(call, response, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                retroAPICallback.onFailure(call, t, requestCode, null);
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

            }
        });
    }
    public static void spportTicket(final Context context, final RetroAPICallback retroAPICallback, final int requestCode) {

//        MyPref myPref = new MyPref(context);
        Call<BaseResponse> call;
        call = apiService1.getSupportTicketDetails(ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.PHONE_NUMBER), "JADS7289ASDDB72389BASDBA89");
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
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

    public static void spportTicketDetail(final Context context,String id, final RetroAPICallback retroAPICallback, final int requestCode) {

//        MyPref myPref = new MyPref(context);
        Call<BaseResponse> call;
        call = apiService1.getSupportTicketDetailsData(id,"JADS7289ASDDB72389BASDBA89");
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
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

    public static void sendConversationMsg(final Context context,String msg,String status,String id, final RetroAPICallback retroAPICallback, final int requestCode) {
        Call<BaseResponse> call;
        call = apiService1.addSupportChat(id,ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.PHONE_NUMBER),msg,status,ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.NAME),"JADS7289ASDDB72389BASDBA89");
        ProgressDialogFragment.showProgress(((AppCompatActivity) context).getSupportFragmentManager());
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

