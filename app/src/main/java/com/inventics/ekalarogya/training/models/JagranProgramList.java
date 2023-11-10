package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class JagranProgramList implements Parcelable {

    @SerializedName("id")String id;
    @SerializedName("program_title")String program_title;
    @SerializedName("program_code")String program_code;
    @SerializedName("count")String current_present;
    @SerializedName("prev_present")String prev_present;
    @SerializedName("imgs") String imgs;


    ///////////////////////////
    @SerializedName("title")String title;
    @SerializedName("code")String code;
    @SerializedName("present")String present;
    @SerializedName("image")String image;

    boolean select=false;

    public JagranProgramList(String id, String current_present, String title, String image) {
        this.id = id;
        this.current_present = current_present;
        this.title = title;
        this.image = image;
    }

    protected JagranProgramList(Parcel in) {
        id = in.readString();
        program_title = in.readString();
        program_code = in.readString();
        current_present = in.readString();
        prev_present = in.readString();
        imgs = in.readString();
        title = in.readString();
        code = in.readString();
        present = in.readString();
        image = in.readString();
        select = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(program_title);
        dest.writeString(program_code);
        dest.writeString(current_present);
        dest.writeString(prev_present);
        dest.writeString(imgs);
        dest.writeString(title);
        dest.writeString(code);
        dest.writeString(present);
        dest.writeString(image);
        dest.writeByte((byte) (select ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JagranProgramList> CREATOR = new Creator<JagranProgramList>() {
        @Override
        public JagranProgramList createFromParcel(Parcel in) {
            return new JagranProgramList(in);
        }

        @Override
        public JagranProgramList[] newArray(int size) {
            return new JagranProgramList[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProgram_title() {
        return program_title;
    }

    public void setProgram_title(String program_title) {
        this.program_title = program_title;
    }

    public String getProgram_code() {
        return program_code;
    }

    public void setProgram_code(String program_code) {
        this.program_code = program_code;
    }

    public String getCurrent_present() {
        return current_present;
    }

    public void setCurrent_present(String current_present) {
        this.current_present = current_present;
    }

    public String getPrev_present() {
        return prev_present;
    }

    public void setPrev_present(String prev_present) {
        this.prev_present = prev_present;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }


    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getPresent() {
        return present;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }


    @Override
    public String toString() {
        return "JagranProgramList{" +
                "id='" + id + '\'' +
                ", program_title='" + program_title + '\'' +
                ", program_code='" + program_code + '\'' +
                ", current_present='" + current_present + '\'' +
                ", prev_present='" + prev_present + '\'' +
                ", imgs='" + imgs + '\'' +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", present='" + present + '\'' +
                ", image='" + image + '\'' +
                ", select=" + select +
                '}';
    }
}
