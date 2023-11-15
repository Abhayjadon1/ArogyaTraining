package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SocialSanitisationModel implements Parcelable {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String statusMessage;
    @SerializedName("code")
    private String errorCode;
    @SerializedName("list_name")
    private  String list_name;

    @SerializedName("id") String id;
    @SerializedName("head_id") String head_id;
    @SerializedName("date") String inspection_date;
    @SerializedName("head_name") String head_name;
    @SerializedName("soakpit") String soakpit;
    @SerializedName("compost_pit") String compost_pit;
    @SerializedName("toilet") String toilets;
    @SerializedName("water_treatment") String water_treatment;

    public String getId() {
        return id;
    }

    public String getHead_id() {
        return head_id;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public String getHead_name() {
        return head_name;
    }

    public String getSoakpit() {
        return soakpit;
    }

    public String getCompost_pit() {
        return compost_pit;
    }

    public String getToilets() {
        return toilets;
    }

    public String getWater_treatment() {
        return water_treatment;
    }

    protected SocialSanitisationModel(Parcel in) {
        id = in.readString();
        head_id = in.readString();
        inspection_date = in.readString();
        head_name = in.readString();
        soakpit = in.readString();
        compost_pit = in.readString();
        toilets = in.readString();
        water_treatment = in.readString();
    }

    public static final Creator<SocialSanitisationModel> CREATOR = new Creator<SocialSanitisationModel>() {
        @Override
        public SocialSanitisationModel createFromParcel(Parcel in) {
            return new SocialSanitisationModel(in);
        }

        @Override
        public SocialSanitisationModel[] newArray(int size) {
            return new SocialSanitisationModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(head_id);
        parcel.writeString(inspection_date);
        parcel.writeString(head_name);
        parcel.writeString(soakpit);
        parcel.writeString(compost_pit);
        parcel.writeString(toilets);
        parcel.writeString(water_treatment);
    }

    public String getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getList_name() {
        return list_name;
    }
}
