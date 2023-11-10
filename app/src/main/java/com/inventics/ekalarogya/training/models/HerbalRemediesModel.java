package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class HerbalRemediesModel implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("village_id") String village_id;
    @SerializedName("village_name") String village_name;
    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("camp_month") String camp_month;
    @SerializedName("male_present") String male_present;
    @SerializedName("female_present") String female_present;
    @SerializedName("children_present") String children_present;
    @SerializedName("total_present") String total_present;
    @SerializedName("treatment_type") String treatment_type;
    @SerializedName("total_cured") String total_cured;


    protected HerbalRemediesModel(Parcel in) {
        id = in.readString();
        village_id = in.readString();
        village_name = in.readString();
        sevika_id = in.readString();
        camp_month = in.readString();
        male_present = in.readString();
        female_present = in.readString();
        children_present = in.readString();
        total_present = in.readString();
        treatment_type = in.readString();
        total_cured = in.readString();
    }

    public static final Creator<HerbalRemediesModel> CREATOR = new Creator<HerbalRemediesModel>() {
        @Override
        public HerbalRemediesModel createFromParcel(Parcel in) {
            return new HerbalRemediesModel(in);
        }

        @Override
        public HerbalRemediesModel[] newArray(int size) {
            return new HerbalRemediesModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public String getSevika_id() {
        return sevika_id;
    }

    public String getCamp_month() {
        return camp_month;
    }

    public String getMale_present() {
        return male_present;
    }

    public String getFemale_present() {
        return female_present;
    }

    public String getChildren_present() {
        return children_present;
    }

    public String getTotal_present() {
        return total_present;
    }

    public String getTreatment_type() {
        return treatment_type;
    }

    public String getTotal_cured() {
        return total_cured;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(village_id);
        parcel.writeString(village_name);
        parcel.writeString(sevika_id);
        parcel.writeString(camp_month);
        parcel.writeString(male_present);
        parcel.writeString(female_present);
        parcel.writeString(children_present);
        parcel.writeString(total_present);
        parcel.writeString(treatment_type);
        parcel.writeString(total_cured);
    }

    @Override
    public String toString() {
        return "HerbalRemediesModel{" +
                "id='" + id + '\'' +
                ", village_id='" + village_id + '\'' +
                ", village_name='" + village_name + '\'' +
                ", sevika_id='" + sevika_id + '\'' +
                ", camp_month='" + camp_month + '\'' +
                ", male_present='" + male_present + '\'' +
                ", female_present='" + female_present + '\'' +
                ", children_present='" + children_present + '\'' +
                ", total_present='" + total_present + '\'' +
                ", treatment_type='" + treatment_type + '\'' +
                ", total_cured='" + total_cured + '\'' +
                '}';
    }
}
