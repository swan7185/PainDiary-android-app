package com.example.paindiaryapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.paindiaryapp.model.UserDailyData;
import com.example.paindiaryapp.model.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserViewModel extends ViewModel {
    public UserRepository userRepository;
    private LiveData<List<UserDailyData>> allUserData;
    private String userEmail;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadAllUserData(Context context, String userEmail) {
        this.userEmail = userEmail;
        userRepository = new UserRepository(context, userEmail);
        allUserData = userRepository.getAllUsers();
    }

    public void insert(UserDailyData userData) {
        userRepository.insert(userData);
    }

    public void delete(UserDailyData userData) { userRepository.deleteUserData(userData);}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<UserDailyData> findByIDFuture(Date date) {
        return userRepository.findUser(date);
    }


    public LiveData<List<UserDailyData>> getAllUserData() {

        return this.allUserData;
    }

    public void Update(UserDailyData userData)
    {
        userRepository.updateUserData(userData);
    }
}
