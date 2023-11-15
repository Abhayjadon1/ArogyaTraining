package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MaritalStatus implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("marital_status") String marital_status;
    @SerializedName("code") String code;

    public MaritalStatus(String id, String marital_status, String code) {
        this.id = id;
        this.marital_status = marital_status;
        this.code = code;
    }

    protected MaritalStatus(Parcel in) {
        id = in.readString();
        marital_status = in.readString();
        code = in.readString();
    }

    public static final Creator<MaritalStatus> CREATOR = new Creator<MaritalStatus>() {
        @Override
        public MaritalStatus createFromParcel(Parcel in) {
            return new MaritalStatus(in);
        }

        @Override
        public MaritalStatus[] newArray(int size) {
            return new MaritalStatus[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "MaritalStatus{" +
                "id='" + id + '\'' +
                ", marital_status='" + marital_status + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(marital_status);
        parcel.writeString(code);
    }
}
