package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MalnutrinCheckupModelResponse implements Parcelable {
    @SerializedName("id")String id;
    @SerializedName("child_id")String child_id;
    @SerializedName("name")String name;
    @SerializedName("date")String date;
    @SerializedName("malnourished")String malnourished;

    protected MalnutrinCheckupModelResponse(Parcel in) {
        id = in.readString();
        child_id = in.readString();
        name = in.readString();
        malnourished = in.readString();
    }

    public static final Creator<MalnutrinCheckupModelResponse> CREATOR = new Creator<MalnutrinCheckupModelResponse>() {
        @Override
        public MalnutrinCheckupModelResponse createFromParcel(Parcel in) {
            return new MalnutrinCheckupModelResponse(in);
        }

        @Override
        public MalnutrinCheckupModelResponse[] newArray(int size) {
            return new MalnutrinCheckupModelResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getChild_id() {
        return child_id;
    }

    public String getName() {
        return name;
    }

    public String getMalnourished() {
        return malnourished;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(child_id);
        parcel.writeString(name);
        parcel.writeString(malnourished);
    }
}
