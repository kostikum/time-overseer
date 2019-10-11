package com.kostikum.timeoverseer.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "process_table")
public class Process {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "project")
    private String mProject;

    @ColumnInfo(name = "duration")
    private String mDuration;

    public Process(@NonNull String project, @NonNull String duration) {
        this.mProject = project;
        this.mDuration = duration;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getProject() {
        return mProject;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        this.mDuration = duration;
    }
}
