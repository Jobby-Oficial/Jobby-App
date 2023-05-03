/*
 * Created by Guilherme Cruz
 * Last modified: 20/01/22, 09:10
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

import com.example.jobby_oficial.Model.Username;

import java.util.List;

@Dao
public interface UsernameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsername(List<Username> username);

    @Delete
    void deleteUsername(Username usernameList);

    @Query("SELECT * FROM username_table ORDER BY id ASC")
    LiveData<List<Username>> getAllUsernames();

    @Query("DELETE FROM username_table")
    void deleteAll();
}
