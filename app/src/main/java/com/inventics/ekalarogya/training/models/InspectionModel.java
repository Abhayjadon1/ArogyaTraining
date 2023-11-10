package com.inventics.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

public class InspectionModel {
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "InspectionModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
