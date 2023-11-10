package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InspectionStayModel implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("inspection_date") String inspection_date;
    @SerializedName("remark") String remark;
    @SerializedName("grades")
    List<InspectionGradesModel>inspectionGradesModels;
    @SerializedName("vidyalay_grade") String vidyalay_grade;
    @SerializedName("jagran_grade") String jagran_grade;
    @SerializedName("family_grade") String family_grade;
    @SerializedName("poster_grade") String poster_grade;
    @SerializedName("plantation_grade") String plantation_grade;
    @SerializedName("other_grade") String other_grade;


    protected InspectionStayModel(Parcel in) {
        id = in.readString();
        sevika_id = in.readString();
        inspection_date = in.readString();
        remark = in.readString();
        vidyalay_grade = in.readString();
        jagran_grade = in.readString();
        family_grade = in.readString();
        poster_grade = in.readString();
        plantation_grade = in.readString();
        other_grade = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sevika_id);
        dest.writeString(inspection_date);
        dest.writeString(remark);
        dest.writeString(vidyalay_grade);
        dest.writeString(jagran_grade);
        dest.writeString(family_grade);
        dest.writeString(poster_grade);
        dest.writeString(plantation_grade);
        dest.writeString(other_grade);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InspectionStayModel> CREATOR = new Creator<InspectionStayModel>() {
        @Override
        public InspectionStayModel createFromParcel(Parcel in) {
            return new InspectionStayModel(in);
        }

        @Override
        public InspectionStayModel[] newArray(int size) {
            return new InspectionStayModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getSevika_id() {
        return sevika_id;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public String getRemark() {
        return remark;
    }

    public String getOther_grade() {
        return other_grade;
    }

    public List<InspectionGradesModel> getInspectionGradesModels() {
        return inspectionGradesModels;
    }

    public String getVidyalay_grade() {
        return vidyalay_grade;
    }

    public String getJagran_grade() {
        return jagran_grade;
    }

    public String getFamily_grade() {
        return family_grade;
    }

    public String getPoster_grade() {
        return poster_grade;
    }

    public String getPlantation_grade() {
        return plantation_grade;
    }

    @Override
    public String toString() {
        return "InspectionStayModel{" +
                "id='" + id + '\'' +
                ", sevika_id='" + sevika_id + '\'' +
                ", inspection_date='" + inspection_date + '\'' +
                ", remark='" + remark + '\'' +
                ", inspectionGradesModels=" + inspectionGradesModels +
                ", vidyalay_grade='" + vidyalay_grade + '\'' +
                ", jagran_grade='" + jagran_grade + '\'' +
                ", family_grade='" + family_grade + '\'' +
                ", poster_grade='" + poster_grade + '\'' +
                ", plantation_grade='" + plantation_grade + '\'' +
                ", other_grade='" + other_grade + '\'' +
                '}';
    }
}
