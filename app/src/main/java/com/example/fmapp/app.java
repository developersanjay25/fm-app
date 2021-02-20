package com.example.fmapp;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.renderscript.RenderScript;

import androidx.core.app.NotificationManagerCompat;

public class app extends Application {

    public static final String CHANNEL_1= "channel1";
    @Override
    public void onCreate() {
        super.onCreate();
          notificationtio();
    }

    public void notificationtio()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_1,"Channel_1",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Tamil fm");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(notificationChannel);
        }
     }
}
