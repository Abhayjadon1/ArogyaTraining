package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AnemiaCheckupModel implements Parcelable {
    @SerializedName("head_id")
    String head_id;
    @SerializedName("member_id")
    String member_id;
    @SerializedName("inspection_date")
    String inspection_date;
    @SerializedName("name")
    String name;
    @SerializedName("anemia_test")
    String anemia_test;
    @SerializedName("is_anemic")
    String is_anemic;
//    @SerializedName("head_id")
//    String head_id;


    protected AnemiaCheckupModel(Parcel in) {
        head_id = in.readString();
        member_id = in.readString();
        inspection_date = in.readString();
        name = in.readString();
        anemia_test = in.readString();
        is_anemic = in.readString();
    }

    public static final Creator<AnemiaCheckupModel> CREATOR = new Creator<AnemiaCheckupModel>() {
        @Override
        public AnemiaCheckupModel createFromParcel(Parcel in) {
            return new AnemiaCheckupModel(in);
        }

        @Override
        public AnemiaCheckupModel[] newArray(int size) {
            return new AnemiaCheckupModel[size];
        }
    };

    public String getHead_id() {
        return head_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public String getName() {
        return name;
    }

    public String getAnemia_test() {
        return anemia_test;
    }

    public String getIs_anemic() {
        return is_anemic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(head_id);
        parcel.writeString(member_id);
        parcel.writeString(inspection_date);
        parcel.writeString(name);
        parcel.writeString(anemia_test);
        parcel.writeString(is_anemic);
    }
}
