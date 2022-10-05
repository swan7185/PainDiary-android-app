package com.example.paindiaryapp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.paindiaryapp.dao.UserDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserDailyData.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
    private static UserDatabase INSTANCE;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static synchronized UserDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "CustomerDatabase") .fallbackToDestructiveMigration() .build();
        }
        return INSTANCE; }
}
