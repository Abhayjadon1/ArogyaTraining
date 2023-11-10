package com.inventics.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sonu on 23:56, 25/08/18
 * Copyright (c) 2018 . All rights reserved.
 */
public class GetConnection extends BaseResponse {
    @SerializedName("connection_id")
    private String connectionId;

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    @Override
    public String toString() {
        return "GetConnection{" +
                "connectionId='" + connectionId + '\'' +
                '}';
    }
}
