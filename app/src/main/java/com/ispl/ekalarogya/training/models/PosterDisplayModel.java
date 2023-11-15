package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PosterDisplayModel implements Parcelable {

    @SerializedName("id")String id;
    @SerializedName("village_id")String village_id;
    @SerializedName("village_name")String village_name;
    @SerializedName("sevika_id")String sevika_id;
    @SerializedName("arogya_topic")String arogya_topic;
    @SerializedName("week")String week;
    @SerializedName("poster_count")String poster_count;
    @SerializedName("total_present")String total_present;
    @SerializedName("posters")
    List<String> posters;


    protected PosterDisplayModel(Parcel in) {
        id = in.readString();
        village_id = in.readString();
        village_name = in.readString();
        sevika_id = in.readString();
        arogya_topic = in.readString();
        week = in.readString();
        poster_count = in.readString();
        total_present = in.readString();
    }

    public static final Creator<PosterDisplayModel> CREATOR = new Creator<PosterDisplayModel>() {
        @Override
        public PosterDisplayModel createFromParcel(Parcel in) {
            return new PosterDisplayModel(in);
        }

        @Override
        public PosterDisplayModel[] newArray(int size) {
            return new PosterDisplayModel[size];
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

    public String getArogya_topic() {
        return arogya_topic;
    }

    public String getWeek() {
        return week;
    }

    public String getPoster_count() {
        return poster_count;
    }

    public String getTotal_present() {
        return total_present;
    }

    public List<String> getPosters() {
        return posters;
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
        parcel.writeString(arogya_topic);
        parcel.writeString(week);
        parcel.writeString(poster_count);
        parcel.writeString(total_present);
    }
}
