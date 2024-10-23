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

import com.example.jobby_oficial.Model.Avaliation;

import java.util.List;

@Dao
public interface AvaliationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAvaliation(List<Avaliation> avaliationList);

    @Delete
    void deleteAvaliationList(List<Avaliation> avaliationList);

    /*@Query("UPDATE avaliation_table SET username=:username WHERE id=:id")
    void updateScheduleUsername(String username, int id);*/

    @Query("SELECT * FROM avaliation_table ORDER BY id ASC")
    LiveData<List<Avaliation>> getAllAvaliations();

    @Query("DELETE FROM avaliation_table")
    void deleteAll();
}
