/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.jobby_oficial.Model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(List<Category> categoryList);

    @Delete
    void deleteCategory(List<Category> categoryList);

    @Query("SELECT * FROM category_table ORDER BY id DESC")
    LiveData<List<Category>> getAllCategorys();

    @Query("DELETE FROM category_table")
    void deleteAll();
}
