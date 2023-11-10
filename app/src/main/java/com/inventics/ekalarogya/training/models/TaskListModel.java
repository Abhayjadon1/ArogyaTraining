package com.inventics.ekalarogya.training.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TaskListModel implements Parcelable {

    @SerializedName("id")String id;
    @SerializedName("task_title")String task_title;
    @SerializedName("task_description")String task_description;
    @SerializedName("start_date")String start_date;
    @SerializedName("end_date")String end_date;
    @SerializedName("task_status")String status;
    @SerializedName("progress")String progress;

    protected TaskListModel(Parcel in) {
        id = in.readString();
        task_title = in.readString();
        task_description = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        status = in.readString();
        progress = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(task_title);
        dest.writeString(task_description);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(status);
        dest.writeString(progress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskListModel> CREATOR = new Creator<TaskListModel>() {
        @Override
        public TaskListModel createFromParcel(Parcel in) {
            return new TaskListModel(in);
        }

        @Override
        public TaskListModel[] newArray(int size) {
            return new TaskListModel[size];
        }
    };

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

    public String getStatus() {
        return status;
    }
    public String getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        return "TaskListModel{" +
                "id='" + id + '\'' +
                ", task_title='" + task_title + '\'' +
                ", task_description='" + task_description + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", task_status='" + status + '\'' +
                ", progress='" + progress + '\'' +
                '}';
    }
}
