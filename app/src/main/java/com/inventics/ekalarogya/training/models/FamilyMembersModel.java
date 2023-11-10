package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FamilyMembersModel implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("head_code") String head_code;
    @SerializedName("head_id") String head_id;
    @SerializedName("head_name") String head_name;
    @SerializedName("village_name") String village_name;
    @SerializedName("village_code") String village_code;
    @SerializedName("dob") String dob;
    @SerializedName("member_code") String member_code;
    @SerializedName("member_id") String member_id;
    @SerializedName("name") String name;
    @SerializedName("relation") String relation;
    @SerializedName("age") String age;
    @SerializedName("gender") String gender;
    @SerializedName("marital_status") String marital_status;
    @SerializedName("occupation") String occupation;
    @SerializedName("relationship_with_head") String relation_with_head;
    @SerializedName("relationship_with_head_code") String relationship_with_head_code;
    @SerializedName("is_pregnant") String is_pregnant;
    @SerializedName("aadhar_num") String aadhar_num;
    @SerializedName("aadhar_photo") String aadhar_photo;
    @SerializedName("village_id") String village_id;


    protected FamilyMembersModel(Parcel in) {
        id = in.readString();
        head_code = in.readString();
        head_id = in.readString();
        head_name = in.readString();
        village_name = in.readString();
        village_code = in.readString();
        dob = in.readString();
        member_code = in.readString();
        member_id = in.readString();
        name = in.readString();
        relation = in.readString();
        age = in.readString();
        gender = in.readString();
        marital_status = in.readString();
        occupation = in.readString();
        relation_with_head = in.readString();
        relationship_with_head_code = in.readString();
        is_pregnant = in.readString();
        aadhar_num = in.readString();
        aadhar_photo = in.readString();
        village_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(head_code);
        dest.writeString(head_id);
        dest.writeString(head_name);
        dest.writeString(village_name);
        dest.writeString(village_code);
        dest.writeString(dob);
        dest.writeString(member_code);
        dest.writeString(member_id);
        dest.writeString(name);
        dest.writeString(relation);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(marital_status);
        dest.writeString(occupation);
        dest.writeString(relation_with_head);
        dest.writeString(relationship_with_head_code);
        dest.writeString(is_pregnant);
        dest.writeString(aadhar_num);
        dest.writeString(aadhar_photo);
        dest.writeString(village_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FamilyMembersModel> CREATOR = new Creator<FamilyMembersModel>() {
        @Override
        public FamilyMembersModel createFromParcel(Parcel in) {
            return new FamilyMembersModel(in);
        }

        @Override
        public FamilyMembersModel[] newArray(int size) {
            return new FamilyMembersModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRelation() {
        return relation;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getRelation_with_head() {
        return relation_with_head;
    }

    public String getIs_pregnant() {
        return is_pregnant;
    }

    public String getAadhar_num() {
        return aadhar_num;
    }

    public String getAadhar_photo() {
        return aadhar_photo;
    }

    public String getVillage_id() {
        return village_id;
    }


    public String getHead_code() {
        return head_code;
    }

    public String getHead_id() {
        return head_id;
    }

    public String getMember_code() {
        return member_code;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getRelationship_with_head_code() {
        return relationship_with_head_code;
    }

    public String getHead_name() {
        return head_name;
    }

    public String getVillage_name() {
        return village_name;
    }

    public String getVillage_code() {
        return village_code;
    }

    public String getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return "FamilyMembersModel{" +
                "id='" + id + '\'' +
                ", head_code='" + head_code + '\'' +
                ", head_id='" + head_id + '\'' +
                ", head_name='" + head_name + '\'' +
                ", village_name='" + village_name + '\'' +
                ", village_code='" + village_code + '\'' +
                ", dob='" + dob + '\'' +
                ", member_code='" + member_code + '\'' +
                ", member_id='" + member_id + '\'' +
                ", name='" + name + '\'' +
                ", relation='" + relation + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", marital_status='" + marital_status + '\'' +
                ", occupation='" + occupation + '\'' +
                ", relation_with_head='" + relation_with_head + '\'' +
                ", relationship_with_head_code='" + relationship_with_head_code + '\'' +
                ", is_pregnant='" + is_pregnant + '\'' +
                ", aadhar_num='" + aadhar_num + '\'' +
                ", aadhar_photo='" + aadhar_photo + '\'' +
                ", village_id='" + village_id + '\'' +
                '}';
    }
}

