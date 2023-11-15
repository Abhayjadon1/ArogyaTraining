package com.ispl.ekalarogya.training.rest;

import com.ispl.ekalarogya.training.models.FamilyMembersModelResponse;
import com.ispl.ekalarogya.training.models.SocialSanitisationModel;
import com.ispl.ekalarogya.training.rest.response.AppVersionResponse;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.response.GetConnection;
import com.ispl.ekalarogya.training.rest.response.HerbalRemediesResponse;
import com.ispl.ekalarogya.training.rest.response.GetAllListResponse;
import com.ispl.ekalarogya.training.rest.response.SocialSanitisationDetailResponse;
import com.ispl.ekalarogya.training.rest.response.UserManagerResponse;
import com.ispl.ekalarogya.training.rest.response.VanAushadhiDeialResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("create_connection")
    Call<GetConnection> getConnection(@Field("api_key") String apiKey);

    @FormUrlEncoded
    @POST("check_version/{version}")
    Call<AppVersionResponse> checkAppVersion(@Field("connection_id") String connectionId, @Path("version") int appVersion);

    @FormUrlEncoded
    @POST("login/otp_request")
    Call<BaseResponse> getLoginOtp(@Field("connection_id") String connectionId, @Field("mobile_no") String phoneNumber);

    @FormUrlEncoded
    @POST("login/otp_verify")
    Call<UserManagerResponse> verifyLoginOTP(@Field("connection_id") String connectionId, @Field("mobile_no") String phoneNumber,
                                             @Field("otp") String otp);
//                                             @Field("fcm_token") String fcmToken);

    @FormUrlEncoded
    @POST("driver/uploadImage")
    Call<BaseResponse> goUploadImage(
            @Field("connection_id") String connectionId,
            @Field("auth_code") String authCode,
            @Field("img") String image);

    @FormUrlEncoded
    @POST("driver/locationUpdate")
    Call<BaseResponse> sendLocationUpdate(@Field("connection_id")
                                                  String connectionId,
                                          @Field("driver_id") String driverId,
                                          @Field("lat") double latitude,
                                          @Field("lng") double longitude,
                                          @Field("auth_code") String authCode,
                                          @Field("speed") double speed,
                                          @Field("accuracy") double accuracy,
                                          @Field("battery") double battery,
                                          @Field("motion") String motion,
                                          @Field("signal_strength") int signalStrength);

    @FormUrlEncoded
    @POST("driver/start_day")
    Call<BaseResponse> goOnline(
            @Field("connection_id") String connectionId,
            @Field("auth_code") String authCode,
            @Field("driver_id") String driverId,
            @Field("img") String image,
            @Field("meter_reading") String meterReading,
            @Field("lat") double latitude,
            @Field("lng") double longitude);
    @FormUrlEncoded
    @POST("driver/check_online")
    Call<BaseResponse> checkOnline(@Field("connection_id") String connectionId,
                                   @Field("auth_code") String authCode);
    @FormUrlEncoded
    @POST("driver/uploadImage_attendence")
    Call<BaseResponse> checkAttendance(@Field("connection_id") String connectionId,
                                       @Field("auth_code") String authCode);

    @FormUrlEncoded
    @POST("users/sevika-punch-out")
    Call<BaseResponse> offline(@Field("connection_id") String connectionId,
                                       @Field("auth_code") String authCode,
                               @Field("punch_out_image") String punch_in_image,
                               @Field("logout_lat") String latitude,
                               @Field("logout_long") String longitude);
    @FormUrlEncoded
    @POST("users/sevika-attendance")
    Call<BaseResponse> online(@Field("connection_id") String connectionId,
                               @Field("auth_code") String authCode,
                               @Field("punch_in_image") String punch_in_image,
                               @Field("latitude") String latitude,
                               @Field("longitude") String longitude
                              );


    @FormUrlEncoded
    @POST("driver/end_day")
    Call<BaseResponse> goOffline(
            @Field("connection_id") String connectionId,
            @Field("auth_code") String authCode,
            @Field("driver_id") String driverId,
            @Field("img") String image,
            @Field("meter_reading") String meterReading,
            @Field("lat") double latitude,
            @Field("lng") double longitude);
//    @FormUrlEncoded
//    @POST("request_signup_otp/{phone}")
//    Call<BaseResponse> sendSignUpOTP(@Field("connection_id") String connectionId,
//                                     @Path("phone") String phoneNumber);
//     @FormUrlEncoded
//    @POST("request_signup_otp_email/{phone}")
//    Call<BaseResponse> sendSignUpOTPEmail(@Field("connection_id") String connectionId,
//                                     @Field("user_email") String email,
//                                     @Path("phone") String phoneNumber);
//
//
//    @FormUrlEncoded
//    @POST("signup")
//    Call<UserManagerResponse> verifySignUpOTP(@Field("connection_id") String connectionId, @Field("name") String name, @Field("email") String email, @Field("user_phone") String phoneNumber, @Field("otp") String otp, @Field("fcm_token") String fcmToken,@Field("refer_code") String referCode);

    @FormUrlEncoded
    @POST("logout")
    Call<BaseResponse> logout(@Field("connection_id") String connectionId,
                              @Field("auth_code") String authCode,
                              @Field("lat") double lat,
                              @Field("long") double lng,
                              @Field("speed") double speed,
                              @Field("accuracy") double accuracy,
                              @Field("battery") double battery,
                              @Field("motion") String motion,
                              @Field("signal_strength") int signalStrength);
    @FormUrlEncoded
    @POST("dashboard")
    Call<BaseResponse>dashboard(@Field("connection_id") String connectionId,@Field("auth_code")String authCode);

    @FormUrlEncoded
    @POST("users/profile")
    Call<BaseResponse>profile(@Field("connection_id") String connectionId,@Field("auth_code")String authCode);

    @FormUrlEncoded
    @POST("users/profile/update")
    Call<BaseResponse>profileUpdate(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("gender")String gender,
                                    @Field("aadhar_photo")String aadhar_photo,
                                    @Field("aadharNumber")String aadharNumber,
                                    @Field("panPhoto")String panPhoto,
                                    @Field("panNumber")String panNumber,
                                    @Field("accountNumber")String accountNumber,
                                    @Field("accHolderName")String accHolderName,
                                    @Field("bankName")String bankName,
                                    @Field("bankBranch")String bankBranch,
                                    @Field("ifscCode")String ifscCode,
                                    @Field("bankRegisteredMobile")String bankRegisteredMobile,
                                    @Field("name")String name,
                                    @Field("mobile")String mobile,
                                    @Field("profile_img")String profile_img);



    @FormUrlEncoded
    @POST("users/sanyojika-list")
    Call<BaseResponse>sanyojikaList(@Field("connection_id") String connectionId,@Field("auth_code")String authCode);




    @FormUrlEncoded
    @POST("family/head/list")
    Call<BaseResponse>villageHeadList(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("village_id") String village_id,
                                    @Field("search_field") String search_field,
                                      @Field("page") int page);

    @FormUrlEncoded
    @POST("malnutrition-checkup/members-list")
    Call<BaseResponse>villageMemberList(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("head_id") String head_id,
                                    @Field("status") String status,//2 for Women & 1 for Child
                                      @Field("page") String page);


    @FormUrlEncoded
    @POST("family/member/list")
    Call<FamilyMembersModelResponse>villageFamilyMembersList(@Field("connection_id") String connectionId,
                                                             @Field("auth_code")String authCode,
                                                             @Field("village_id") String village_id,
                                                             @Field("head_id")String  head_id,
                                                             @Field("status") String  status);



    @FormUrlEncoded
    @POST("malnutrition-checkup/child-women-list")
    Call<FamilyMembersModelResponse>childWomenList(@Field("connection_id") String connectionId,
                                                             @Field("auth_code")String authCode,
                                                             @Field("village_id") String village_id,
                                                             @Field("head_id")String  head_id,
                                                             @Field("page")String  page,
                                                             @Field("status") String  status);


    //STATUS  - 1:Member listing with head included , 2:Member listing with head excluded


    @FormUrlEncoded
    @POST("village/listing")
    Call<BaseResponse>villageList(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode);


    @FormUrlEncoded
    @POST("task-management/task-list")
    Call<BaseResponse>taskList(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode);

    @FormUrlEncoded
    @POST("task-management/task-detail")
    Call<BaseResponse>taskDetails(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                               @Field("task_id")String  task_id);


    @FormUrlEncoded
    @POST("task-management/task-status-update")
    Call<BaseResponse>taskUpdate(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("task_id")String task_id,
                               @Field("task_status")String  task_status);






    @FormUrlEncoded
    @POST("get_list")
    Call<GetAllListResponse>occupationList(@Field("connection_id") String connectionId,
                                           @Field("auth_code")String authCode,
                                           @Field("language")String language);



    @FormUrlEncoded
    @POST("family/head/add")
    Call<BaseResponse>addFamilyHeadNew(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("status") String status,
                                    @Field("name") String head_name,
                                    @Field("gender") String gender,
                                    @Field("age") String age,
                                    @Field("phone_num") String phone_num,
                                    @Field("occupation") String occupation,
                                    @Field("address") String address,
                                    @Field("latitude") String latitude,
                                    @Field("longitude") String longitude,
                                    @Field("aadhar_num") String adhar_no,
                                    @Field("image") String adhar_photo,
                                       @Field("village_id") String village_id,
                                    @Field("head_id") String headId);


    @FormUrlEncoded
    @POST("family/member/add")
    Call<BaseResponse>addFamilyMemberNew(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                         @Field("head_id")String head_id,
                                         @Field("status")String status,
                                    @Field("head_code")String head_code,
                                   @Field("name") String member_name,
                                    @Field("marital_status")String marital_status,
                                    @Field("gender") String gender,
                                    @Field("age") String age,
                                    @Field("occupation") String occupation,
                                    @Field("relation_with_head") String relation_with_head,
//                                    @Field("address") String address,
//                                    @Field("latitude") String latitude,
//                                    @Field("longitude") String longitude,
                                    @Field("aadhar_num") String adhar_no,
                                    @Field("is_pregnant") String is_pregnant,
                                    @Field("village_id") String village_id,
                                    @Field("member_id") String member_id,
                                    @Field("image") String adhar_photo);


    @FormUrlEncoded
    @POST("family/head/details")
    Call<BaseResponse>getFamilyHead(@Field("connection_id") String connectionId,
                                         @Field("auth_code")String authCode,
                                         @Field("head_id")String head_id);

    @FormUrlEncoded
    @POST("family/member/add")
    Call<BaseResponse>getFamilyMember(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("head_id")String head_id,
                                    @Field("head_id")String member_id);

    @FormUrlEncoded
    @POST("personal/hygiene/list")
    Call<BaseResponse>personalHygineList(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("village_id")String village_id);

    @FormUrlEncoded
    @POST("personal/hygiene/children")
    Call<BaseResponse>personalHygineChild(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("head_id")String head_id,
                                          @Field("page")int page);

    @FormUrlEncoded
    @POST("personal/hygiene/add")
    Call<BaseResponse>addHygineChild(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("head_id")String head_id,
                                    @Field("village_id")String village_id,
                                    @Field("member_id")String member_id,
                                    @Field("nail_check")String nail_check,
                                    @Field("health_check")String health_check,
                                    @Field("health_image")String health_image,
                                    @Field("inspection_date")String inspection_date,
                                    @Field("status")String status,
                                    @Field("hygiene_id")String hygiene_id,
                                    @Field("nail_image")String nail_image);


    @FormUrlEncoded
    @POST("personal/hygiene/detail")
    Call<BaseResponse>viewDetailsHygineCheckup(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("hygiene_id")String hygiene_id);


    @FormUrlEncoded
    @POST("social-sanitation/list")
    Call<BaseResponse>socialSanitisationList(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("status")String status,//0,1
                                    @Field("village_id")String village_id);


    @FormUrlEncoded
    @POST("social-sanitation/detail")
    Call<SocialSanitisationDetailResponse>socialSanitisationDetails(@Field("connection_id") String connectionId,
                                                                    @Field("auth_code")String authCode,
                                                                    @Field("sanitation_id")String sanitation_id);


    @FormUrlEncoded
    @POST("gkv-report/in")
    Call<BaseResponse>gkvVillage(@Field("connection_id") String connectionId,
                                                          @Field("auth_code")String authCode);

   @FormUrlEncoded
    @POST("gkv-report/generate")
    Call<BaseResponse>gkvReport(@Field("connection_id") String connectionId,
                                                          @Field("auth_code")String authCode,
                                                          @Field("village_id")String village_id,//String village_id,String month,String year
                                                          @Field("month")String month,
                                                          @Field("year")String year);
    @GET
    Call<ResponseBody> downloadInvoice(@Url String invoiceUrl);


    @FormUrlEncoded
    @POST("social-sanitation/add")
    Call<SocialSanitisationModel>socialSanitisationAdd(@Field("connection_id") String connectionId,
                                                       @Field("auth_code")String authCode,
                                                       @Field("village_id")String village_id,
                                                       @Field("head_id")String head_id,
                                                       @Field("soakpit_status")String soakpit_status,
                                                       @Field("soakpit_image")String soakpit_image,
                                                       @Field("compost_status")String compost_status,
                                                       @Field("toilet_status")String toilet_status,
                                                       @Field("water_treatment")String water_treatment,
                                                       @Field("toilet_image")String toilet_image,
                                                       @Field("wall_writing")String wall_writing,
                                                       @Field("people_use_toilet")String people_use_toilet,
                                                       @Field("medicine_plantation")String medicine_plantation,
                                                       @Field("nutrition_plantation")String nutrition_plantation,
                                                       @Field("filter_uses")String filter_uses, //
                                                       @Field("tree_planting")String tree_planting,
                                                       @Field("wall_writing_photo")String wall_writing_photo,
                                                       @Field("people_use_toilet_photo")String people_use_toilet_photo,
                                                       @Field("medicine_plantation_photo")String medicine_plantation_photo,
                                                       @Field("nutrition_plantation_photo")String nutrition_plantation_photo,
                                                       @Field("filter_uses_photo")String filter_uses_photo,
                                                       @Field("tree_planting_photo")String tree_planting_photo,

                                                       @Field("water_treatment_image")String water_treatment_image,
                                                       @Field("composit_image")String composit_image,
                                                       @Field("inspection_date")String inspection_date,
                                                       @Field("sanitation_id")String sanitation_id,
                                                       @Field("status")String status);


    @FormUrlEncoded
    @POST("disease-prevention/list")
    Call<BaseResponse>diseasePreventionList(@Field("connection_id") String connectionId,
                                                @Field("auth_code")String authCode,
                                                @Field("page")int page,
                                            @Field("search_village")String search_village,
                                            @Field("search_month")String search_month);


    @FormUrlEncoded
    @POST("disease-prevention/add")
    Call<BaseResponse>diseasePreventionAdd(@Field("connection_id") String connectionId,
                                                @Field("auth_code")String authCode,
                                                @Field("village_id")String village_id,
                                                @Field("inspection_date")String inspection_date,
                                                @Field("cleanliness")String cleanliness,
                                                @Field("malaria")String malaria,
                                                @Field("nutrition")String nutrition,
                                                @Field("vaccination")String vaccination,
                                                @Field("maternal_safety")String maternal_safety,
                                                @Field("child_safety")String child_safety,
                                                @Field("other")String other,
                                                @Field("family_present")String family_present,
                                                @Field("male_present")String male_present,
                                                @Field("female_present")String female_present,
                                                @Field("children_present")String children_present,
                                                @Field("total_present")String total_present);

    @FormUrlEncoded
    @POST("anemia-checkup/list")
    Call<BaseResponse>anemiaCheckupList(@Field("connection_id") String connectionId,
                                       @Field("auth_code")String authCode,
                                       @Field("village_id")String village_id);


    @FormUrlEncoded
    @POST("anemia-checkup/detail")
    Call<BaseResponse>anemiaCheckupDetails(@Field("connection_id") String connectionId,
                                       @Field("auth_code")String authCode,
                                       @Field("anemia_checkup_id")String anemia_checkup_id);



    @FormUrlEncoded
    @POST("anemia-checkup/add")
    Call<BaseResponse>anemiaCheckupAdd(@Field("connection_id") String connectionId,
                                       @Field("auth_code")String authCode,
                                       @Field("village_id")String village_id,
                                       @Field("head_id")String head_id,
                                       @Field("member_id")String member_id,
                                       @Field("inspection_date")String inspection_date,
                                       @Field("is_pregnant")String is_pregnant,
                                       @Field("anemia_test")String anemia_test,
                                       @Field("is_anemic")String is_anemic,
                                       @Field("anemia_type")String anemia_type,
                                       @Field("status")String status,
                                       @Field("age")String age,
                                       @Field("anemic_id")String anemic_id);



    @FormUrlEncoded
    @POST("malnutrition-checkup/list")
    Call<BaseResponse>malnutrionCheckupList(@Field("connection_id") String connectionId,
                                        @Field("auth_code")String authCode,
                                        @Field("village_id")String village_id,
                                            @Field("page")String page);
    @FormUrlEncoded
    @POST("malnutrition-checkup/detail")
    Call<BaseResponse>malnutrionCheckupDetails(@Field("connection_id") String connectionId,
                                            @Field("auth_code")String authCode,
                                            @Field("malnutrition_checkup_id")String malnutrition_checkup_id);

    @FormUrlEncoded
    @POST("malnutrition-checkup/add")
    Call<BaseResponse>malnutrionAdd(@Field("connection_id") String connectionId,
                                            @Field("auth_code")String authCode,
                                            @Field("status")String status,
                                            @Field("age")String age,
                                            @Field("gender")String gender,
                                            @Field("village_id")String village_id,
                                            @Field("head_id")String head_id,
                                            @Field("member_id")String member_id,
                                            @Field("inspection_date")String inspection_date,
                                            @Field("checkup")String checkup,
                                            @Field("checkup_id")String checkup_id,
                                            @Field("mid_arm_measurement")String mid_arm_measurement);



    @FormUrlEncoded
    @POST("herbal-remedies/ayurvedic/list")
    Call<HerbalRemediesResponse>ayurvedicTreatment(@Field("connection_id") String connectionId,
                                                   @Field("auth_code")String authCode,
                                                   @Field("page")String page);
    @FormUrlEncoded
    @POST("family-visit/list")
    Call<BaseResponse>familyvisitlist(@Field("connection_id") String connectionId,
                                                   @Field("auth_code")String authCode,
                                                   @Field("page")String page);



    @FormUrlEncoded
    @POST("herbal-remedies/ayurvedic/detail")
    Call<HerbalRemediesResponse>ayurvedicTreatmentDetail(@Field("connection_id") String connectionId,
                                                   @Field("auth_code")String authCode,
                                                   @Field("treatment_id")String treatment_id);

    @FormUrlEncoded
    @POST("herbal-remedies/ayurvedic/add")
    Call<HerbalRemediesResponse>ayurvedicTreatmentadd(@Field("connection_id") String connectionId,
                                                   @Field("auth_code")String authCode,
                                                   @Field("village_id")String village_id,
                                                   @Field("camp_month")String camp_month,
                                                   @Field("male")String male,
                                                   @Field("female")String female,
                                                   @Field("children")String children,
                                                   @Field("total")String total,
                                                   @Field("treatment_type")String treatment_type,
                                                   @Field("total_cured")String total_cured,
                                                   @Field("status")String status,
                                                      @Field("herbal_id")String herbal_id);

    @FormUrlEncoded
    @POST("family-visit/add")
    Call<BaseResponse>familyVisitadd(@Field("connection_id") String connectionId,
                                                   @Field("auth_code")String authCode,
                                                   @Field("village_id")String village_id,
                                                   @Field("inspection_date")String camp_month,
                                                   @Field("male")String male,
                                                   @Field("female")String female,
                                                   @Field("children")String children,
                                                   @Field("total")String total,
                                                   @Field("family")String family,
                                                   @Field("status")String status,
                                               @Field("subject")String subject,
                                               @Field("headId")String headId,

                                               @Field("visit_id")String visit_id);


    @FormUrlEncoded
    @POST("herbal-remedies/medicine-garden/list")
    Call<BaseResponse>medicineGardenList(@Field("connection_id") String connectionId,
                                         @Field("auth_code")String authCode,
                                         @Field("village_id")String village_id,
                                         @Field("page")String page);
    @FormUrlEncoded
    @POST("herbal-remedies/medicine-garden/single-family-garden-list")
    Call<BaseResponse>singlemedicineGardenList(@Field("connection_id") String connectionId,
                                         @Field("auth_code")String authCode,
                                         @Field("head_id")String head_id,
                                         @Field("page")String page);
    @FormUrlEncoded
    @POST("herbal-remedies/medicine-garden/add")
    Call<BaseResponse>medicineGardenAdd(@Field("connection_id") String connectionId,
                                         @Field("auth_code")String authCode,
                                         @Field("status")String status,
                                         @Field("village_id")String village_id,
                                         @Field("head_id")String head_id,
                                         @Field("plant_id")String plant_id,
                                         @Field("plant_count")String plant_count,
                                         @Field("date")String date,
                                         @Field("plant_image")String plant_image,
                                         @Field("garden_id")String garden_id);



    @FormUrlEncoded
    @POST("herbal-remedies/van-aushadhi/patient-list")
    Call<BaseResponse>vanAushadhiList(@Field("connection_id") String connectionId,
                                         @Field("auth_code")String authCode,
                                         @Field("village_id")String village_id,
                                         @Field("status")String status);

    @FormUrlEncoded
    @POST("herbal-remedies/van-aushadhi/patient-detail")
    Call<VanAushadhiDeialResponse>vanAushadhiDetails(@Field("connection_id") String connectionId,
                                                     @Field("auth_code")String authCode,

                                                     @Field("patient_id")String patient_id);

    @FormUrlEncoded
    @POST("herbal-remedies/van-aushadhi/patient-add")
    Call<VanAushadhiDeialResponse>vanAushadhiAdd(@Field("connection_id") String connectionId,
                                         @Field("auth_code")String authCode,
                                         @Field("village_id")String village_id,
                                         @Field("date")String date,
                                         @Field("head_id")String head_id,
                                         @Field("member_name")String member_name,
                                         @Field("guest_patient_name")String guest_patient_name,
                                         @Field("pat_phone")String pat_phone,
                                         @Field("age")String age,
//                                         @Field("gender")String gender,
                                         @Field("problem")String problem,
                                         @Field("prescription")String prescription,
                                         @Field("fees")String fees,
                                         @Field("treatment_status")String treatment_status,
                                         @Field("status")String status,
                                         @Field("aushadhi_id")String patient_id);



    @FormUrlEncoded
    @POST("first-aid/emergency-handling/list")
    Call<BaseResponse>firstAidList(@Field("connection_id") String connectionId,
                                      @Field("auth_code")String authCode,
                                      @Field("village_id")String village_id);

    @FormUrlEncoded
    @POST("herbal-remedies/van-aushadhi/patient-detail")
    Call<BaseResponse>firstAidDetails(@Field("connection_id") String connectionId,
                                         @Field("auth_code")String authCode,
                                         @Field("patient_id")String patient_id);

    @FormUrlEncoded
    @POST("first-aid/emergency-handling")
    Call<BaseResponse>firstAidAdd(@Field("connection_id") String connectionId,
                                     @Field("auth_code")String authCode,
                                     @Field("village_id")String village_id,
                                     @Field("head_id")String head_id,
                                     @Field("member_name")String member_name,
                                     @Field("guest_patient_name")String guest_patient_name,
                                     @Field("date")String date,
                                     @Field("cuts")String cuts,
                                     @Field("heart_attack")String heart_attacks,
                                     @Field("unconscious")String unconscious,
                                     @Field("snake_bites")String snake_bites,
                                     @Field("other")String other,
                                     @Field("training")String training,
                                     @Field("record")String record,
                                     @Field("remarks")String remarks,
                                     @Field("eh_id")String eh_id,
                                     @Field("status")String status);


    @FormUrlEncoded
    @POST("samiti-meeting/list")
    Call<BaseResponse>samitiList(@Field("connection_id") String connectionId,
                                   @Field("auth_code")String authCode);

    @FormUrlEncoded
    @POST("samiti-meeting/detail")
    Call<BaseResponse>samitiListDeatils(@Field("connection_id") String connectionId,
                                 @Field("auth_code")String authCode,
                                 @Field("meeting_id")String meeting_id);

    @FormUrlEncoded
    @POST("image_upload_api")
    Call<BaseResponse>uploadImage(@Field("folder_path") String folder_path,
                                 @Field("image")String image);



    @FormUrlEncoded
    @POST("samiti-meeting")
    Call<BaseResponse>samitiListadd(@Field("connection_id") String connectionId,
                                        @Field("auth_code")String authCode,
                                        @Field("status")String status,
                                        @Field("samiti_id")String samiti_id,
                                        @Field("village_id")String village_id,
                                        @Field("date")String date,
                                        @Field("samiti_member")String samiti_member,
                                        @Field("sevika_member")String sevika_member,
                                        @Field("sanyojika")String sanyojika,
                                        @Field("sevavriti")String sevavriti,
                                        @Field("sanch_coordinator")String sanch_coordinator,
                                        @Field("total")String total,
                                        @Field("image")String image,
                                        @Field("remarks")String remarks);


    @FormUrlEncoded
    @POST("eye-screening/list")
    Call<BaseResponse>eyeScreeningList(@Field("connection_id") String connectionId,
                                        @Field("auth_code")String authCode,
                                        @Field("village_id")String village_id,
                                        @Field("status")String flag);

    @FormUrlEncoded
    @POST("eye-screening/add")
    Call<BaseResponse>eyeScreeningAdd(@Field("connection_id") String connectionId,
                                    @Field("auth_code")String authCode,
                                    @Field("status")String status,
                                    @Field("village_id")String village_id,
                                    @Field("patient_id")String patient_id,
                                      @Field("member_id")String member_id,
                                      @Field("head_id")String head_id,
                                      @Field("guest_patient_name")String guest_patient_name,//String guest_patient_name,String member_name,String patient_mobile,String gender,
                                      @Field("member_name")String member_name,
                                      @Field("patient_mobile")String patient_mobile,
                                      @Field("gender")String gender,
                                    @Field("test_date")String test_date,
                                    @Field("vision_defect")String vision_defect,
                                    @Field("age")String age,
                                    @Field("es_id")String es_id,
                                    @Field("remarks")String remarks);
    //village_id:1
    //head_id:1
    ////member_id:
    //test_date:2022-06-18
    //vision_defect:1
    //age:26
    //remarks:far eye problem
    //status:0
    ////es_id:11

    @FormUrlEncoded
    @POST("eye-van/list")
    Call<BaseResponse>eyeVanList(@Field("connection_id") String connectionId,
                                       @Field("auth_code")String authCode,
                                       @Field("village_id")String village_id,
                                       @Field("status")String flag);

    @FormUrlEncoded
    @POST("eye-van/update")
    Call<BaseResponse>eyeVanAdd(@Field("connection_id") String connectionId,
                                      @Field("auth_code")String authCode,
                                      @Field("status")String status,
                                      @Field("eap_id")String eap_id,
                                      @Field("test_date")String test_date,
                                      @Field("referred_hospital")String referred_hospital,
                                      @Field("glasses_fee")String glasses_fee,
                                      @Field("glasses_issue")String glasses_issue,
                                      @Field("medicine_issue")String medicine_issue,
                                      @Field("medicine_fee")String medicine_fee,
                                      @Field("problem_code")String problem_code,
                                      @Field("r_dist_sph")String r_dist_sph,
                                      @Field("r_dist_cyl")String r_dist_cyl,
                                      @Field("r_dist_axis")String r_dist_axis,
                                      @Field("l_dist_sph")String l_dist_sph,
                                      @Field("l_dist_cyl")String l_dist_cyl,
                                      @Field("l_dist_axis")String l_dist_axis,
                                      @Field("r_reading_sph")String r_reading_sph,
                                      @Field("r_reading_cyl")String r_reading_cyl,
                                      @Field("r_reading_axis")String r_reading_axis,
                                      @Field("l_reading_sph")String l_reading_sph,
                                      @Field("l_reading_cyl")String l_reading_cyl,
                                      @Field("l_reading_axis")String l_reading_axis,
                                      @Field("right_power")String right_power,
                                      @Field("left_power")String left_power,
                                      @Field("photo")String photo);


    @FormUrlEncoded
    @POST("arogya-poster/list")
    Call<BaseResponse>posterDisplayList(@Field("connection_id") String connectionId,
                                       @Field("auth_code")String authCode);

    @FormUrlEncoded
    @POST("arogya-poster/detail")
    Call<BaseResponse>posterDisplayDetails(@Field("connection_id") String connectionId,
                                       @Field("auth_code")String authCode,
                                         @Field("poster_id")String poster_id);

    @FormUrlEncoded
    @POST("arogya-poster/add")
    Call<BaseResponse>posterDisplayAdd(@Field("connection_id") String connectionId,
                                       @Field("auth_code")String authCode,
                                        @Field("village_id")String village_id,
                                        @Field("arogya_topic")String arogya_topic,
                                        @Field("week")String week,
                                        @Field("poster_count")String poster_count,
                                        @Field("total_present")String total_present,
                                        @Field("posters") String posters,
                                        @Field("status")String status,
                                       @Field("poster_display_id")String poster_display_id);




    @FormUrlEncoded
    @POST("village-visit/list")
    Call<BaseResponse>villagevisitList(@Field("connection_id") String connectionId,
                                          @Field("auth_code")String authCode);

    @FormUrlEncoded
    @POST("village-visit/detail")
    Call<BaseResponse>getVillagevisitDetails(@Field("connection_id") String connectionId,
                                          @Field("auth_code")String authCode,
                                             @Field("visit_id")String visit_id);

    @FormUrlEncoded
    @POST("village-visit/add")
    Call<BaseResponse>addVillagevisit(@Field("connection_id") String connectionId,
                                          @Field("auth_code")String authCode,
                                          @Field("village_id")String village_id,
                                          @Field("sanyojika_name")String sanyojika_name,
                                          @Field("visit")String visit,
                                          @Field("date")String date,
                                          @Field("responsibility")String responsibility,
                                          @Field("purpose")String purpose,
                                          @Field("status")String status,
                                          @Field("visit_id")String visit_id);

    @FormUrlEncoded
    @POST("arogya-camp/list")
    Call<BaseResponse>arogyaCampList(@Field("connection_id") String connectionId,
                                       @Field("auth_code")String authCode,
                                     @Field("village_id")String village_id);


    @FormUrlEncoded
    @POST("arogya-camp/detail")
    Call<BaseResponse>arogyaCampDetails(@Field("connection_id") String connectionId,
                                       @Field("auth_code")String authCode,
                                       @Field("camp_id")String camp_id);

    @FormUrlEncoded
    @POST("arogya-camp/add")
    Call<BaseResponse>addArogyaCampDetails(@Field("connection_id") String connectionId,
                                        @Field("auth_code")String authCode,
                                        @Field("status")String status,
                                        @Field("arogya_camp_id")String arogya_camp_id,
                                        @Field("village_id")String village_id,
                                        @Field("date")String date,
                                        @Field("patient_boy")String patient_boy,
                                        @Field("patient_girl")String patient_girl,
                                        @Field("patient_male")String patient_male,
                                        @Field("patient_female")String patient_female,
                                        @Field("total_children")String total_children,
                                        @Field("total")String total,
                                        @Field("doctor")String doctor,
                                        @Field("ref_pvt")String ref_pvt,
                                        @Field("ref_govt")String ref_govt,
                                        @Field("camp_photo")String camp_photo);




    @FormUrlEncoded
    @POST("jagran-program/list")
    Call<BaseResponse>jagaranList(@Field("connection_id") String connectionId,
                                     @Field("auth_code")String authCode,
                                  @Field("village_id") String village_id);


    @FormUrlEncoded
    @POST("jagran-program/detail")
    Call<BaseResponse>jagaranDetails(@Field("connection_id") String connectionId,
                                        @Field("auth_code")String authCode,
                                        @Field("jagran_id")String jagaran_id);
    @FormUrlEncoded
    @POST("jagran-program/add")
    Call<BaseResponse>addJagaranDetails(@Field("connection_id") String connectionId,
                                     @Field("auth_code")String authCode,
                                     @Field("status")String status,
                                     @Field("jagran_id")String jagran_id,
                                     @Field("village_id")String village_id,
                                     @Field("program_date")String program_date,
                                     @Field("program_data")String program_data);


    @FormUrlEncoded
    @POST("inspection-stay/list")
    Call<BaseResponse>inspectionList(@Field("connection_id") String connectionId,
                                     @Field("auth_code")String authCode);


    @FormUrlEncoded
    @POST("inspection-stay/detail")
    Call<BaseResponse>inspectionDetails(@Field("connection_id") String connectionId,
                                        @Field("auth_code")String authCode,
                                        @Field("inspection_id")String inspection_id);
    @FormUrlEncoded
    @POST("inspection-stay/add")
    Call<BaseResponse>inspectionAdd(@Field("connection_id") String connectionId,
                                        @Field("auth_code")String authCode,
                                        @Field("sanyojak")String sanyojak,
                                        @Field("inspection_date")String inspection_date,
                                        @Field("vidyalay_grade")String vidyalay_grade,
                                        @Field("jagran_grade")String jagran_grade,
                                        @Field("family_grade")String family_grade,
                                        @Field("poster_grade")String poster_grade,
                                        @Field("vanaushadhi_grade")String vanaushadhi_grade,
                                        @Field("remark")String remark
                                    );

    //sanyojak:Damna
    //inspection_date:2022-01-21
    //vidyalay_grade:A
    //jagran_grade:B
    //family_grade:C
    //poster_grade:A
    //vanaushadhi_grade:A




}
