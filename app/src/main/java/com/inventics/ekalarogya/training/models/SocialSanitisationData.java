package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SocialSanitisationData implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("head_name") String head_name;
    @SerializedName("head_id") String head_id;
    @SerializedName("toilet") String toilet;
    @SerializedName("toilet_photo") String toilet_photo;
    @SerializedName("soakpit") String soakpit;
    @SerializedName("soakpit_photo") String soakpit_photo;
    @SerializedName("compost_pit") String compost_pit;
    @SerializedName("compost_pit_photo") String compost_pit_photo;
    @SerializedName("water_treatment") String water_treatment;
    @SerializedName("water_treatment_photo") String water_treatment_photo;
    @SerializedName("wall_writing") String wall_writing;
    @SerializedName("wall_writing_photo") String wall_writing_photo;
    @SerializedName("people_use_toilet") String people_use_toilet;
    @SerializedName("people_use_toilet_photo") String people_use_toilet_photo;
    @SerializedName("medicine_plantation") String medicine_plantation;
    @SerializedName("medicine_plantation_photo") String medicine_plantation_photo;
    @SerializedName("nutrition_plantation") String nutrition_plantation;
    @SerializedName("nutrition_plantation_photo") String nutrition_plantation_photo;
    @SerializedName("filter_uses") String filter_uses;
    @SerializedName("filter_uses_photo") String filter_uses_photo;
    @SerializedName("tree_planting") String tree_planting;
    @SerializedName("tree_planting_photo") String tree_planting_photo;

    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("village_id") String village_id;
    @SerializedName("inspection_date") String inspection_date;


    protected SocialSanitisationData(Parcel in) {
        id = in.readString();
        head_name = in.readString();
        head_id = in.readString();
        toilet = in.readString();
        toilet_photo = in.readString();
        soakpit = in.readString();
        soakpit_photo = in.readString();
        compost_pit = in.readString();
        compost_pit_photo = in.readString();
        water_treatment = in.readString();
        water_treatment_photo = in.readString();
        wall_writing = in.readString();
        wall_writing_photo = in.readString();
        people_use_toilet = in.readString();
        people_use_toilet_photo = in.readString();
        medicine_plantation = in.readString();
        medicine_plantation_photo = in.readString();
        nutrition_plantation = in.readString();
        nutrition_plantation_photo = in.readString();
        filter_uses = in.readString();
        filter_uses_photo = in.readString();
        tree_planting = in.readString();
        tree_planting_photo = in.readString();
        sevika_id = in.readString();
        village_id = in.readString();
        inspection_date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(head_name);
        dest.writeString(head_id);
        dest.writeString(toilet);
        dest.writeString(toilet_photo);
        dest.writeString(soakpit);
        dest.writeString(soakpit_photo);
        dest.writeString(compost_pit);
        dest.writeString(compost_pit_photo);
        dest.writeString(water_treatment);
        dest.writeString(water_treatment_photo);
        dest.writeString(wall_writing);
        dest.writeString(wall_writing_photo);
        dest.writeString(people_use_toilet);
        dest.writeString(people_use_toilet_photo);
        dest.writeString(medicine_plantation);
        dest.writeString(medicine_plantation_photo);
        dest.writeString(nutrition_plantation);
        dest.writeString(nutrition_plantation_photo);
        dest.writeString(filter_uses);
        dest.writeString(filter_uses_photo);
        dest.writeString(tree_planting);
        dest.writeString(tree_planting_photo);
        dest.writeString(sevika_id);
        dest.writeString(village_id);
        dest.writeString(inspection_date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SocialSanitisationData> CREATOR = new Creator<SocialSanitisationData>() {
        @Override
        public SocialSanitisationData createFromParcel(Parcel in) {
            return new SocialSanitisationData(in);
        }

        @Override
        public SocialSanitisationData[] newArray(int size) {
            return new SocialSanitisationData[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getHead_name() {
        return head_name;
    }

    public String getHead_id() {
        return head_id;
    }

    public String getToilet() {
        return toilet;
    }

    public String getToilet_photo() {
        return toilet_photo;
    }

    public String getSoakpit() {
        return soakpit;
    }

    public String getSoakpit_photo() {
        return soakpit_photo;
    }

    public String getCompost_pit() {
        return compost_pit;
    }

    public String getCompost_pit_photo() {
        return compost_pit_photo;
    }

    public String getWater_treatment() {
        return water_treatment;
    }

    public String getWater_treatment_photo() {
        return water_treatment_photo;
    }

    public String getWall_writing() {
        return wall_writing;
    }

    public String getWall_writing_photo() {
        return wall_writing_photo;
    }

    public String getPeople_use_toilet() {
        return people_use_toilet;
    }

    public String getPeople_use_toilet_photo() {
        return people_use_toilet_photo;
    }

    public String getMedicine_plantation() {
        return medicine_plantation;
    }

    public String getMedicine_plantation_photo() {
        return medicine_plantation_photo;
    }

    public String getNutrition_plantation() {
        return nutrition_plantation;
    }

    public String getNutrition_plantation_photo() {
        return nutrition_plantation_photo;
    }

    public String getFilter_uses() {
        return filter_uses;
    }

    public String getFilter_uses_photo() {
        return filter_uses_photo;
    }

    public String getTree_planting() {
        return tree_planting;
    }

    public String getTree_planting_photo() {
        return tree_planting_photo;
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

    @Override
    public String toString() {
        return "SocialSanitisationData{" +
                "id='" + id + '\'' +
                ", head_name='" + head_name + '\'' +
                ", head_id='" + head_id + '\'' +
                ", toilet='" + toilet + '\'' +
                ", toilet_photo='" + toilet_photo + '\'' +
                ", soakpit='" + soakpit + '\'' +
                ", soakpit_photo='" + soakpit_photo + '\'' +
                ", compost_pit='" + compost_pit + '\'' +
                ", compost_pit_photo='" + compost_pit_photo + '\'' +
                ", water_treatment='" + water_treatment + '\'' +
                ", water_treatment_photo='" + water_treatment_photo + '\'' +
                ", wall_writing='" + wall_writing + '\'' +
                ", wall_writing_photo='" + wall_writing_photo + '\'' +
                ", people_use_toilet='" + people_use_toilet + '\'' +
                ", people_use_toilet_photo='" + people_use_toilet_photo + '\'' +
                ", medicine_plantation='" + medicine_plantation + '\'' +
                ", medicine_plantation_photo='" + medicine_plantation_photo + '\'' +
                ", nutrition_plantation='" + nutrition_plantation + '\'' +
                ", nutrition_plantation_photo='" + nutrition_plantation_photo + '\'' +
                ", filter_uses='" + filter_uses + '\'' +
                ", filter_uses_photo='" + filter_uses_photo + '\'' +
                ", tree_planting='" + tree_planting + '\'' +
                ", tree_planting_photo='" + tree_planting_photo + '\'' +
                ", sevika_id='" + sevika_id + '\'' +
                ", village_id='" + village_id + '\'' +
                ", inspection_date='" + inspection_date + '\'' +
                '}';
    }
}
