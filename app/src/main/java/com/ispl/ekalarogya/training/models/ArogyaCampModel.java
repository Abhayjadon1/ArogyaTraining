package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ArogyaCampModel implements Parcelable {
    @SerializedName("sevika_id")String sevika_id;
    @SerializedName("village_id")String village_id;
    @SerializedName("village_name")String village_name;
    @SerializedName("meeting_date")String meeting_date;
    @SerializedName("total_patient")String total_patient;
    @SerializedName("camp_id")String camp_id;
    @SerializedName("village_count")String village_visit_count;
    @SerializedName("sanch_name")String sanch_name;
    @SerializedName("no_of_doctor")String no_of_doctor;

   ////////////////////////////////////// DEtail RESPOSNSE
   @SerializedName("id")String id;
   @SerializedName("boy_patient")String boy_patient;
   @SerializedName("girl_patient")String girl_patient;
   @SerializedName("total_children")String total_children;
   @SerializedName("male_patient")String male_patient;
   @SerializedName("female_patient")String female_patient;
   @SerializedName("referred_pvt")String referred_pvt;
   @SerializedName("referred_govt")String referred_govt;
   @SerializedName("camp_photo")String camp_photo;

    protected ArogyaCampModel(Parcel in) {
        sevika_id = in.readString();
        village_id = in.readString();
        village_name = in.readString();
        meeting_date = in.readString();
        total_patient = in.readString();
        camp_id = in.readString();
        no_of_doctor = in.readString();
        id = in.readString();
        sanch_name = in.readString();
        village_visit_count = in.readString();
        boy_patient = in.readString();
        girl_patient = in.readString();
        total_children = in.readString();
        male_patient = in.readString();
        female_patient = in.readString();
        referred_pvt = in.readString();
        referred_govt = in.readString();
        camp_photo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sevika_id);
        dest.writeString(village_id);
        dest.writeString(village_name);
        dest.writeString(meeting_date);
        dest.writeString(total_patient);
        dest.writeString(camp_id);
        dest.writeString(no_of_doctor);
        dest.writeString(id);
        dest.writeString(village_visit_count);
        dest.writeString(sanch_name);
        dest.writeString(boy_patient);
        dest.writeString(girl_patient);
        dest.writeString(total_children);
        dest.writeString(male_patient);
        dest.writeString(female_patient);
        dest.writeString(referred_pvt);
        dest.writeString(referred_govt);
        dest.writeString(camp_photo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArogyaCampModel> CREATOR = new Creator<ArogyaCampModel>() {
        @Override
        public ArogyaCampModel createFromParcel(Parcel in) {
            return new ArogyaCampModel(in);
        }

        @Override
        public ArogyaCampModel[] newArray(int size) {
            return new ArogyaCampModel[size];
        }
    };

    public String getSevika_id() {
        return sevika_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public String getMeeting_date() {
        return meeting_date;
    }

    public String getTotal_patient() {
        return total_patient;
    }

    public String getCamp_id() {
        return camp_id;
    }

    public String getNo_of_doctor() {
        return no_of_doctor;
    }

    public String getId() {
        return id;
    }

    public String getVillage_visit_count() {
        return village_visit_count;
    }

    public String getSanch_name() {
        return sanch_name;
    }

    public String getBoy_patient() {
        return boy_patient;
    }

    public String getGirl_patient() {
        return girl_patient;
    }

    public String getTotal_children() {
        return total_children;
    }

    public String getMale_patient() {
        return male_patient;
    }

    public String getFemale_patient() {
        return female_patient;
    }

    public String getReferred_pvt() {
        return referred_pvt;
    }

    public String getReferred_govt() {
        return referred_govt;
    }

    public String getCamp_photo() {
        return camp_photo;
    }

    @Override
    public String toString() {
        return "ArogyaCampModel{" +
                "sevika_id='" + sevika_id + '\'' +
                ", village_id='" + village_id + '\'' +
                ", village_name='" + village_name + '\'' +
                ", meeting_date='" + meeting_date + '\'' +
                ", total_patient='" + total_patient + '\'' +
                ", camp_id='" + camp_id + '\'' +
                ", no_of_doctor='" + no_of_doctor + '\'' +
                ", id='" + id + '\'' +
                ", sanch_name='" + sanch_name + '\'' +
                ", village_visit_count='" + village_visit_count + '\'' +
                ", boy_patient='" + boy_patient + '\'' +
                ", girl_patient='" + girl_patient + '\'' +
                ", total_children='" + total_children + '\'' +
                ", male_patient='" + male_patient + '\'' +
                ", female_patient='" + female_patient + '\'' +
                ", referred_pvt='" + referred_pvt + '\'' +
                ", referred_govt='" + referred_govt + '\'' +
                ", camp_photo='" + camp_photo + '\'' +
                '}';
    }
}
