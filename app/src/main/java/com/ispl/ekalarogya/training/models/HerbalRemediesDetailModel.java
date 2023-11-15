package com.ispl.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

public class HerbalRemediesDetailModel {
    @SerializedName("id") String id;
    @SerializedName("village_id") String village_id;
    @SerializedName("village_name") String village_name;
    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("camp_month") String camp_month;
    @SerializedName("male_present") String male_present;
    @SerializedName("female_present") String female_present;
    @SerializedName("children_present") String children_present;
    @SerializedName("total_present") String total_present;
    @SerializedName("treatment_type") String treatment_type;
    @SerializedName("total_cured") String total_cured;


}
