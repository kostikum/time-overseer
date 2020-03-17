package com.kostikum.timeoverseer.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.kostikum.timeoverseer.db.entity.Process;
import com.kostikum.timeoverseer.db.entity.ProcessWithProject;

import org.joda.time.LocalDate;

import java.util.List;

@Dao
public interface ProcessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Process process);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Process process);

    @Query("SELECT * FROM process_table")
    LiveData<List<Process>> getAllProcesses();

    @Query("SELECT * FROM process_table WHERE localDate = :localDate")
    LiveData<List<ProcessWithProject>> getProcessesWithProjectsByDate(LocalDate localDate);

    @Transaction
    @Query("SELECT * FROM process_table")
    LiveData<List<ProcessWithProject>> getProcessesWithProjects();
}
