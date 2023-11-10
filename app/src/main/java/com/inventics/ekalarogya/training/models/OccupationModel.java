package com.inventics.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

public class OccupationModel {
    @SerializedName("id")String id;
    @SerializedName("occupation")String occupation;
    @SerializedName("code")String code;

    public String getId() {
        return id;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "OccupationModel{" +
                "id='" + id + '\'' +
                ", occupation='" + occupation + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
