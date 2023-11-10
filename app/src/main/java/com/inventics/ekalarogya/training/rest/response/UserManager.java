package com.inventics.ekalarogya.training.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sonu on 07/01/18.
 */


public class UserManager extends BaseResponse implements Parcelable {

    @SerializedName("user_name")
    private String userId;
    @SerializedName("fname")
    private String userFirstName;
    @SerializedName("lname")
    private String userLastName;
    @SerializedName("email")
    private String userEmailId;
    @SerializedName("mobile_no")
    private String userPhoneNumber;
    @SerializedName("role_id")
    String role_id;
    @SerializedName("photo")
    private String userPhoto;
    @SerializedName("auth_code")
    private String authCode;
    @SerializedName("dob")
    private String dob;
    @SerializedName("genderid")
    private int genderId;
    @SerializedName("alter_mobile")
    private String alternatePhone;
    @SerializedName("village_id")
    private String village_id;



    public UserManager() {
    }


    protected UserManager(Parcel in) {
        userId = in.readString();
        userFirstName = in.readString();
        userLastName = in.readString();
        userEmailId = in.readString();
        userPhoneNumber = in.readString();
        role_id = in.readString();
        userPhoto = in.readString();
        authCode = in.readString();
        dob = in.readString();
        genderId = in.readInt();
        alternatePhone = in.readString();
        village_id = in.readString();
    }

    public static final Creator<UserManager> CREATOR = new Creator<UserManager>() {
        @Override
        public UserManager createFromParcel(Parcel in) {
            return new UserManager(in);
        }

        @Override
        public UserManager[] newArray(int size) {
            return new UserManager[size];
        }
    };

    public String getVillage_id() {
        return village_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public String getDob() {
        return dob;
    }

    public int getGenderId() {
        return genderId;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getFullName(){
        return userFirstName+" "+userLastName;
    }


    @Override
    public String toString() {
        return "UserManager{" +
                "userId='" + userId + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userEmailId='" + userEmailId + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", authCode='" + authCode + '\'' +
                ", village_id='" + village_id + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
        parcel.writeString(userFirstName);
        parcel.writeString(userLastName);
        parcel.writeString(userEmailId);
        parcel.writeString(userPhoneNumber);
        parcel.writeString(role_id);
        parcel.writeString(userPhoto);
        parcel.writeString(authCode);
        parcel.writeString(dob);
        parcel.writeInt(genderId);
        parcel.writeString(alternatePhone);
        parcel.writeString(village_id);
    }
}
