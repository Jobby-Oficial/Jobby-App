/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Dao.CategoryDao;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.Category;

import java.util.List;

public class CategoryRepository {

    public SingletonRoomDatabase database;
    private LiveData<List<Category>> getAllCategorys;

    public CategoryRepository(Application application){
        database = SingletonRoomDatabase.getInstance(application);
        getAllCategorys = database.categoryDao().getAllCategorys();
    }

    public void insert(List<Category> categoryList){
        new CategoryRepository.InsertAsyncTask(database).execute(categoryList);
    }

    public LiveData<List<Category>> getAllCategorys(){
        return getAllCategorys;
    }

    public static class InsertAsyncTask extends AsyncTask<List<Category>, Void, Void> {
        private CategoryDao categoryDao;

        private InsertAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.categoryDao = singletonRoomDatabase.categoryDao();
        }

        @Override
        protected Void doInBackground(List<Category>... lists) {
            categoryDao.deleteAll();
            categoryDao.insertCategory(lists[0]);
            return null;
        }
    }
}
