package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VanaushadhiModel implements Parcelable {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String statusMessage;
    @SerializedName("code")
    private String errorCode;
    @SerializedName("id") String id;
    @SerializedName("EAP_id") String EAP_id;
    @SerializedName("patient_name") String patient_name;
    @SerializedName("village_id") String village_id;
    @SerializedName("gender") String gender;
    @SerializedName("age") String age;
    @SerializedName("problem") String problem;
    @SerializedName("prescription") String prescription;
    @SerializedName("fees") String fees;
    @SerializedName("appointment_date") String appointment_date;
    @SerializedName("cured") String cured;
    @SerializedName("head_name") String head_name;
    @SerializedName("member_name") String member_name;
    @SerializedName("member_gender") String member_gender;
    @SerializedName("head_gender") String head_gender;

    protected VanaushadhiModel(Parcel in) {
        status = in.readString();
        statusMessage = in.readString();
        errorCode = in.readString();
        id = in.readString();
        EAP_id = in.readString();
        patient_name = in.readString();
        village_id = in.readString();
        gender = in.readString();
        age = in.readString();
        problem = in.readString();
        prescription = in.readString();
        fees = in.readString();
        appointment_date = in.readString();
        cured = in.readString();
        head_name = in.readString();
        member_name = in.readString();
        member_gender = in.readString();
        head_gender = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(statusMessage);
        dest.writeString(errorCode);
        dest.writeString(id);
        dest.writeString(EAP_id);
        dest.writeString(patient_name);
        dest.writeString(village_id);
        dest.writeString(gender);
        dest.writeString(age);
        dest.writeString(problem);
        dest.writeString(prescription);
        dest.writeString(fees);
        dest.writeString(appointment_date);
        dest.writeString(cured);
        dest.writeString(head_name);
        dest.writeString(member_name);
        dest.writeString(member_gender);
        dest.writeString(head_gender);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VanaushadhiModel> CREATOR = new Creator<VanaushadhiModel>() {
        @Override
        public VanaushadhiModel createFromParcel(Parcel in) {
            return new VanaushadhiModel(in);
        }

        @Override
        public VanaushadhiModel[] newArray(int size) {
            return new VanaushadhiModel[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getId() {
        return id;
    }

    public String getEAP_id() {
        return EAP_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getProblem() {
        return problem;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getFees() {
        return fees;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public String getCured() {
        return cured;
    }

    public String getHead_name() {
        return head_name;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getMember_gender() {
        return member_gender;
    }

    public String getHead_gender() {
        return head_gender;
    }
}
