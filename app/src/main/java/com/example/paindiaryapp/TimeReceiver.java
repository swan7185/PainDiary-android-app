package com.example.paindiaryapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TimeReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {


        Toast.makeText(context, "It's time to update your daily data!", Toast.LENGTH_LONG).show();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        try {
            notificationManager.notify(id, notification);
        }catch (Exception e)
        {
            Log.e("time receiver", e.getMessage());
        }
    }}