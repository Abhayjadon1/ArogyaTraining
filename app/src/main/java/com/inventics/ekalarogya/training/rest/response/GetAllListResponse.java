package com.inventics.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;
import com.inventics.ekalarogya.training.models.EyeProblemModel;
import com.inventics.ekalarogya.training.models.InspectionModel;
import com.inventics.ekalarogya.training.models.JagranProgramList;
import com.inventics.ekalarogya.training.models.MaritalStatus;
import com.inventics.ekalarogya.training.models.OccupationModel;
import com.inventics.ekalarogya.training.models.PlantModel;
import com.inventics.ekalarogya.training.models.PurposeModel;
import com.inventics.ekalarogya.training.models.RelationModel;
import com.inventics.ekalarogya.training.models.ResponsbilityModel;
import com.inventics.ekalarogya.training.models.TreatmentTypeModel;

import java.util.List;

public class GetAllListResponse {
    @SerializedName("status") String status;
    @SerializedName("occupation_list")
    List<OccupationModel> occupation_list;
    @SerializedName("message")String message;
    @SerializedName("code") String code;
    @SerializedName("relation_list")
    List<RelationModel> relationModelList;
    @SerializedName("marital_status_list")
    List<MaritalStatus> maritalStatusList;

    @SerializedName("responsbility_list")
    List<ResponsbilityModel> responsbilityModels;

    @SerializedName("purpose_list")
    List<PurposeModel> purposeModels;

    @SerializedName("plant_list")
    List<PlantModel> plantModelList;

    @SerializedName("eye_problem_type")
    List<EyeProblemModel> eyeProblemModelList;


    @SerializedName("program_list")
    List<JagranProgramList> jagranProgramLists;

    @SerializedName("treatment_type")
    List<TreatmentTypeModel> treatmentTypes;

    @SerializedName("inspection")
    List<InspectionModel> inspectionModels;

    public List<JagranProgramList> getJagranProgramLists() {
        return jagranProgramLists;
    }

    public String getStatus() {
        return status;
    }

    public List<TreatmentTypeModel> getTreatmentTypes() {
        return treatmentTypes;
    }

    public List<InspectionModel> getInspectionModels() {
        return inspectionModels;
    }

    public List<PurposeModel> getPurposeModels() {
        return purposeModels;
    }

    public List<ResponsbilityModel> getResponsbilityModels() {
        return responsbilityModels;
    }

    public List<MaritalStatus> getMaritalStatusList() {
        return maritalStatusList;
    }

    public List<OccupationModel> getOccupation_list() {
        return occupation_list;
    }

    public List<RelationModel> getRelationModelList() {
        return relationModelList;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public List<PlantModel> getPlantModelList() {
        return plantModelList;
    }
}
