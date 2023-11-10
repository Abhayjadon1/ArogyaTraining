package com.inventics.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;

public class PersonalHygieneCheckupResponse {
    @SerializedName("id") String id;//Hygeine Id
    @SerializedName("member_name") String member_name;
    @SerializedName("head_id") String head_id;
    @SerializedName("nail") String nail;
    @SerializedName("nail_photo") String nail_photo;
    @SerializedName("health") String health;
    @SerializedName("health_photo") String health_photo;
    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("village_id") String village_id;
    @SerializedName("inspection_date") String inspection_date;

    public String getId() {
        return id;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getHead_id() {
        return head_id;
    }

    public String getNail() {
        return nail;
    }

    public String getNail_photo() {
        return nail_photo;
    }

    public String getHealth() {
        return health;
    }

    public String getHealth_photo() {
        return health_photo;
    }

    public String getSevika_id() {
        return sevika_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getInspection_date() {
        return inspection_date;
    }
}
