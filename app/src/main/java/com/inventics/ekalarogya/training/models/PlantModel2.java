package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlantModel2  implements Parcelable {
    @SerializedName("id")int id;
    @SerializedName("plant_name")String plant_name;
    @SerializedName("plant_img") String plant_img;
    @SerializedName("image") String image;
    @SerializedName("count") int count;
    boolean isSelect;

    public PlantModel2(int id, String plant_name, String plant_img, String image, int count, boolean isSelect) {
        this.id = id;
        this.plant_name = plant_name;
        this.plant_img = plant_img;
        this.image = image;
        this.count = count;
        this.isSelect = isSelect;
    }



    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    PlantModel2(boolean isSelect){
        this.isSelect=isSelect;
    }

    protected PlantModel2(Parcel in) {
        id = in.readInt();
        count = in.readInt();
        plant_name = in.readString();
        plant_img = in.readString();
        image = in.readString();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<PlantModel2> CREATOR = new Creator<PlantModel2>() {
        @Override
        public PlantModel2 createFromParcel(Parcel in) {
            return new PlantModel2(in);
        }

        @Override
        public PlantModel2[] newArray(int size) {
            return new PlantModel2[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
    }

    public String getPlant_img() {
        return plant_img;
    }

    public String getImage() {
        return image;
    }

    public void setPlant_img(String plant_img) {
        this.plant_img = plant_img;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "PlantModel2{" +
                "id=" + id +
                ", plant_name='" + plant_name + '\'' +
                ", plant_img='" + plant_img + '\'' +
                ", image='" + image + '\'' +
                ", count=" + count +
                ", isSelect=" + isSelect +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(count);
        parcel.writeString(plant_name);
        parcel.writeString(plant_img);
        parcel.writeString(image);
        parcel.writeByte((byte) (isSelect ? 1 : 0));
    }
}
