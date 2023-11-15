package com.ispl.ekalarogya.training.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sonu on 17:21, 2019-06-12
 * Copyright (c) 2019 . All rights reserved.
 */
public class Service implements Parcelable {
    @SerializedName("name")
    private String serviceName;
    @SerializedName("id")
    private long serviceId;

    public Service() {
    }

    protected Service(Parcel in) {
        serviceName = in.readString();
        serviceId = in.readLong();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    public String getServiceName() {
        return serviceName;
    }

    public long getServiceId() {
        return serviceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(serviceName);
        parcel.writeLong(serviceId);
    }
}
