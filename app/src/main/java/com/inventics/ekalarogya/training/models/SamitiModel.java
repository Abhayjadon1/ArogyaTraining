package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SamitiModel implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("village_id") String village_id;
    @SerializedName("village") String village;
    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("meeting_date") String meeting_date;
    @SerializedName("total") String total;

    protected SamitiModel(Parcel in) {
        id = in.readString();
        village_id = in.readString();
        village = in.readString();
        sevika_id = in.readString();
        meeting_date = in.readString();
        total = in.readString();
    }

    public static final Creator<SamitiModel> CREATOR = new Creator<SamitiModel>() {
        @Override
        public SamitiModel createFromParcel(Parcel in) {
            return new SamitiModel(in);
        }

        @Override
        public SamitiModel[] newArray(int size) {
            return new SamitiModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getVillage() {
        return village;
    }

    public String getSevika_id() {
        return sevika_id;
    }

    public String getMeeting_date() {
        return meeting_date;
    }

    public String getTotal() {
        return total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(village_id);
        parcel.writeString(village);
        parcel.writeString(sevika_id);
        parcel.writeString(meeting_date);
        parcel.writeString(total);
    }
}
