package com.ispl.ekalarogya.training.helper;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ProductImageModel implements Parcelable {
    public static final int LOCAL_IMAGE_TYPE = 1;
    public static final int REMOTE_IMAGE_TYPE = 2;
    public static final Creator<ProductImageModel> CREATOR = new Creator<ProductImageModel>() {
        @Override
        public ProductImageModel createFromParcel(Parcel in) {
            return new ProductImageModel(in);
        }


        @Override
        public ProductImageModel[] newArray(int size) {
            return new ProductImageModel[size];
        }
    };
    String id;
    private String imagePath;
    private int imageType;
    private Uri imageURI;

    public ProductImageModel() {

    }

    protected ProductImageModel(Parcel in) {
        imagePath = in.readString();
        imageType = in.readInt();
        imageURI = in.readParcelable(Uri.class.getClassLoader());
        id = in.readString();
    }

    public static int getLocalImageType() {
        return LOCAL_IMAGE_TYPE;
    }

    public static int getRemoteImageType() {
        return REMOTE_IMAGE_TYPE;
    }

    public static Creator<ProductImageModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
        dest.writeInt(imageType);
        dest.writeParcelable(imageURI, flags);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public Uri getImageURI() {
        return imageURI;
    }

    public void setImageURI(Uri imageURI) {
        this.imageURI = imageURI;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
