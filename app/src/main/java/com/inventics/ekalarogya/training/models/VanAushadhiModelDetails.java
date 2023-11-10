package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VanAushadhiModelDetails implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("eap_id") String eap_id;
    @SerializedName("pat_name") String pat_name;
    @SerializedName("village_id") String village_id;
    @SerializedName("gender") String gender;
    @SerializedName("age") String age;
    @SerializedName("problem") String problem;
    @SerializedName("prescription") String prescription;
    @SerializedName("fees") String fees;
    @SerializedName("date") String date;
    @SerializedName("treatment_status") String treatment_status;
    @SerializedName("cured") String cured;
    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("member_name") String member_name;
    @SerializedName("patient_name") String patient_name;
    @SerializedName("patient_phone") String patient_mobile;
    @SerializedName("head_name") String head_name;
    @SerializedName("head_gender") String head_gender;
    @SerializedName("member_gender") String member_gender;
    @SerializedName("appointment_date") String appointment_date;
    @SerializedName("head_id") String head_id;

    protected VanAushadhiModelDetails(Parcel in) {
        id = in.readString();
        eap_id = in.readString();
        pat_name = in.readString();
        village_id = in.readString();
        gender = in.readString();
        age = in.readString();
        problem = in.readString();
        prescription = in.readString();
        fees = in.readString();
        date = in.readString();
        treatment_status = in.readString();
        cured = in.readString();
        sevika_id = in.readString();
        member_name = in.readString();
        patient_name = in.readString();
        patient_mobile = in.readString();
        head_name = in.readString();
        head_gender = in.readString();
        member_gender = in.readString();
        appointment_date = in.readString();
        head_id = in.readString();
    }

    public static final Creator<VanAushadhiModelDetails> CREATOR = new Creator<VanAushadhiModelDetails>() {
        @Override
        public VanAushadhiModelDetails createFromParcel(Parcel in) {
            return new VanAushadhiModelDetails(in);
        }

        @Override
        public VanAushadhiModelDetails[] newArray(int size) {
            return new VanAushadhiModelDetails[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getEap_id() {
        return eap_id;
    }

    public String getPat_name() {
        return pat_name;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getHead_name() {
        return head_name;
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

    public String getDate() {
        return date;
    }

    public String getTreatment_status() {
        return treatment_status;
    }

    public String getCured() {
        return cured;
    }

    public String getSevika_id() {
        return sevika_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getPatient_mobile() {
        return patient_mobile;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getHead_gender() {
        return head_gender;
    }

    public String getMember_gender() {
        return member_gender;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public String getHead_id() {
        return head_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(eap_id);
        parcel.writeString(pat_name);
        parcel.writeString(village_id);
        parcel.writeString(gender);
        parcel.writeString(age);
        parcel.writeString(problem);
        parcel.writeString(prescription);
        parcel.writeString(fees);
        parcel.writeString(date);
        parcel.writeString(treatment_status);
        parcel.writeString(cured);
        parcel.writeString(sevika_id);
        parcel.writeString(member_name);
        parcel.writeString(patient_name);
        parcel.writeString(patient_mobile);
        parcel.writeString(head_name);
        parcel.writeString(head_gender);
        parcel.writeString(member_gender);
        parcel.writeString(appointment_date);
        parcel.writeString(head_id);
    }
}
