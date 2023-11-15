package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FamilyMemberListModel implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("name") String name;
    @SerializedName("member_code") String member_code;
    @SerializedName("family_head") String family_head;

    protected FamilyMemberListModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        family_head = in.readString();
        member_code=in.readString();
    }

    public static final Creator<FamilyMemberListModel> CREATOR = new Creator<FamilyMemberListModel>() {
        @Override
        public FamilyMemberListModel createFromParcel(Parcel in) {
            return new FamilyMemberListModel(in);
        }

        @Override
        public FamilyMemberListModel[] newArray(int size) {
            return new FamilyMemberListModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFamily_head() {
        return family_head;
    }

    public String getMember_code() {
        return member_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(family_head);
        parcel.writeString(member_code);
    }
}
