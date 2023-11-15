package com.ispl.ekalarogya.training.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FamilyVisitModel implements Parcelable {
    // "": 2,
    //            "": 65,
    //            "": "Test Village",
    //            "": 4,
    //            "": 3,
    //            "": 2,
    //            "": 5,
    //            "": "2023-02-02"
    @SerializedName("id") String id;
    @SerializedName("village_id") String village_id;
    @SerializedName("village_name") String village_name;
    @SerializedName("male") String male;
    @SerializedName("female") String female;
    @SerializedName("children") String children;
    @SerializedName("family") String family;
    @SerializedName("subject") String subject;
    @SerializedName("inspection_date") String inspection_date;

    protected FamilyVisitModel(Parcel in) {
        id = in.readString();
        village_id = in.readString();
        village_name = in.readString();
        male = in.readString();
        female = in.readString();
        children = in.readString();
        family = in.readString();
        subject = in.readString();
        inspection_date = in.readString();
    }

    public static final Creator<FamilyVisitModel> CREATOR = new Creator<FamilyVisitModel>() {
        @Override
        public FamilyVisitModel createFromParcel(Parcel in) {
            return new FamilyVisitModel(in);
        }

        @Override
        public FamilyVisitModel[] newArray(int size) {
            return new FamilyVisitModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public String getMale() {
        return male;
    }

    public String getFemale() {
        return female;
    }

    public String getChildren() {
        return children;
    }

    public String getFamily() {
        return family;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(village_id);
        parcel.writeString(village_name);
        parcel.writeString(male);
        parcel.writeString(female);
        parcel.writeString(children);
        parcel.writeString(family);
        parcel.writeString(subject);
        parcel.writeString(inspection_date);
    }

    @Override
    public String toString() {
        return "FamilyVisitModel{" +
                "id='" + id + '\'' +
                ", village_id='" + village_id + '\'' +
                ", village_name='" + village_name + '\'' +
                ", male='" + male + '\'' +
                ", female='" + female + '\'' +
                ", children='" + children + '\'' +
                ", family='" + family + '\'' +
                ", subject='" + subject + '\'' +
                ", inspection_date='" + inspection_date + '\'' +
                '}';
    }
}
