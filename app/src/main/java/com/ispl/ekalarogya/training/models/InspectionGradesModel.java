package com.ispl.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

public class InspectionGradesModel {
    @SerializedName("vidyalay_grade") String vidyalay_grade;
    @SerializedName("jagran_grade") String jagran_grade;
    @SerializedName("family_grade") String family_grade;
    @SerializedName("poster_grade") String poster_grade;
    @SerializedName("plantation_grade") String plantation_grade;

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
}
