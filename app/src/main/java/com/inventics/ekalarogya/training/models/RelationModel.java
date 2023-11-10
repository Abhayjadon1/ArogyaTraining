package com.inventics.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

public class RelationModel {
    @SerializedName("id") String id;
    @SerializedName("relations") String relation;
    @SerializedName("code") String code;

    public String getId() {
        return id;
    }

    public String getRelation() {
        return relation;
    }

    public String getCode() {
        return code;
    }
}
