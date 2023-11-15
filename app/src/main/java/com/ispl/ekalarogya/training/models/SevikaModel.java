package com.ispl.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

public class SevikaModel {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("role_id")
    String role_id;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole_id() {
        return role_id;
    }
}
