package com.haphap.todoapp.Model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Task.TABLE_NAME)
public class Task implements Parcelable {

    @Ignore
    static final String TABLE_NAME = "tasks_table";

    @PrimaryKey(autoGenerate = true)
    private Long id_task;

    @ColumnInfo(name = "userId")
    private Long userId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    private String date;

    @Ignore
    public Task(Long userId, String title, String description, String date) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    @Ignore
    public Task(Long id_task, Long userId, String title, String description, String date) {
        this.userId = userId;
        this.id_task = id_task;
        this.title = title;
        this.description = description;
        this.date = date;
    }


    public Task () {

    }

    public Long getId_task() {
        return id_task;
    }

    public void setId_task(Long id_task) {
        this.id_task = id_task;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id_task == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id_task);
        }
        dest.writeValue(this.userId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
    }

    public Task(Parcel in) {
        if (in.readByte() == 0) {
            id_task = null;
        } else {
            id_task = in.readLong();
        }
        this.userId = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}

