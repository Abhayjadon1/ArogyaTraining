package com.inventics.ekalarogya.training.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PersonlHygnChildResponse implements Parcelable {
    @SerializedName("id") String id;//id is hygiene id
    @SerializedName("name") String name;
    @SerializedName("age") String age;

    protected PersonlHygnChildResponse(Parcel in) {
        id = in.readString();
        name = in.readString();
        age = in.readString();
    }

    public static final Creator<PersonlHygnChildResponse> CREATOR = new Creator<PersonlHygnChildResponse>() {
        @Override
        public PersonlHygnChildResponse createFromParcel(Parcel in) {
            return new PersonlHygnChildResponse(in);
        }

        @Override
        public PersonlHygnChildResponse[] newArray(int size) {
            return new PersonlHygnChildResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(age);
    }
}
