package com.ispl.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;
import com.ispl.ekalarogya.training.models.DashBoardModel;

import java.util.List;

public class DashBoardResponse {

    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;

    @SerializedName("dashboard")
    List<DashBoardModel> dashBoardResponseList;
    public List<DashBoardModel> getDashBoardResponseList() {
        return dashBoardResponseList;
    }

//    public DashBoardResponse(List<DashBoardModel> dashBoardResponseList) {
//        this.dashBoardResponseList = dashBoardResponseList;
//    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
