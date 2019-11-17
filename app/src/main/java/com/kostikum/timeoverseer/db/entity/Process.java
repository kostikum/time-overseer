package com.kostikum.timeoverseer.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "process_table")//,
//        foreignKeys = @ForeignKey(entity = Project.class,
//                parentColumns = "id",
//                childColumns = "project_id",
//                onDelete = CASCADE))
public class Process {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Date date;
    private long project_id;
    private int duration;

    public Process(Date date, long project_id, int duration) {
        this.date = date;
        this.project_id = project_id;
        this.duration = duration;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

