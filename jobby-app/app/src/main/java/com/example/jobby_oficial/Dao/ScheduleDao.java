/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.jobby_oficial.Model.Schedule;

import java.util.List;

@Dao
public interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSchedule(List<Schedule> scheduleList);

    @Delete
    void deleteScheduleList(List<Schedule> scheduleList);

    @Query("UPDATE schedule_table SET username=:username WHERE id=:id")
    void updateScheduleUsername(String username, int id);

    @Query("SELECT * FROM schedule_table ORDER BY id ASC")
    LiveData<List<Schedule>> getAllSchedules();

    @Query("DELETE FROM schedule_table")
    void deleteAll();
}
