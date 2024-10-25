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

import com.example.jobby_oficial.Model.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(List<Favorite> favoriteList);

    @Delete
    void deleteFavoriteList(List<Favorite> favoriteList);

    @Query("SELECT * FROM favorite_table ORDER BY id ASC")
    LiveData<List<Favorite>> getAllFavorites();

    @Query("DELETE FROM favorite_table")
    void deleteAll();
}
