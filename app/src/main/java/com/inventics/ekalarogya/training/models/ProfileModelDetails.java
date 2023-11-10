package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProfileModelDetails implements Parcelable {
    @SerializedName("name")String name;
    @SerializedName("mobile")String mobile_no;
    @SerializedName("email")String email;
    @SerializedName("gender")String gender;
    @SerializedName("online")String online;
    @SerializedName("aadhar_photo")String aadhar_photo;
    @SerializedName("aadharNumber")String aadharNumber;
    @SerializedName("panPhoto")String panPhoto;
    @SerializedName("panNumber")String panNumber;
    @SerializedName("accountNumber")String accountNumber;
    @SerializedName("accHolderName")String accHolderName;
    @SerializedName("bankName")String bankName;
    @SerializedName("bankBranch")String bankBranch;
    @SerializedName("ifscCode")String ifscCode;
    @SerializedName("bankRegisteredMobile")String bankRegisteredMobile;
    @SerializedName("profile_img")String profile_img;

    protected ProfileModelDetails(Parcel in) {
        name = in.readString();
        mobile_no = in.readString();
        email = in.readString();
        gender = in.readString();
        online = in.readString();
        aadhar_photo = in.readString();
        aadharNumber = in.readString();
        panPhoto = in.readString();
        panNumber = in.readString();
        accountNumber = in.readString();
        accHolderName = in.readString();
        bankName = in.readString();
        bankBranch = in.readString();
        ifscCode = in.readString();
        bankRegisteredMobile = in.readString();
        profile_img = in.readString();
    }

    public static final Creator<ProfileModelDetails> CREATOR = new Creator<ProfileModelDetails>() {
        @Override
        public ProfileModelDetails createFromParcel(Parcel in) {
            return new ProfileModelDetails(in);
        }

        @Override
        public ProfileModelDetails[] newArray(int size) {
            return new ProfileModelDetails[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getOnline() {
        return online;
    }

    public String getAadhar_photo() {
        return aadhar_photo;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public String getPanPhoto() {
        return panPhoto;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccHolderName() {
        return accHolderName;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public String getBankRegisteredMobile() {
        return bankRegisteredMobile;
    }

    public String getProfile_img() {
        return profile_img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(mobile_no);
        parcel.writeString(email);
        parcel.writeString(gender);
        parcel.writeString(online);
        parcel.writeString(aadhar_photo);
        parcel.writeString(aadharNumber);
        parcel.writeString(panPhoto);
        parcel.writeString(panNumber);
        parcel.writeString(accountNumber);
        parcel.writeString(accHolderName);
        parcel.writeString(bankName);
        parcel.writeString(bankBranch);
        parcel.writeString(ifscCode);
        parcel.writeString(bankRegisteredMobile);
        parcel.writeString(profile_img);
    }

    @Override
    public String toString() {
        return "ProfileModelDetails{" +
                "name='" + name + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", online='" + online + '\'' +
                ", aadhar_photo='" + aadhar_photo + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", panPhoto='" + panPhoto + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accHolderName='" + accHolderName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankBranch='" + bankBranch + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", bankRegisteredMobile='" + bankRegisteredMobile + '\'' +
                ", profile_img='" + profile_img + '\'' +
                '}';
    }
}
