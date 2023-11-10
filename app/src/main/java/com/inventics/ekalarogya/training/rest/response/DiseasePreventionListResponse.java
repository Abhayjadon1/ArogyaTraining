package com.inventics.ekalarogya.training.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DiseasePreventionListResponse implements Parcelable {
    @SerializedName("id") String id;
    @SerializedName("village_id") String village_id;
    @SerializedName("inspection_date") String inspection_date;
    @SerializedName("village_name") String village_name;
    @SerializedName("cleanliness") String cleanliness;
    @SerializedName("malaria") String malaria;
    @SerializedName("nutrition") String nutrition;
    @SerializedName("maternal_safety") String maternal_safety;
    @SerializedName("child_safety") String child_safety;
    @SerializedName("total") String total;

    protected DiseasePreventionListResponse(Parcel in) {
        id = in.readString();
        village_id = in.readString();
        inspection_date = in.readString();
        village_name = in.readString();
        cleanliness = in.readString();
        malaria = in.readString();
        nutrition = in.readString();
        maternal_safety = in.readString();
        child_safety = in.readString();
        total = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(village_id);
        dest.writeString(inspection_date);
        dest.writeString(village_name);
        dest.writeString(cleanliness);
        dest.writeString(malaria);
        dest.writeString(nutrition);
        dest.writeString(maternal_safety);
        dest.writeString(child_safety);
        dest.writeString(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DiseasePreventionListResponse> CREATOR = new Creator<DiseasePreventionListResponse>() {
        @Override
        public DiseasePreventionListResponse createFromParcel(Parcel in) {
            return new DiseasePreventionListResponse(in);
        }

        @Override
        public DiseasePreventionListResponse[] newArray(int size) {
            return new DiseasePreventionListResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public String getVillage_name() {
        return village_name;
    }

    public String getCleanliness() {
        return cleanliness;
    }

    public String getMalaria() {
        return malaria;
    }

    public String getNutrition() {
        return nutrition;
    }

    public String getMaternal_safety() {
        return maternal_safety;
    }

    public String getChild_safety() {
        return child_safety;
    }

    public String getTotal() {
        return total;
    }
}
