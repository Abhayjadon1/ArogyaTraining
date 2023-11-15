package com.ispl.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;
import com.ispl.ekalarogya.training.models.FamilyMembersModel;

import java.util.List;

public class FamilyHeadDetailsModelResponse {
    @SerializedName("id") String id;
    @SerializedName("family_head") String family_head;
    @SerializedName("gender") String gender;
    @SerializedName("age") String age;
    @SerializedName("occupation") String occupation;
    @SerializedName("address") String address;
    @SerializedName("latitude") String latitude;
    @SerializedName("longitude") String longitude;
    @SerializedName("phone_num") String phone_num;
    @SerializedName("aadhar") String aadhar;
    @SerializedName("aadhar_photo") String aadhar_photo;
    @SerializedName("members")
    List<FamilyMembersModel> familyMembersModelList;
    @SerializedName("total_members_of_family") String total_members_of_family;
    @SerializedName("village_id") String village_id;
    @SerializedName("submitted_by") String submitted_by;


    public String getId() {
        return id;
    }

    public String getFamily_head() {
        return family_head;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getAadhar() {
        return aadhar;
    }

    public List<FamilyMembersModel> getFamilyMembersModelList() {
        return familyMembersModelList;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public String getAadhar_photo() {
        return aadhar_photo;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getSubmitted_by() {
        return submitted_by;
    }

    public String getTotal_members_of_family() {
        return total_members_of_family;
    }
}
