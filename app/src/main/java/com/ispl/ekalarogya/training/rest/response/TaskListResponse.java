package com.ispl.ekalarogya.training.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ispl.ekalarogya.training.models.TaskStatus;

public class TaskListResponse implements Parcelable{
    @SerializedName("task_detail")
    private String task_detail;

    @SerializedName("id")String id;
    @SerializedName("title")String task_title;
    @SerializedName("description")String task_description;
    @SerializedName("start_date")String start_date;
    @SerializedName("end_date")String end_date;
    @SerializedName("task_status")
    TaskStatus task_status;
    @SerializedName("progress")String progress;


    protected TaskListResponse(Parcel in) {
        task_detail = in.readString();
        id = in.readString();
        task_title = in.readString();
        task_description = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        task_status = in.readParcelable(TaskStatus.class.getClassLoader());
        progress = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(task_detail);
        dest.writeString(id);
        dest.writeString(task_title);
        dest.writeString(task_description);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeParcelable(task_status, flags);
        dest.writeString(progress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskListResponse> CREATOR = new Creator<TaskListResponse>() {
        @Override
        public TaskListResponse createFromParcel(Parcel in) {
            return new TaskListResponse(in);
        }

        @Override
        public TaskListResponse[] newArray(int size) {
            return new TaskListResponse[size];
        }
    };

    public String getTask_detail() {
        return task_detail;
    }

    public String getId() {
        return id;
    }

    public String getTask_title() {
        return task_title;
    }

    public String getTask_description() {
        return task_description;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public TaskStatus getTask_status() {
        return task_status;
    }

    public String getProgress() {
        return progress;
    }
}

