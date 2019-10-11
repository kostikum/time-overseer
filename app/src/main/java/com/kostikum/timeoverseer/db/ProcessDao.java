package com.kostikum.timeoverseer.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kostikum.timeoverseer.entity.Process;

import java.util.List;

@Dao
public interface ProcessDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Process process);

    @Query("SELECT * FROM process_table")
    LiveData<List<Process>> getAllProcesses();
}
