package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VillageFamilyModel implements Parcelable {
    @SerializedName("family_head_id")
    String family_head_id;
    @SerializedName("family_head")
    String family_head;
    @SerializedName("gender")
    String gender;
    @SerializedName("family_member_count")
    String family_member_count;
    @SerializedName("head_code")
    String head_code;
    @SerializedName("village_id")
    String village_id;
    @SerializedName("submitted_by")
    String submitted_by;
    @SerializedName("id")
    String id;
    @SerializedName("age")
    String age;
    @SerializedName("occupation")
    String occupation;


    protected VillageFamilyModel(Parcel in) {
        family_head_id = in.readString();
        family_head = in.readString();
        gender = in.readString();
        family_member_count = in.readString();
        head_code=in.readString();
    }

    public static final Creator<VillageFamilyModel> CREATOR = new Creator<VillageFamilyModel>() {
        @Override
        public VillageFamilyModel createFromParcel(Parcel in) {
            return new VillageFamilyModel(in);
        }

        @Override
        public VillageFamilyModel[] newArray(int size) {
            return new VillageFamilyModel[size];
        }
    };

    public String getFamily_head_id() {
        return family_head_id;
    }

    public String getFamily_head() {
        return family_head;
    }

    public String getGender() {
        return gender;
    }

    public String getFamily_member_count() {
        return family_member_count;
    }

    public String getHead_code() {
        return head_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(family_head_id);
        parcel.writeString(family_head);
        parcel.writeString(gender);
        parcel.writeString(family_member_count);
        parcel.writeString(head_code);
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getSubmitted_by() {
        return submitted_by;
    }

    public String getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public String getOccupation() {
        return occupation;
    }
}
