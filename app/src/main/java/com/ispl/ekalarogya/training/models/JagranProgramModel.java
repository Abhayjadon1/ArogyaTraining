package com.ispl.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JagranProgramModel implements Parcelable {
    @SerializedName("id")String id;
    @SerializedName("sevika_id")String sevika_id;
    @SerializedName("village_id")String village_id;
    @SerializedName("village_name")String village_name;
    @SerializedName("meeting_date")String meeting_date;
    @SerializedName("program_date")String program_date;
    @SerializedName("year")String year;
    @SerializedName("month")String month;
    @SerializedName("programs")String programs;
    @SerializedName("total_present")String total_present;
    @SerializedName("jagran_id")String jagran_id;
    @SerializedName("total")String total;
    @SerializedName("program_list")
    List<JagranProgramList> program_list;
    @SerializedName("program_data")
    String program_details;
//    List<JagranProgramList> program_details;

    @SerializedName("healthJagran")int healthJagran;
    @SerializedName("program_count")int program_count;
    @SerializedName("soakPit")int soakPit;
    @SerializedName("wastePit")int wastePit;
    @SerializedName("wallWriting")int wallWriting;
    @SerializedName("peopleToilet")int peopleToilet;
    @SerializedName("medicinePlant")int medicinePlant;
    @SerializedName("nutritionPlant")int nutritionPlant;
    @SerializedName("filterUses")int filterUses;
    @SerializedName("treePlanting")int treePlanting;
    @SerializedName("program_ids")List<String> program_ids;
    @SerializedName("plant_names")List<String> plant_names;
    @SerializedName("images")List<ImageList> images;
//    @SerializedName("nutritionPlant")int nutritionPlant;




//    public JagranProgramModel( List<JagranProgramList> program_details) {
//        this.program_list = program_list;
//        this.program_details = program_details;
//
//    }


    protected JagranProgramModel(Parcel in) {
        id = in.readString();
        sevika_id = in.readString();
        village_id = in.readString();
        village_name = in.readString();
        meeting_date = in.readString();
        program_date = in.readString();
        year = in.readString();
        month = in.readString();
        programs = in.readString();
        total_present = in.readString();
        jagran_id = in.readString();
        total = in.readString();
        program_list = in.createTypedArrayList(JagranProgramList.CREATOR);
        program_details = in.readString();
//        program_details = in.createTypedArrayList(JagranProgramList.CREATOR);
        healthJagran = in.readInt();
        program_count = in.readInt();
        soakPit = in.readInt();
        wastePit = in.readInt();
        wallWriting = in.readInt();
        peopleToilet = in.readInt();
        medicinePlant = in.readInt();
        nutritionPlant = in.readInt();
        filterUses = in.readInt();
        treePlanting = in.readInt();
        program_ids = in.createStringArrayList();
        plant_names = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sevika_id);
        dest.writeString(village_id);
        dest.writeString(village_name);
        dest.writeString(meeting_date);
        dest.writeString(program_date);
        dest.writeString(year);
        dest.writeString(month);
        dest.writeString(programs);
        dest.writeString(total_present);
        dest.writeString(jagran_id);
        dest.writeString(total);
        dest.writeTypedList(program_list);
        dest.writeString(program_details);
//        dest.writeTypedList(program_details);
        dest.writeInt(healthJagran);
        dest.writeInt(program_count);
        dest.writeInt(soakPit);
        dest.writeInt(wastePit);
        dest.writeInt(wallWriting);
        dest.writeInt(peopleToilet);
        dest.writeInt(medicinePlant);
        dest.writeInt(nutritionPlant);
        dest.writeInt(filterUses);
        dest.writeInt(treePlanting);
        dest.writeStringList(program_ids);
        dest.writeStringList(plant_names);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JagranProgramModel> CREATOR = new Creator<JagranProgramModel>() {
        @Override
        public JagranProgramModel createFromParcel(Parcel in) {
            return new JagranProgramModel(in);
        }

        @Override
        public JagranProgramModel[] newArray(int size) {
            return new JagranProgramModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getSevika_id() {
        return sevika_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public String getMeeting_date() {
        return meeting_date;
    }

    public String getProgram_date() {
        return program_date;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getPrograms() {
        return programs;
    }

    public String getTotal_present() {
        return total_present;
    }

    public String getJagran_id() {
        return jagran_id;
    }

    public String getTotal() {
        return total;
    }

    public int getProgram_count() {
        return program_count;
    }

    public List<JagranProgramList> getProgram_list() {
        return program_list;
    }

//    public List<JagranProgramList> getProgram_details() {
//        return program_details;
//    }


    public String getProgram_details() {
        return program_details;
    }

    public int getHealthJagran() {
        return healthJagran;
    }

    public int getSoakPit() {
        return soakPit;
    }

    public int getWastePit() {
        return wastePit;
    }

    public int getWallWriting() {
        return wallWriting;
    }

    public int getPeopleToilet() {
        return peopleToilet;
    }

    public int getMedicinePlant() {
        return medicinePlant;
    }

    public int getNutritionPlant() {
        return nutritionPlant;
    }

    public int getFilterUses() {
        return filterUses;
    }

    public int getTreePlanting() {
        return treePlanting;
    }

    public List<String> getProgram_ids() {
        return program_ids;
    }

    public List<ImageList> getImages() {
        return images;
    }

    public List<String> getPlant_names() {
        return plant_names;
    }
}
