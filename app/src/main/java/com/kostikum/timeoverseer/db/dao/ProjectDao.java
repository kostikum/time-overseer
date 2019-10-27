package com.kostikum.timeoverseer.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kostikum.timeoverseer.db.entity.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getAllProjects();
}
