package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VillageVisitModel implements Parcelable {

    @SerializedName("id") String id;
    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("village_id") String village_id;
    @SerializedName("village_name") String village_name;
    @SerializedName("sanyojika_name") String sanyojika_name;
    @SerializedName("visit") String visit;
    @SerializedName("date") String date;
    @SerializedName("responsibility") String responsibility;
    @SerializedName("purpose") String purpose;

    protected VillageVisitModel(Parcel in) {
        id = in.readString();
        sevika_id = in.readString();
        village_id = in.readString();
        sanyojika_name = in.readString();
        visit = in.readString();
        date = in.readString();
        responsibility = in.readString();
        purpose = in.readString();
        village_name=in.readString();
    }

    public static final Creator<VillageVisitModel> CREATOR = new Creator<VillageVisitModel>() {
        @Override
        public VillageVisitModel createFromParcel(Parcel in) {
            return new VillageVisitModel(in);
        }

        @Override
        public VillageVisitModel[] newArray(int size) {
            return new VillageVisitModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getSevika_id() {
        return sevika_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getSanyojika_name() {
        return sanyojika_name;
    }

    public String getVisit() {
        return visit;
    }

    public String getDate() {
        return date;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getVillage_name() {
        return village_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(sevika_id);
        parcel.writeString(village_id);
        parcel.writeString(sanyojika_name);
        parcel.writeString(visit);
        parcel.writeString(date);
        parcel.writeString(responsibility);
        parcel.writeString(purpose);
        parcel.writeString(village_name);
    }

    @Override
    public String toString() {
        return "VillageVisitModel{" +
                "id='" + id + '\'' +
                ", sevika_id='" + sevika_id + '\'' +
                ", village_id='" + village_id + '\'' +
                ", village_name='" + village_name + '\'' +
                ", sanyojika_name='" + sanyojika_name + '\'' +
                ", visit='" + visit + '\'' +
                ", date='" + date + '\'' +
                ", responsibility='" + responsibility + '\'' +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
