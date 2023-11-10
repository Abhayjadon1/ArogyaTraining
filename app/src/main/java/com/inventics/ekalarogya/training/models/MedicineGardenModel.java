package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicineGardenModel implements Parcelable {
    @SerializedName("name")String name;
    @SerializedName("head_id")String head_id;
    @SerializedName("village_id")String village_id;
    @SerializedName("id")String id;
    @SerializedName("head_name")String head_name;
    @SerializedName("plants")
    List<PlantModel>plantModelList;
    @SerializedName("month") String month;
    @SerializedName("month_value") String month_value;


    protected MedicineGardenModel(Parcel in) {
        name = in.readString();
        head_id = in.readString();
        village_id = in.readString();
        id = in.readString();
        head_name = in.readString();
        plantModelList = in.createTypedArrayList(PlantModel.CREATOR);
        month = in.readString();
        month_value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(head_id);
        dest.writeString(village_id);
        dest.writeString(id);
        dest.writeString(head_name);
        dest.writeTypedList(plantModelList);
        dest.writeString(month);
        dest.writeString(month_value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MedicineGardenModel> CREATOR = new Creator<MedicineGardenModel>() {
        @Override
        public MedicineGardenModel createFromParcel(Parcel in) {
            return new MedicineGardenModel(in);
        }

        @Override
        public MedicineGardenModel[] newArray(int size) {
            return new MedicineGardenModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getHead_id() {
        return head_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getId() {
        return id;
    }

    public String getHead_name() {
        return head_name;
    }

    public List<PlantModel> getPlantModelList() {
        return plantModelList;
    }

    public String getMonth() {
        return month;
    }

    public String getMonth_value() {
        return month_value;
    }

    @Override
    public String toString() {
        return "MedicineGardenModel{" +
                "name='" + name + '\'' +
                ", head_id='" + head_id + '\'' +
                ", village_id='" + village_id + '\'' +
                ", id='" + id + '\'' +
                ", head_name='" + head_name + '\'' +
                ", plantModelList=" + plantModelList +
                ", month='" + month + '\'' +
                ", month_value='" + month_value + '\'' +
                '}';
    }
}
