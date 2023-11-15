package com.ispl.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

public class DashBoardModel {
    @SerializedName("path") String path;
    @SerializedName("id") String id;
    @SerializedName("title") String title;

    public DashBoardModel(String path, String id, String title) {
        this.path = path;
        this.id = id;
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
