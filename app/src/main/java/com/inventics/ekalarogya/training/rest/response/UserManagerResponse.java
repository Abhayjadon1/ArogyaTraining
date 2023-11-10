package com.inventics.ekalarogya.training.rest.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sonu on 17:12, 2019-06-12
 * Copyright (c) 2019 . All rights reserved.
 */
public class UserManagerResponse extends BaseResponse {
    @Nullable
    @SerializedName("user_signin")
    private UserManager userManager;

    @Nullable
    @SerializedName("Data")
    private
    UserManager profileData;

    @Nullable
    public UserManager getUserManager() {
        return userManager;
    }

    @Nullable
    public UserManager getProfileData() {
        return profileData;
    }
}
