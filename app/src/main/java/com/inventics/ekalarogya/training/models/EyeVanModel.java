package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class EyeVanModel implements Parcelable {
    @SerializedName("name")String name;
    @SerializedName("member_name")String member_name;
    @SerializedName("member_id")String member_id;
    @SerializedName("id")String id;
    @SerializedName("sevika_id")String sevika_id;
    @SerializedName("guest_patient_name")String guest_patient_name;
    @SerializedName("patient_mobile")String patient_mobile;
    @SerializedName("village_id")String village_id;
    @SerializedName("head_id")String head_id;
    @SerializedName("gender")String gender;
    @SerializedName("patient_code")String patient_code;
    @SerializedName("test_date")String test_date;
    @SerializedName("patient_family_id")String patient_family_id;
    @SerializedName("dob")String dob;
    @SerializedName("age")String age;
    @SerializedName("vision_defect")String vision_defect;
    @SerializedName("remark")String remark;

    //EyeVan____________________________________________
    @SerializedName("power")String power;
    @SerializedName("right_power")String right_power;
    @SerializedName("left_power")String left_power;
    @SerializedName("eap_id")String eap_id;
    @SerializedName("problem")String problem;
    @SerializedName("medicine_issue")String medicine_issue;
    @SerializedName("glasses_issue")String glasses_issue;
    @SerializedName("glasses_fee")String glasses_fee;
    @SerializedName("referred_hospital")String referred_hospital;
    @SerializedName("r_dist_sph")String r_dist_sph;
    @SerializedName("r_dist_cyl")String r_dist_cyl;
    @SerializedName("r_dist_axis")String r_dist_axis;
    @SerializedName("l_dist_sph")String l_dist_sph;
    @SerializedName("l_dist_cyl")String l_dist_cyl;
    @SerializedName("l_dist_axis")String l_dist_axis;
    @SerializedName("r_reading_cyl")String r_reading_cyl;
    @SerializedName("r_reading_axis")String r_reading_axis;
    @SerializedName("l_reading_sph")String l_reading_sph;
    @SerializedName("l_reading_cyl")String l_reading_cyl;
    @SerializedName("l_reading_axis")String l_reading_axis;
    @SerializedName("photo")String photo;
    @SerializedName("getMedicine_fee")String medicine_fee;
//    @SerializedName("right_power")String right_power;
//    @SerializedName("left_power")String left_power;


    protected EyeVanModel(Parcel in) {
        name = in.readString();
        member_name = in.readString();
        member_id = in.readString();
        id = in.readString();
        sevika_id = in.readString();
        guest_patient_name = in.readString();
        patient_mobile = in.readString();
        village_id = in.readString();
        head_id = in.readString();
        gender = in.readString();
        patient_code = in.readString();
        test_date = in.readString();
        patient_family_id = in.readString();
        dob = in.readString();
        age = in.readString();
        vision_defect = in.readString();
        remark = in.readString();
        power = in.readString();
        right_power = in.readString();
        left_power = in.readString();
        eap_id = in.readString();
        problem = in.readString();
        medicine_issue = in.readString();
        glasses_issue = in.readString();
        glasses_fee = in.readString();
        referred_hospital = in.readString();
        r_dist_sph = in.readString();
        r_dist_cyl = in.readString();
        r_dist_axis = in.readString();
        l_dist_sph = in.readString();
        l_dist_cyl = in.readString();
        l_dist_axis = in.readString();
        r_reading_cyl = in.readString();
        r_reading_axis = in.readString();
        l_reading_sph = in.readString();
        l_reading_cyl = in.readString();
        l_reading_axis = in.readString();
        photo = in.readString();
        medicine_fee = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(member_name);
        dest.writeString(member_id);
        dest.writeString(id);
        dest.writeString(sevika_id);
        dest.writeString(guest_patient_name);
        dest.writeString(patient_mobile);
        dest.writeString(village_id);
        dest.writeString(head_id);
        dest.writeString(gender);
        dest.writeString(patient_code);
        dest.writeString(test_date);
        dest.writeString(patient_family_id);
        dest.writeString(dob);
        dest.writeString(age);
        dest.writeString(vision_defect);
        dest.writeString(remark);
        dest.writeString(power);
        dest.writeString(right_power);
        dest.writeString(left_power);
        dest.writeString(eap_id);
        dest.writeString(problem);
        dest.writeString(medicine_issue);
        dest.writeString(glasses_issue);
        dest.writeString(glasses_fee);
        dest.writeString(referred_hospital);
        dest.writeString(r_dist_sph);
        dest.writeString(r_dist_cyl);
        dest.writeString(r_dist_axis);
        dest.writeString(l_dist_sph);
        dest.writeString(l_dist_cyl);
        dest.writeString(l_dist_axis);
        dest.writeString(r_reading_cyl);
        dest.writeString(r_reading_axis);
        dest.writeString(l_reading_sph);
        dest.writeString(l_reading_cyl);
        dest.writeString(l_reading_axis);
        dest.writeString(photo);
        dest.writeString(medicine_fee);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EyeVanModel> CREATOR = new Creator<EyeVanModel>() {
        @Override
        public EyeVanModel createFromParcel(Parcel in) {
            return new EyeVanModel(in);
        }

        @Override
        public EyeVanModel[] newArray(int size) {
            return new EyeVanModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getId() {
        return id;
    }

    public String getSevika_id() {
        return sevika_id;
    }

    public String getGuest_patient_name() {
        return guest_patient_name;
    }

    public String getPatient_mobile() {
        return patient_mobile;
    }

    public String getVillage_id() {
        return village_id;
    }

    public String getHead_id() {
        return head_id;
    }

    public String getGender() {
        return gender;
    }

    public String getPatient_code() {
        return patient_code;
    }

    public String getTest_date() {
        return test_date;
    }

    public String getPatient_family_id() {
        return patient_family_id;
    }

    public String getDob() {
        return dob;
    }

    public String getAge() {
        return age;
    }

    public String getVision_defect() {
        return vision_defect;
    }

    public String getRemark() {
        return remark;
    }

    public String getPower() {
        return power;
    }

    public String getRight_power() {
        return right_power;
    }

    public String getLeft_power() {
        return left_power;
    }

    public String getEap_id() {
        return eap_id;
    }

    public String getProblem() {
        return problem;
    }

    public String getMedicine_issue() {
        return medicine_issue;
    }

    public String getGlasses_issue() {
        return glasses_issue;
    }

    public String getGlasses_fee() {
        return glasses_fee;
    }

    public String getReferred_hospital() {
        return referred_hospital;
    }

    public String getR_dist_sph() {
        return r_dist_sph;
    }

    public String getR_dist_cyl() {
        return r_dist_cyl;
    }

    public String getR_dist_axis() {
        return r_dist_axis;
    }

    public String getL_dist_sph() {
        return l_dist_sph;
    }

    public String getL_dist_cyl() {
        return l_dist_cyl;
    }

    public String getL_dist_axis() {
        return l_dist_axis;
    }

    public String getR_reading_cyl() {
        return r_reading_cyl;
    }

    public String getR_reading_axis() {
        return r_reading_axis;
    }

    public String getL_reading_sph() {
        return l_reading_sph;
    }

    public String getL_reading_cyl() {
        return l_reading_cyl;
    }

    public String getL_reading_axis() {
        return l_reading_axis;
    }

    public String getPhoto() {
        return photo;
    }

    public String getMedicine_fee() {
        return medicine_fee;
    }

    @Override
    public String toString() {
        return "EyeVanModel{" +
                "name='" + name + '\'' +
                ", member_name='" + member_name + '\'' +
                ", member_id='" + member_id + '\'' +
                ", id='" + id + '\'' +
                ", sevika_id='" + sevika_id + '\'' +
                ", guest_patient_name='" + guest_patient_name + '\'' +
                ", patient_mobile='" + patient_mobile + '\'' +
                ", village_id='" + village_id + '\'' +
                ", head_id='" + head_id + '\'' +
                ", gender='" + gender + '\'' +
                ", patient_code='" + patient_code + '\'' +
                ", test_date='" + test_date + '\'' +
                ", patient_family_id='" + patient_family_id + '\'' +
                ", dob='" + dob + '\'' +
                ", age='" + age + '\'' +
                ", vision_defect='" + vision_defect + '\'' +
                ", remark='" + remark + '\'' +
                ", power='" + power + '\'' +
                ", right_power='" + right_power + '\'' +
                ", left_power='" + left_power + '\'' +
                ", eap_id='" + eap_id + '\'' +
                ", problem='" + problem + '\'' +
                ", medicine_issue='" + medicine_issue + '\'' +
                ", glasses_issue='" + glasses_issue + '\'' +
                ", glasses_fee='" + glasses_fee + '\'' +
                ", referred_hospital='" + referred_hospital + '\'' +
                ", r_dist_sph='" + r_dist_sph + '\'' +
                ", r_dist_cyl='" + r_dist_cyl + '\'' +
                ", r_dist_axis='" + r_dist_axis + '\'' +
                ", l_dist_sph='" + l_dist_sph + '\'' +
                ", l_dist_cyl='" + l_dist_cyl + '\'' +
                ", l_dist_axis='" + l_dist_axis + '\'' +
                ", r_reading_cyl='" + r_reading_cyl + '\'' +
                ", r_reading_axis='" + r_reading_axis + '\'' +
                ", l_reading_sph='" + l_reading_sph + '\'' +
                ", l_reading_cyl='" + l_reading_cyl + '\'' +
                ", l_reading_axis='" + l_reading_axis + '\'' +
                ", photo='" + photo + '\'' +
                ", medicine_fee='" + medicine_fee + '\'' +
                '}';
    }
}
