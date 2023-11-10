package com.inventics.ekalarogya.training.rest.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sonu on 16:05, 2019-07-21
 * Copyright (c) 2019 . All rights reserved.
 */
public class FeedbackResponse extends BaseResponse{
    @SerializedName("Hioid")
    private long orderId;

    public long getOrderId() {
        return orderId;
    }
}
