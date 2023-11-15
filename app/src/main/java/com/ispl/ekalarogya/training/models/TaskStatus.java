package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TaskStatus  implements Parcelable {
    @SerializedName("id")String id;
    @SerializedName("status_title")String status_title;
    @SerializedName("created_at")String created_at;
    @SerializedName("updated_at")String updated_at;


    protected TaskStatus(Parcel in) {
        id = in.readString();
        status_title = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<TaskStatus> CREATOR = new Creator<TaskStatus>() {
        @Override
        public TaskStatus createFromParcel(Parcel in) {
            return new TaskStatus(in);
        }

        @Override
        public TaskStatus[] newArray(int size) {
            return new TaskStatus[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getStatus_title() {
        return status_title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(status_title);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
    }
}
