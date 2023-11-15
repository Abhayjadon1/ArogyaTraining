package com.ispl.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

public class AddFamilyHeadModel {
    @SerializedName("id") String id;
    @SerializedName("head_name") String head_name;
    @SerializedName("gender") String gender;
    @SerializedName("age") String age;
    @SerializedName("occupation") String occupation;
    @SerializedName("address") String address;
    @SerializedName("latitude") String latitude;
    @SerializedName("longitude") String longitude;
    @SerializedName("adhar_no") String adhar_no;
    @SerializedName("adhar_photo") String adhar_photo;
    @SerializedName("village_id") String village_id;
    @SerializedName("submitted_by") String submitted_by;


    public String getId() {
        return id;
    }

    public String getHead_name() {
        return head_name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
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

    public String getAdhar_no() {
        return adhar_no;
    }

    public String getAdhar_photo() {
        return adhar_photo;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getSubmitted_by() {
        return submitted_by;
    }


}
