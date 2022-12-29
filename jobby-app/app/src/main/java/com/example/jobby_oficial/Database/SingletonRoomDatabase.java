/*
 * Copyright (c) $year. Guilherme Cruz
 */

package com.example.jobby_oficial.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.jobby_oficial.Dao.CategoryDao;
import com.example.jobby_oficial.Dao.UsersDao;
import com.example.jobby_oficial.Model.Category;
import com.example.jobby_oficial.Model.Users;

@Database(entities = {Users.class, Category.class}, version = 1)
public abstract class SingletonRoomDatabase extends RoomDatabase {

    public abstract UsersDao usersDao();
    public abstract CategoryDao categoryDao();

    private static volatile SingletonRoomDatabase INSTANCE;

    public static SingletonRoomDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SingletonRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SingletonRoomDatabase.class, "Jobby_Database")
                            .fallbackToDestructiveMigration().addCallback(roomCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private UsersDao usersDao;
        private CategoryDao categoryDao;

        private PopulateDbAsyncTask(SingletonRoomDatabase db){
            usersDao = db.usersDao();
            categoryDao = db.categoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*usersDao.insertUser(new Users(
                    "guilherme",
                    "123",
                    "img.jpg",
                    "Guilherme Cruz",
                    "guilhermecruz@gmail.com",
                    "912345678",
                    "Masculino",
                    "28-01-2000"));

            usersDao.insertUser(new Users(
                    "daniela",
                    "123",
                    "img.jpg",
                    "Daniela Ribeiro",
                    "danielaribeiro@gmail.com",
                    "912345678",
                    "Feminino",
                    "08-12-2000"));*/
            categoryDao.deleteAll();
            return null;
        }
    }
}
