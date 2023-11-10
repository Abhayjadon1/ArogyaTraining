package com.inventics.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;
import com.inventics.ekalarogya.training.models.VanAushadhiModelDetails;

public class VanAushadhiDeialResponse {

    @SerializedName("status") String status;
    @SerializedName("list_name") String list_name;
    @SerializedName("van_aushadhi_detail")
    VanAushadhiModelDetails vanAusadhiDetailModelRsp;
    @SerializedName("message") String message;
    @SerializedName("code") String code;




    public String getStatus() {
        return status;
    }

    public String getList_name() {
        return list_name;
    }

    public VanAushadhiModelDetails getVanAusadhiDetailModelRsp() {
        return vanAusadhiDetailModelRsp;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

}
