package com.inventics.ekalarogya.training.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MalnutrinDetailResponse implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("village_name") String village_name;
    @SerializedName("village_id") String village_id;
    @SerializedName("head_name") String head_name;
    @SerializedName("head_id") String head_id;
    @SerializedName("member_name") String member_name;
    @SerializedName("member_id") String member_id;
    @SerializedName("age") String age;
    @SerializedName("gender") String gender;
    @SerializedName("inspection_date") String inspection_date;
    @SerializedName("malnourished") String malnourished;
    @SerializedName("mid_arm_measurement") String mid_arm_measurement;
    @SerializedName("relation_with_head") String relation_with_head;
//


    protected MalnutrinDetailResponse(Parcel in) {
        id = in.readString();
        village_name = in.readString();
        village_id = in.readString();
        head_name = in.readString();
        head_id = in.readString();
        member_name = in.readString();
        member_id = in.readString();
        age = in.readString();
        gender = in.readString();
        inspection_date = in.readString();
        malnourished = in.readString();
        mid_arm_measurement = in.readString();
        relation_with_head = in.readString();
    }

    public static final Creator<MalnutrinDetailResponse> CREATOR = new Creator<MalnutrinDetailResponse>() {
        @Override
        public MalnutrinDetailResponse createFromParcel(Parcel in) {
            return new MalnutrinDetailResponse(in);
        }

        @Override
        public MalnutrinDetailResponse[] newArray(int size) {
            return new MalnutrinDetailResponse[size];
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

    public String getGender() {
        return gender;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public String getMalnourished() {
        return malnourished;
    }

    public String getMid_arm_measurement() {
        return mid_arm_measurement;
    }
    public String getRelation_with_head() {
        return relation_with_head;
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
        parcel.writeString(gender);
        parcel.writeString(inspection_date);
        parcel.writeString(malnourished);
        parcel.writeString(mid_arm_measurement);
        parcel.writeString(relation_with_head);
    }

    @Override
    public String toString() {
        return "MalnutrinDetailResponse{" +
                "id='" + id + '\'' +
                ", village_name='" + village_name + '\'' +
                ", village_id='" + village_id + '\'' +
                ", head_name='" + head_name + '\'' +
                ", head_id='" + head_id + '\'' +
                ", member_name='" + member_name + '\'' +
                ", member_id='" + member_id + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", inspection_date='" + inspection_date + '\'' +
                ", malnourished='" + malnourished + '\'' +
                ", mid_arm_measurement='" + mid_arm_measurement + '\'' +
                ", relation_with_head='" + relation_with_head + '\'' +
                '}';
    }
}
