package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PersonalHygineListModel implements Parcelable {

    @SerializedName("id") String id;////id is hygiene id
    @SerializedName("inspection_date") String inspection_date;
    @SerializedName("member_name") String name;
    @SerializedName("member_id") String member_id;
    @SerializedName("nail_check") String nail_check;
    @SerializedName("health_check") String health_check;
    @SerializedName("nail") String nail;
    @SerializedName("health") String health;
    @SerializedName("village_id") String village_id;
    @SerializedName("head_id") String head_id;
    @SerializedName("nail_photo") String nail_photo;
    @SerializedName("health_photo") String health_photo;

    @SerializedName("sevika_id") String sevika_id;


    protected PersonalHygineListModel(Parcel in) {
        id = in.readString();
        inspection_date = in.readString();
        name = in.readString();
        member_id = in.readString();
        nail_check = in.readString();
        health_check = in.readString();
        nail = in.readString();
        health = in.readString();
        village_id = in.readString();
        head_id = in.readString();
        nail_photo = in.readString();
        health_photo = in.readString();
        sevika_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(inspection_date);
        dest.writeString(name);
        dest.writeString(member_id);
        dest.writeString(nail_check);
        dest.writeString(health_check);
        dest.writeString(nail);
        dest.writeString(health);
        dest.writeString(village_id);
        dest.writeString(head_id);
        dest.writeString(nail_photo);
        dest.writeString(health_photo);
        dest.writeString(sevika_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonalHygineListModel> CREATOR = new Creator<PersonalHygineListModel>() {
        @Override
        public PersonalHygineListModel createFromParcel(Parcel in) {
            return new PersonalHygineListModel(in);
        }

        @Override
        public PersonalHygineListModel[] newArray(int size) {
            return new PersonalHygineListModel[size];
        }
    };

    public String getMember_id() {
        return member_id;
    }

    public String getId() {
        return id;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public String getName() {
        return name;
    }

    public String getNail_check() {
        return nail_check;
    }

    public String getHealth_check() {
        return health_check;
    }

    public String getNail() {
        return nail;
    }

    public String getHealth() {
        return health;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getHead_id() {
        return head_id;
    }

    public String getNail_photo() {
        return nail_photo;
    }

    public String getHealth_photo() {
        return health_photo;
    }


    public String getSevika_id() {
        return sevika_id;
    }

    @Override
    public String toString() {
        return "PersonalHygineListModel{" +
                "id='" + id + '\'' +
                ", inspection_date='" + inspection_date + '\'' +
                ", name='" + name + '\'' +
                ", member_id='" + member_id + '\'' +
                ", nail_check='" + nail_check + '\'' +
                ", health_check='" + health_check + '\'' +
                ", nail='" + nail + '\'' +
                ", health='" + health + '\'' +
                ", village_id='" + village_id + '\'' +
                ", head_id='" + head_id + '\'' +
                ", nail_photo='" + nail_photo + '\'' +
                ", health_photo='" + health_photo + '\'' +
                ", sevika_id='" + sevika_id + '\'' +
                '}';
    }
}
