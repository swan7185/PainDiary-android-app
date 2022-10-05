package com.example.paindiaryapp.model;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.paindiaryapp.HomeActivity;
import com.example.paindiaryapp.dao.UserDAO;
import com.example.paindiaryapp.viewmodel.UserViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class UserRepository {
    private UserDAO userDao;
    private LiveData<List<UserDailyData>> allData;
    private String UserEmail;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public UserRepository(Context context, String email){
        UserDatabase db = UserDatabase.getInstance(context);
        this.UserEmail = email;
        this.userDao =db.userDao();
        allData= userDao.getAll(email);



    }
// Room executes this query on a separate thread
    public LiveData<List<UserDailyData>> getAllUsers() {
        return allData;
    }
    public void insert(final UserDailyData User){ UserDatabase.databaseWriteExecutor.execute(new Runnable() {
        @Override
        public void run() { userDao.insert(User);
        } });
    }

    public void deleteAll()
    {
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() { userDao.deleteAll();
            } });
    }

    public void updateUserData(UserDailyData userData)
    {
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() { userDao.update(userData);
            } });
    }

    public void deleteUserData(UserDailyData userData)
    {
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.delete(userData);
            } });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<UserDailyData> findUser(final Date date) {
        return CompletableFuture.supplyAsync(new Supplier<UserDailyData>() {
            @Override
            public UserDailyData get() {
                return userDao.findUserData(UserEmail, date); }
            },
                UserDatabase.databaseWriteExecutor); }

}