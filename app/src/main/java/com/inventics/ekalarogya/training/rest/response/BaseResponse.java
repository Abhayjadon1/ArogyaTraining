package com.inventics.ekalarogya.training.rest.response;


import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.inventics.ekalarogya.training.main.support_ticket.SupportTicket;
import com.inventics.ekalarogya.training.models.AddFamilyHeadModel;
import com.inventics.ekalarogya.training.models.AnemiaCheckupModel;
import com.inventics.ekalarogya.training.models.ArogyaCampModel;
import com.inventics.ekalarogya.training.models.DashBoardModel;
import com.inventics.ekalarogya.training.models.EyeVanModel;
import com.inventics.ekalarogya.training.models.FamilyHeadDetailsModelResponse;
import com.inventics.ekalarogya.training.models.FamilyMembersModel;
import com.inventics.ekalarogya.training.models.FirstAidModel;
import com.inventics.ekalarogya.training.models.InspectionStayModel;
import com.inventics.ekalarogya.training.models.JagranProgramModel;
import com.inventics.ekalarogya.training.models.MalnutrinAnemiaCheckupModelResponse;
import com.inventics.ekalarogya.training.models.MalnutrinCheckupModelResponse;
import com.inventics.ekalarogya.training.models.MedicineGardenModel;
import com.inventics.ekalarogya.training.models.PersonalHygineListModel;
import com.inventics.ekalarogya.training.models.PosterDisplayModel;
import com.inventics.ekalarogya.training.models.ProfileModelDetails;
import com.inventics.ekalarogya.training.models.SamitiModel;
import com.inventics.ekalarogya.training.models.SevikaModel;
import com.inventics.ekalarogya.training.models.SocialSanitisationModel;
import com.inventics.ekalarogya.training.models.TaskListModel;
import com.inventics.ekalarogya.training.models.VanaushadhiModel;
import com.inventics.ekalarogya.training.models.VillageFamilyModel;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.models.VillageVisitModel;

import java.util.List;

public class BaseResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("Status")
    private String statusHelp;

 @SerializedName("gkv_file_path")
    private String gkv_file_path;


    public String getStatusHelp() {
        return statusHelp;
    }

    @SerializedName("message")
    private String statusMessage;
    @SerializedName("Message")
    private String statusMessageforhelp;
    @SerializedName("inspection_img")
    private String inspectionImg;

    public String getStatusMessageforhelp() {
        return statusMessageforhelp;
    }



    @SerializedName("code")
    private String errorCode;
    @SerializedName("Image")
    private String Image;

    public String getInspection_img() {
        return inspectionImg;
    }
    public String getImage() {
        return Image;
    }

    public String getGkv_file_path() {
        return gkv_file_path;
    }

    @SerializedName("uploadImage")
    String uploadImage;
    @SerializedName("qr")
    String qr;
    @SerializedName("Ticket_list")
    List<SupportTicket> Ticket_list;
    @SerializedName("family_visit_list")
    List<FamilyVisitModel> familyVisitModelList;


    public String getUploadImage() {
        return uploadImage;
    }
    @SerializedName("ViewTicket")
    SupportTicket viewTicket;

    public SupportTicket getViewTicket() {
        return viewTicket;
    }

    public String getQr() {
        return qr;
    }

    public List<SupportTicket> getTicket_list() {
        return Ticket_list;
    }

    @SerializedName("list_name")
    private  String list_name;

    @SerializedName("dashboard")
    List<DashBoardModel> dashBoardModelList;

    @SerializedName("user_profile")
    ProfileModelDetails profileModelDetails;

    @SerializedName("task_list")
    List<TaskListModel> taskListModelList;

    @SerializedName("task_detail")
    TaskListResponse task_detail;



    @SerializedName("villages")
    List<VillageListModel> villageListModelList;

    @SerializedName("families")
    List<VillageFamilyModel>villageFamilyModelList;

    public List<VillageFamilyModel> getVillageFamilyModelList() {
        return villageFamilyModelList;
    }

    @SerializedName("add_family_head")
    AddFamilyHeadModel addFamilyHeadModel;

    @SerializedName("head_details")
    com.inventics.ekalarogya.training.models.FamilyHeadDetailsModelResponse familyHeadDetailsModelResponse;

    public com.inventics.ekalarogya.training.models.FamilyHeadDetailsModelResponse getFamilyHeadDetailsModel() {
        return familyHeadDetailsModelResponse;
    }

    @SerializedName("family_member_list")
    List<FamilyMembersModel> familyMembersModelList;

    @SerializedName("member_list")
    List<FamilyMembersModel> familyMembersModelListNew;


    public List<FamilyMembersModel> getFamilyMembersModelListNew() {
        return familyMembersModelListNew;
    }

    @SerializedName("personal_hygiene_list")
    List<PersonalHygineListModel> personal_hygiene_list;


    @SerializedName("anemia_pregnancy_list")
    List<AnemiaCheckupModel> anemiaCheckupModelList;

    @SerializedName("children_list")
    List<FamilyMembersModel> personlHygnChildResponses;

    @SerializedName("personal_hygiene")
    PersonalHygineListModel personalHygieneCheckupResponse;

    @SerializedName("social_sanitation")
    List<SocialSanitisationModel> socialSanitisationModelList;

    @SerializedName("disease_prevention_list")
    List<DiseasePreventionListResponse> diseasePreventionListResponseList;

    @SerializedName("anemia_checkup_list")
    List<MalnutrinAnemiaCheckupModelResponse> malnutrinAnemiaCheckupModelResponseList;

    @SerializedName("sanyojika_list")
    List<SevikaModel> sevikaModelList;


    @SerializedName("anemia_checkup_details")
    AnemiaCheckupDetailsResponse anemiaCheckupDetailsResponse;



    @SerializedName("malnutrition_checkup_list")
    List<MalnutrinCheckupModelResponse> malnutrinCheckupModelResponsesList;
    @SerializedName("malnutrition_checkup_details")
    MalnutrinDetailResponse malnutrinDetailResponses;


    @SerializedName("medicine_garden")
    List<MedicineGardenModel> medicineGardenModelsList;
    @SerializedName("medicine_garden_family_detail")
    List<MedicineGardenModel> medicineGardenModels;

//    @SerializedName("van_aushadhi")
//    List<VanaushadhiModel> vanaushadhiModelList;

    @SerializedName("van_aushadhi_list")
    List<VanaushadhiModel> vanaushadhiModelList;

    @SerializedName("village_visit_detail")
    VillageListModel villageListModelDetails;

    public VillageListModel getVillageListModelDetails() {
        return villageListModelDetails;
    }

    @SerializedName("emergency_handling_list")
    List<FirstAidModel>firstAidModelList;
    @SerializedName("emergency_handling_details")
    FirstAidModel emergencyDetail;
    String path;

    public String getPath() {
        return path;
    }

    @Nullable
    @SerializedName("online")
    private String isOnline;
    @Nullable
    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(@Nullable String isOnline) {
        this.isOnline = isOnline;
    }

    @SerializedName("samiti_meeting_list")
    List<SamitiModel> samitiModelList;

    @SerializedName("samiti_meeting_detail")
    SamitiDetailResponse samitiDetailResponse;

    @SerializedName("eye_screen_list")
    List<EyeVanModel> eyeVanModelList;

    @SerializedName("eye_van_list")
    List<EyeVanModel> eyeVanList;

    @SerializedName("eyevan_add")
    EyeVanModel eyeVanAdd;



    @SerializedName("poster_display_list")
    List<PosterDisplayModel> posterDisplayModelList;

    @SerializedName("poster_display_detail")
    PosterDisplayModel posterDisplayModelDetails;

    @SerializedName("village_visit_list")
    List<VillageVisitModel> villageVisitModelList;

    @SerializedName("arogya_camp_list")
    List<ArogyaCampModel> arogyaCampModels;

    public List<ArogyaCampModel> getArogyaCampModels() {
        return arogyaCampModels;
    }

    @SerializedName("arogya_camp_detail")
    ArogyaCampModel arogyaCampModel;


    @SerializedName("jagran_program_list")
    List<JagranProgramModel> jagranProgramModelList;

    @SerializedName("jagran_program_detail")
    JagranProgramModel jagranProgramModelDetails;

    public List<JagranProgramModel> getJagranProgramModelList() {
        return jagranProgramModelList;
    }
    @SerializedName("inspection_stay_list")
    List<InspectionStayModel> inspectionStayModelList;

    @SerializedName("inspection_stay_detail")
    InspectionStayModel inspectionStayModel;

    public List<InspectionStayModel> getInspectionStayModelList() {
        return inspectionStayModelList;
    }

    public ProfileModelDetails getProfileModelDetails() {
        return profileModelDetails;
    }

    public List<SevikaModel> getSevikaModelList() {
        return sevikaModelList;
    }

    public InspectionStayModel getInspectionStayModel() {
        return inspectionStayModel;
    }

    public JagranProgramModel getJagranProgramModelDetails() {
        return jagranProgramModelDetails;
    }

    public List<FamilyVisitModel> getFamilyVisitModelList() {
        return familyVisitModelList;
    }

    public ArogyaCampModel getArogyaCampModel() {
        return arogyaCampModel;
    }

    public List<VillageVisitModel> getVillageVisitModelList() {
        return villageVisitModelList;
    }

    public PosterDisplayModel getPosterDisplayModelDetails() {
        return posterDisplayModelDetails;
    }

    public List<VillageListModel> getVillageListModelList() {
        return villageListModelList;
    }

    public String getList_name() {
        return list_name;
    }

    public List<DashBoardModel> getDashBoardModelList() {
        return dashBoardModelList;
    }

    public String getStatus() {
        return status;
    }

    public List<EyeVanModel> getEyeVanList() {
        return eyeVanList;
    }

    public EyeVanModel getEyeVanAdd() {
        return eyeVanAdd;
    }

    public FirstAidModel getEmergencyDetail() {
        return emergencyDetail;
    }

    public AddFamilyHeadModel getAddFamilyHeadModel() {
        return addFamilyHeadModel;
    }

    public List<FamilyMembersModel> getFamilyMembersModelList() {
        return familyMembersModelList;
    }

    public List<AnemiaCheckupModel> getAnemiaCheckupModelList() {
        return anemiaCheckupModelList;
    }



    public List<PersonalHygineListModel> getPersonal_hygiene_list() {
        return personal_hygiene_list;
    }

    public List<FamilyMembersModel> getPersonlHygnChildResponses() {
        return personlHygnChildResponses;
    }

    public PersonalHygineListModel getPersonalHygieneCheckupResponse() {
        return personalHygieneCheckupResponse;
    }

    public List<SocialSanitisationModel> getSocialSanitisationModelList() {
        return socialSanitisationModelList;
    }

    public List<DiseasePreventionListResponse> getDiseasePreventionListResponseList() {
        return diseasePreventionListResponseList;
    }

    public List<MalnutrinAnemiaCheckupModelResponse> getMalnutrinAnemiaCheckupModelResponseList() {
        return malnutrinAnemiaCheckupModelResponseList;
    }

    public AnemiaCheckupDetailsResponse getAnemiaCheckupDetailsResponse() {
        return anemiaCheckupDetailsResponse;
    }

    public List<MalnutrinCheckupModelResponse> getMalnutrinCheckupModelResponsesList() {
        return malnutrinCheckupModelResponsesList;
    }

    public MalnutrinDetailResponse getMalnutrinDetailResponses() {
        return malnutrinDetailResponses;
    }

    public TaskListResponse getTask_detail() {
        return task_detail;
    }

    public List<MedicineGardenModel> getMedicineGardenModelsList() {
        return medicineGardenModelsList;
    }

    public FamilyHeadDetailsModelResponse getFamilyHeadDetailsModelResponse() {
        return familyHeadDetailsModelResponse;
    }

    public List<TaskListModel> getTaskListModelList() {
        return taskListModelList;
    }

    public List<EyeVanModel> getEyeVanModelList() {
        return eyeVanModelList;
    }

    public List<PosterDisplayModel> getPosterDisplayModelList() {
        return posterDisplayModelList;
    }

    public PosterDisplayModel getPosterDisplayModel() {
        return posterDisplayModelDetails;
    }

    public List<SamitiModel> getSamitiModelList() {
        return samitiModelList;
    }

    public SamitiDetailResponse getSamitiDetailResponse() {
        return samitiDetailResponse;
    }

    public List<VanaushadhiModel> getVanaushadhiModelList() {
        return vanaushadhiModelList;
    }

    public List<FirstAidModel> getFirstAidModelList() {
        return firstAidModelList;
    }

    public List<MedicineGardenModel> getMedicineGardenModels() {
        return medicineGardenModels;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }


}
