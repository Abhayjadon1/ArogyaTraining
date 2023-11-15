package com.ispl.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;
import com.ispl.ekalarogya.training.models.SocialSanitisationData;

public class SocialSanitisationDetailResponse {
    @SerializedName("status") String status;
    @SerializedName("list_name") String list_name;
//    @SerializedName("personal_hygiene") String personal_hygiene;
    @SerializedName("social_sanitation")
SocialSanitisationData socialSanitisationData;
    @SerializedName("message") String message;
    @SerializedName("code") String code;

    public String getStatus() {
        return status;
    }

    public String getList_name() {
        return list_name;
    }

//    public String getPersonal_hygiene() {
//        return personal_hygiene;
//    }

    public SocialSanitisationData getSocialSanitisationData() {
        return socialSanitisationData;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }



}
