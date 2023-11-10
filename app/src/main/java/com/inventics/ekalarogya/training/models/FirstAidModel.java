package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FirstAidModel implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("village_id") String village_id;
    @SerializedName("head_id") String head_id;
    @SerializedName("head_name") String head_name;
    @SerializedName("all")String all;
    @SerializedName("date")String date;
    @SerializedName("name")String name;
    @SerializedName("other")String other;
    @SerializedName("member_id")String member_id;
    @SerializedName("cuts") String cuts;
    @SerializedName("heart_attacks") String heart_attacks;
    @SerializedName("unconscious") String unconscious;
    @SerializedName("snake_bites") String snake_bites;
    @SerializedName("training") String training;
    @SerializedName("incident_record") String incident_record;
    @SerializedName("remarks") String remarks;
    @SerializedName("inspection_date") String inspection_date;
    @SerializedName("patient_name") String patient_name;
    @SerializedName("guest_patient_name") String guest_patient_name;
//    @SerializedName("sevika_id") String sevika_id;


    protected FirstAidModel(Parcel in) {
        id = in.readString();
        sevika_id = in.readString();
        village_id = in.readString();
        head_id = in.readString();
        head_name = in.readString();
        guest_patient_name = in.readString();
        patient_name = in.readString();
        all = in.readString();
        date = in.readString();
        name = in.readString();
        member_id = in.readString();
        other = in.readString();
        cuts = in.readString();
        heart_attacks = in.readString();
        unconscious = in.readString();
        training = in.readString();
        incident_record = in.readString();
        remarks = in.readString();
        snake_bites = in.readString();
        inspection_date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sevika_id);
        dest.writeString(village_id);
        dest.writeString(head_id);
        dest.writeString(head_name);
        dest.writeString(guest_patient_name);
        dest.writeString(patient_name);
        dest.writeString(all);
        dest.writeString(date);
        dest.writeString(other);
        dest.writeString(name);
        dest.writeString(member_id);
        dest.writeString(cuts);
        dest.writeString(heart_attacks);
        dest.writeString(unconscious);
        dest.writeString(training);
        dest.writeString(incident_record);
        dest.writeString(remarks);
        dest.writeString(snake_bites);
        dest.writeString(inspection_date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FirstAidModel> CREATOR = new Creator<FirstAidModel>() {
        @Override
        public FirstAidModel createFromParcel(Parcel in) {
            return new FirstAidModel(in);
        }

        @Override
        public FirstAidModel[] newArray(int size) {
            return new FirstAidModel[size];
        }
    };

    public String getOther() {
        return other;
    }

    public String getId() {
        return id;
    }

    public String getSevika_id() {
        return sevika_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getHead_id() {
        return head_id;
    }

    public String getHead_name() {
        return head_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getGuest_patient_name() {
        return guest_patient_name;
    }

    public String getAll() {
        return all;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getCuts() {
        return cuts;
    }

    public String getHeart_attacks() {
        return heart_attacks;
    }

    public String getUnconscious() {
        return unconscious;
    }

    public String getSnake_bites() {
        return snake_bites;
    }

    public String getTraining() {
        return training;
    }

    public String getIncident_record() {
        return incident_record;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getInspection_date() {
        return inspection_date;
    }


    @Override
    public String toString() {
        return "FirstAidModel{" +
                "id='" + id + '\'' +
                ", sevika_id='" + sevika_id + '\'' +
                ", village_id='" + village_id + '\'' +
                ", head_id='" + head_id + '\'' +
                ", head_name='" + head_name + '\'' +
                ", all='" + all + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", other='" + other + '\'' +
                ", member_id='" + member_id + '\'' +
                ", cuts='" + cuts + '\'' +
                ", heart_attacks='" + heart_attacks + '\'' +
                ", unconscious='" + unconscious + '\'' +
                ", snake_bites='" + snake_bites + '\'' +
                ", training='" + training + '\'' +
                ", incident_record='" + incident_record + '\'' +
                ", remarks='" + remarks + '\'' +
                ", inspection_date='" + inspection_date + '\'' +
                ", patient_name='" + patient_name + '\'' +
                ", guest_patient_name='" + guest_patient_name + '\'' +
                '}';
    }
}
