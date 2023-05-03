/*
 * Created by Guilherme Cruz
 * Last modified: 25/12/21, 17:06
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.jobby_oficial.Dao.AvaliationDao;
import com.example.jobby_oficial.Dao.CategoryDao;
import com.example.jobby_oficial.Dao.FavoriteDao;
import com.example.jobby_oficial.Dao.ScheduleDao;
import com.example.jobby_oficial.Dao.ServiceDao;
import com.example.jobby_oficial.Dao.UserDao;
import com.example.jobby_oficial.Dao.UsernameDao;
import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Model.Category;
import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Schedule;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.Model.Username;

@Database(entities = {Category.class, User.class, Service.class, Favorite.class, Schedule.class, Username.class, Avaliation.class}, version = 1)
public abstract class SingletonRoomDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();
    public abstract UserDao usersDao();
    public abstract ServiceDao serviceDao();
    public abstract FavoriteDao favoriteDao();
    public abstract ScheduleDao scheduleDao();
    public abstract UsernameDao usernameDao();
    public abstract AvaliationDao avaliationDao();

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
        private CategoryDao categoryDao;
        private UserDao usersDao;
        private ServiceDao serviceDao;
        private FavoriteDao favoriteDao;
        private ScheduleDao scheduleDao;
        private UsernameDao usernameDao;
        private AvaliationDao avaliationDao;

        private PopulateDbAsyncTask(SingletonRoomDatabase db){
            categoryDao = db.categoryDao();
            usersDao = db.usersDao();
            serviceDao = db.serviceDao();
            favoriteDao = db.favoriteDao();
            scheduleDao = db.scheduleDao();
            usernameDao = db.usernameDao();
            avaliationDao = db.avaliationDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*usersDao.insertUser(new User(
                    "guilherme",
                    "123",
                    "img.jpg",
                    "Guilherme Cruz",
                    "guilhermecruz@gmail.com",
                    "912345678",
                    "Masculino",
                    "28-01-2000"));

            usersDao.insertUser(new User(
                    "daniela",
                    "123",
                    "img.jpg",
                    "Daniela Ribeiro",
                    "danielaribeiro@gmail.com",
                    "912345678",
                    "Feminino",
                    "08-12-2000"));*/
            categoryDao.deleteAll();
            usersDao.deleteAll();
            serviceDao.deleteAll();
            favoriteDao.deleteAll();
            scheduleDao.deleteAll();
            usernameDao.deleteAll();
            avaliationDao.deleteAll();
            return null;
        }
    }
}
