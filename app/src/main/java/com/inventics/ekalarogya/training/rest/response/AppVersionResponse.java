package com.inventics.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;

public class AppVersionResponse extends BaseResponse {

    @SerializedName("force_update")
    private int forceUpdate;


    @SerializedName("update_required")
    private int updateRequired;

    public int getForceUpdate() {
        return forceUpdate;
    }

    public int getUpdateRequired() {
        return updateRequired;
    }
}
