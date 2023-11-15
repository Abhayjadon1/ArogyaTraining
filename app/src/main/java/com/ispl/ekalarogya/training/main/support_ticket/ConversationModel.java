package com.ispl.ekalarogya.training.main.support_ticket;

import com.google.gson.annotations.SerializedName;

public class ConversationModel {
    @SerializedName("id")
    int id;
    @SerializedName("comment")
    String comment;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("user_img")
    String user_img;
    @SerializedName("userName")
    String userName;
    @SerializedName("customerName")
    String customerName;


    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUser_img() {
        return user_img;
    }

    public String getUserName() {
        return userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return "ConversationModel{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", created_at='" + created_at + '\'' +
                ", user_img='" + user_img + '\'' +
                ", userName='" + userName + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}

