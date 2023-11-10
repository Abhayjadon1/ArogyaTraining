package com.inventics.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sonu on 15:28, 2019-06-18
 * Copyright (c) 2019 . All rights reserved.
 */
public class ServiceResponse extends BaseResponse {

    @SerializedName("text_message")
    String textMessage;
    @SerializedName("service_list")
    private ArrayList<Service> serviceList;

    public String getTextMessage() {
        return textMessage;
    }

    public ArrayList<Service> getServiceList() {
        return serviceList;
    }
}
