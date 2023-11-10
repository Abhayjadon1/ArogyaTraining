package com.inventics.ekalarogya.training.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SamitiDetailResponse implements Parcelable {

    @SerializedName("id") String id;
    @SerializedName("village_id") String village_id;
    @SerializedName("village") String village;
    @SerializedName("sevika_id") String sevika_id;
    @SerializedName("meeting_date") String meeting_date;
    @SerializedName("samiti") String samiti;
    @SerializedName("sevika") String sevika;
    @SerializedName("sanyojak") String sanyojak;
    @SerializedName("sevavriti") String sevavriti;
    @SerializedName("total") String total;
    @SerializedName("sanch_coordinator") String sanch_coordinator;
    @SerializedName("remark") String remark;
    @SerializedName("image") String image;

    protected SamitiDetailResponse(Parcel in) {
        id = in.readString();
        village_id = in.readString();
        village = in.readString();
        sevika_id = in.readString();
        meeting_date = in.readString();
        samiti = in.readString();
        sevika = in.readString();
        sanyojak = in.readString();
        sevavriti = in.readString();
        total = in.readString();
        sanch_coordinator = in.readString();
        remark = in.readString();
        image = in.readString();
    }

    public static final Creator<SamitiDetailResponse> CREATOR = new Creator<SamitiDetailResponse>() {
        @Override
        public SamitiDetailResponse createFromParcel(Parcel in) {
            return new SamitiDetailResponse(in);
        }

        @Override
        public SamitiDetailResponse[] newArray(int size) {
            return new SamitiDetailResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getVillage() {
        return village;
    }

    public String getSevika_id() {
        return sevika_id;
    }

    public String getMeeting_date() {
        return meeting_date;
    }

    public String getSamiti() {
        return samiti;
    }

    public String getSevika() {
        return sevika;
    }

    public String getSanyojak() {
        return sanyojak;
    }

    public String getSevavriti() {
        return sevavriti;
    }

    public String getImage() {
        return image;
    }

    public String getTotal() {
        return total;
    }

    public String getRemark() {
        return remark;
    }

    public String getSanch_coordinator() {
        return sanch_coordinator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(village_id);
        parcel.writeString(village);
        parcel.writeString(sevika_id);
        parcel.writeString(meeting_date);
        parcel.writeString(samiti);
        parcel.writeString(sevika);
        parcel.writeString(sanyojak);
        parcel.writeString(sevavriti);
        parcel.writeString(total);
        parcel.writeString(sanch_coordinator);
        parcel.writeString(remark);
        parcel.writeString(image);
    }
}
