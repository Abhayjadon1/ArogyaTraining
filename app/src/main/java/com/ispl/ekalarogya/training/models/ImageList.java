package com.ispl.ekalarogya.training.models;

import com.google.gson.annotations.SerializedName;

public class ImageList  {
    @SerializedName("image")String images;
    @SerializedName("inspection_img")String inspection_img;

    public ImageList(String images) {
        this.images = images;
    }

    public String getInspection_img() {
        return inspection_img;
    }

    //    public ImageList(Parcel in) {
//        images = in.readString();
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(images);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Creator<ImageList> CREATOR = new Creator<ImageList>() {
//        @Override
//        public ImageList createFromParcel(Parcel in) {
//            return new ImageList(in);
//        }
//
//        @Override
//        public ImageList[] newArray(int size) {
//            return new ImageList[size];
//        }
//    };

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ImageList{" +
                "images='" + images + '\'' +
                '}';
    }
}
