package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FamilyHeadDetailsModelResponse implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("family_head") String family_head;
    @SerializedName("gender") String gender;
    @SerializedName("head_code") String head_code;
    @SerializedName("age") String age;
    @SerializedName("dob") String dob;
    @SerializedName("occupation") String occupation;
    @SerializedName("address") String address;
    @SerializedName("latitude") String latitude;
    @SerializedName("longitude") String longitude;
    @SerializedName("phone_num") String phone_num;
    @SerializedName("aadhar") String aadhar;
    @SerializedName("aadhar_photo") String aadhar_photo;
    @SerializedName("members")
    List<FamilyMembersModel> familyMembersModelList;
    @SerializedName("total_members_of_family") String total_members_of_family;
    @SerializedName("village_id") String village_id;
    @SerializedName("village_name") String village_name;
    @SerializedName("submitted_by") String submitted_by;


    protected FamilyHeadDetailsModelResponse(Parcel in) {
        id = in.readString();
        family_head = in.readString();
        gender = in.readString();
        head_code = in.readString();
        age = in.readString();
        dob = in.readString();
        occupation = in.readString();
        address = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        phone_num = in.readString();
        aadhar = in.readString();
        aadhar_photo = in.readString();
        familyMembersModelList = in.createTypedArrayList(FamilyMembersModel.CREATOR);
        total_members_of_family = in.readString();
        village_id = in.readString();
        village_name = in.readString();
        submitted_by = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(family_head);
        dest.writeString(gender);
        dest.writeString(head_code);
        dest.writeString(age);
        dest.writeString(dob);
        dest.writeString(occupation);
        dest.writeString(address);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(phone_num);
        dest.writeString(aadhar);
        dest.writeString(aadhar_photo);
        dest.writeTypedList(familyMembersModelList);
        dest.writeString(total_members_of_family);
        dest.writeString(village_id);
        dest.writeString(village_name);
        dest.writeString(submitted_by);
    }

    public static final Creator<FamilyHeadDetailsModelResponse> CREATOR = new Creator<FamilyHeadDetailsModelResponse>() {
        @Override
        public FamilyHeadDetailsModelResponse createFromParcel(Parcel in) {
            return new FamilyHeadDetailsModelResponse(in);
        }

        @Override
        public FamilyHeadDetailsModelResponse[] newArray(int size) {
            return new FamilyHeadDetailsModelResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getFamily_head() {
        return family_head;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getAadhar() {
        return aadhar;
    }

    public List<FamilyMembersModel> getFamilyMembersModelList() {
        return familyMembersModelList;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public String getAadhar_photo() {
        return aadhar_photo;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getSubmitted_by() {
        return submitted_by;
    }

    public String getTotal_members_of_family() {
        return total_members_of_family;
    }

    public String getHead_code() {
        return head_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDob() {
        return dob;
    }

    public String getVillage_name() {
        return village_name;
    }
}
