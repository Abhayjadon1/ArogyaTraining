package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaceModel implements Parcelable {
    private String placeID;
    private String primaryPlace;
    private String secondaryPlace;

    public PlaceModel() {
    }

    protected PlaceModel(Parcel in) {
        placeID = in.readString();
        primaryPlace = in.readString();
        secondaryPlace = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeID);
        dest.writeString(primaryPlace);
        dest.writeString(secondaryPlace);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaceModel> CREATOR = new Creator<PlaceModel>() {
        @Override
        public PlaceModel createFromParcel(Parcel in) {
            return new PlaceModel(in);
        }

        @Override
        public PlaceModel[] newArray(int size) {
            return new PlaceModel[size];
        }
    };

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getPrimaryPlace() {
        return primaryPlace;
    }

    public void setPrimaryPlace(String primaryPlace) {
        this.primaryPlace = primaryPlace;
    }

    public String getSecondaryPlace() {
        return secondaryPlace;
    }

    public void setSecondaryPlace(String secondaryPlace) {
        this.secondaryPlace = secondaryPlace;
    }

    @Override
    public String toString() {
        return "PlaceModel{" +
                "placeID='" + placeID + '\'' +
                ", primaryPlace='" + primaryPlace + '\'' +
                ", secondaryPlace='" + secondaryPlace + '\'' +
                '}';
    }
}
