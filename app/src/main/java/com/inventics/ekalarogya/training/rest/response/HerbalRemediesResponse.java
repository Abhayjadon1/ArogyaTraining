package com.inventics.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;
import com.inventics.ekalarogya.training.models.HerbalRemediesModel;

import java.util.List;

public class HerbalRemediesResponse {
    @SerializedName("status")String status;
    @SerializedName("list_name")String list_name;
    @SerializedName("ayurvedic_treatment_list")
    List <HerbalRemediesModel> herbalRemediesModelList;
    @SerializedName("herbal_remedies_detail") String herbal_remedies_detail;
    @SerializedName("message")String message;
    @SerializedName("code")String code;

    public String getStatus() {
        return status;
    }

    public String getList_name() {
        return list_name;
    }

    public List<HerbalRemediesModel> getHerbalRemediesModelList() {
        return herbalRemediesModelList;
    }

    public String getHerbal_remedies_detail() {
        return herbal_remedies_detail;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}

