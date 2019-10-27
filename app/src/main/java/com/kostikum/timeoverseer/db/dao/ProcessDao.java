package com.kostikum.timeoverseer.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.entity.ProcessWithProject;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Dao
public interface ProcessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Process process);

    @Query("SELECT * FROM process_table")
    LiveData<List<Process>> getAllProcesses();

    @Query("SELECT * FROM process_table WHERE date = :date")
    List<Process> getProcessesByDate(Date date);

    @Query("SELECT * FROM process_table")
    LiveData<List<ProcessWithProject>> getProcessesWithProjects();
}
