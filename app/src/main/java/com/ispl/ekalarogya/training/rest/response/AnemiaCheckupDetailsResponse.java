package com.ispl.ekalarogya.training.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AnemiaCheckupDetailsResponse implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("village_name") String village_name;
    @SerializedName("village_id") String village_id;
    @SerializedName("head_name") String head_name;
    @SerializedName("head_id") String head_id;
    @SerializedName("member_name") String member_name;
    @SerializedName("member_id") String member_id;
    @SerializedName("age") String age;
    @SerializedName("inspection_date") String inspection_date;
    @SerializedName("is_pregnant") String is_pregnant;
    @SerializedName("is_anemic") String is_anemic;
    @SerializedName("anemia_test") String anemia_test;
    @SerializedName("anemia_type") String anemia_type;

    protected AnemiaCheckupDetailsResponse(Parcel in) {
        id = in.readString();
        village_name = in.readString();
        village_id = in.readString();
        head_name = in.readString();
        head_id = in.readString();
        member_name = in.readString();
        member_id = in.readString();
        age = in.readString();
        inspection_date = in.readString();
        is_pregnant = in.readString();
        is_anemic = in.readString();
        anemia_test = in.readString();
        anemia_type = in.readString();
    }

    public static final Creator<AnemiaCheckupDetailsResponse> CREATOR = new Creator<AnemiaCheckupDetailsResponse>() {
        @Override
        public AnemiaCheckupDetailsResponse createFromParcel(Parcel in) {
            return new AnemiaCheckupDetailsResponse(in);
        }

        @Override
        public AnemiaCheckupDetailsResponse[] newArray(int size) {
            return new AnemiaCheckupDetailsResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getHead_name() {
        return head_name;
    }

    public String getHead_id() {
        return head_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getAge() {
        return age;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public String getIs_pregnant() {
        return is_pregnant;
    }

    public String getIs_anemic() {
        return is_anemic;
    }

    public String getAnemia_test() {
        return anemia_test;
    }

    public String getAnemia_type() {
        return anemia_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(village_name);
        parcel.writeString(village_id);
        parcel.writeString(head_name);
        parcel.writeString(head_id);
        parcel.writeString(member_name);
        parcel.writeString(member_id);
        parcel.writeString(age);
        parcel.writeString(inspection_date);
        parcel.writeString(is_pregnant);
        parcel.writeString(is_anemic);
        parcel.writeString(anemia_test);
        parcel.writeString(anemia_type);
    }
}
