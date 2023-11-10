package com.inventics.ekalarogya.training.main.support_ticket;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupportTicket{

    @SerializedName("id")
    int id;
    @SerializedName("user_id")
    int user_id;
    @SerializedName("ticket_id")
    String ticket_id;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("status")
    String status;
    @SerializedName("subject")
    String subject;

    @SerializedName("message")
    String message;
@SerializedName("file_name")
    String file_name;

    @SerializedName("chat")
    List<ConversationModel> chat;

    public String getSubject() {
        return subject;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getStatus() {
        return status;
    }

    public List<ConversationModel> getChat() {
        return chat;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", ticket_id='" + ticket_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}




