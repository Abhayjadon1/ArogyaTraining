package com.inventics.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FamilyMembersModelResponse {
    @SerializedName("status") String status;
    @SerializedName("list_name") String list_name;
    @SerializedName("families")
    List<FamilyMembersModel> familyMemberListModels;


    @SerializedName("message") String message;
    @SerializedName("code") String code;

    public String getStatus() {
        return status;
    }

    public String getList_name() {
        return list_name;
    }

    public List<FamilyMembersModel> getFamilyMemberListModels() {
        return familyMemberListModels;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
