package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MalnutrinAnemiaCheckupModelResponse implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("member_id") String member_id;
    @SerializedName("name") String name;
    @SerializedName("date")String date;
    @SerializedName("anemia_test") String anemia_test;
    @SerializedName("is_pregnant") String is_pregnant;
    @SerializedName("inspection_date") String inspection_date;
    @SerializedName("anemia_type") String anemia_type;
    @SerializedName("is_anemic") String is_anemic;
    @SerializedName("village_id") String village_id;


    protected MalnutrinAnemiaCheckupModelResponse(Parcel in) {
        id = in.readString();
        member_id = in.readString();
        name = in.readString();
        date = in.readString();
        anemia_test = in.readString();
        is_pregnant = in.readString();
        inspection_date = in.readString();
        anemia_type = in.readString();
        is_anemic = in.readString();
        village_id = in.readString();
    }

    public static final Creator<MalnutrinAnemiaCheckupModelResponse> CREATOR = new Creator<MalnutrinAnemiaCheckupModelResponse>() {
        @Override
        public MalnutrinAnemiaCheckupModelResponse createFromParcel(Parcel in) {
            return new MalnutrinAnemiaCheckupModelResponse(in);
        }

        @Override
        public MalnutrinAnemiaCheckupModelResponse[] newArray(int size) {
            return new MalnutrinAnemiaCheckupModelResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getName() {
        return name;
    }

    public String getAnemia_test() {
        return anemia_test;
    }

    public String getDate() {
        return date;
    }

    public String getIs_pregnant() {
        return is_pregnant;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public String getAnemia_type() {
        return anemia_type;
    }

    public String getIs_anemic() {
        return is_anemic;
    }

    public String getVillage_id() {
        return village_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(member_id);
        parcel.writeString(name);
        parcel.writeString(date);
        parcel.writeString(anemia_test);
        parcel.writeString(is_pregnant);
        parcel.writeString(inspection_date);
        parcel.writeString(anemia_type);
        parcel.writeString(is_anemic);
        parcel.writeString(village_id);
    }
}
