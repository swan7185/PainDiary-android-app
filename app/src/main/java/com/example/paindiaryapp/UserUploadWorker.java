package com.example.paindiaryapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.paindiaryapp.model.UserDailyData;

public class UserUploadWorker extends Worker
{
    public UserUploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i("UserUploadWorker", "insert user data into firestore.");
        UserDailyData data = Util.deserializeFromJson(getInputData().getString("data string"));
        Util.SaveToFireBaseDb(data);
        return Result.success();
    }
}