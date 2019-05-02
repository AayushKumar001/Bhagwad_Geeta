package com.application.aayush.geeta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by Aayush on 11/12/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    MediaPlayer mediaPlayer;
    private static final int MY_NOTIFICATION_ID=1;
    NotificationManager notificationManager;
    Notification myNotification;
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("In Start Service","Alarm Receiver");
        sharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("vibrate_flag",false)) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(10000);
        }
        Intent myIntent = new Intent(context, RingtonePlayService.class);
        context.startService(myIntent);
/*
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent
                .getActivity(context,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);

        myNotification = new NotificationCompat.Builder(context)
                .setContentTitle("Notification for reading Geetha !")
                .setContentText("Don't forget to read it today")
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.shell)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.shell))
                .build();

        notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

        context.startActivity(new Intent(context,Splash.class));
*/

    }

}
