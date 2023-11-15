package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VillageListModel implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("sanyojika_name") String sanyojika_name;
    @SerializedName("visit") String visit;
    @SerializedName("date") String date;
    @SerializedName("responsibility") String responsibility;
    @SerializedName("purpose") String purpose;


@SerializedName("village_id") String village_id;
@SerializedName("village_name") String village_name;
@SerializedName("total_family") String total_family;
//@SerializedName("villages") List<VillageFamilyModel> villageFamilyModelList;


    protected VillageListModel(Parcel in) {
        id = in.readString();
        sevika_id = in.readString();
        sanyojika_name = in.readString();
        visit = in.readString();
        date = in.readString();
        responsibility = in.readString();
        purpose = in.readString();
        village_id = in.readString();
        village_name = in.readString();
        total_family = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sevika_id);
        dest.writeString(sanyojika_name);
        dest.writeString(visit);
        dest.writeString(date);
        dest.writeString(responsibility);
        dest.writeString(purpose);
        dest.writeString(village_id);
        dest.writeString(village_name);
        dest.writeString(total_family);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VillageListModel> CREATOR = new Creator<VillageListModel>() {
        @Override
        public VillageListModel createFromParcel(Parcel in) {
            return new VillageListModel(in);
        }

        @Override
        public VillageListModel[] newArray(int size) {
            return new VillageListModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getSevika_id() {
        return sevika_id;
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

    public String getVillage_id() {
        return village_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public String getTotal_family() {
        return total_family;
    }
}
