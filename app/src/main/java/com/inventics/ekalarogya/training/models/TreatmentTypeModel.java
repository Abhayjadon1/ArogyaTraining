package com.inventics.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

public class TreatmentTypeModel {

    @SerializedName("id")String id;
    @SerializedName("title")String title;
    @SerializedName("code")String code;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }
}
