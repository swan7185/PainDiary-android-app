package com.example.paindiaryapp;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.paindiaryapp.model.UserDailyData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;




public class Util {
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";

    public static String serializeToJson(UserDailyData userData) {
        Gson gson = new Gson();
        return gson.toJson(userData);
    }

    // Deserialize to single object.
    public static UserDailyData deserializeFromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, UserDailyData.class);
    }

    private static FirebaseFirestore firebaseDb = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setupDailyJob(UserDailyData userData)
    {
        Calendar calendar = Calendar.getInstance();
        Calendar runDate = getToday();
        runDate.set(Calendar.HOUR_OF_DAY, 22);
        if(calendar.after(runDate))
            runDate.add(Calendar.DATE, 1);

        Duration duration = Duration.ofMillis(runDate.getTimeInMillis() - calendar.getTimeInMillis());
        Log.i("setupDailyJob", "enqueue jobs, which run after " + duration.getSeconds() + "s");
        OneTimeWorkRequest.Builder myWorkBuilder =
                new OneTimeWorkRequest.Builder(UserUploadWorker.class)
                        //.setInitialDelay(duration)
                        .setInputData(
                                new Data.Builder()
                                        .putString("data string", serializeToJson(userData))
                                        .build()
                        );
        OneTimeWorkRequest myWork = myWorkBuilder
                .build();
        WorkManager.getInstance()
                .enqueueUniqueWork("dailyUpdate", ExistingWorkPolicy.REPLACE, myWork);
    }

    public static void SaveToFireBaseDb(UserDailyData userData)
    {
        String TAG = "Insert to firebase";
        boolean res = true;
        String test = userData.date.toString();
        firebaseDb.collection("user")
                .document(userData.userEmail)
                .collection("weatherData")
                .document(userData.date.toString())
                .set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "success to insert data.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "fail to insert data.");
                    }
                });

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean  isValidPassword(final String password){
        return password.matches(PASSWORD_PATTERN);
    }

    public boolean Equal(Date a, Date b)
    {
        return a.getYear() == b.getYear()
                && a.getMonth() == b.getMonth()
                && a.getDay() == b.getDay();
    }
    public static Calendar getToday()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
    public static Integer tryParseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
