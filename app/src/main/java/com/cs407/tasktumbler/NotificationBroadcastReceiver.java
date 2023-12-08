package com.cs407.tasktumbler;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String task = intent.getStringExtra("reminder");

        Log.d("Brodcast", "triggered");
        Intent launchIntent = new Intent(context, MainActivity.class);
        launchIntent.putExtra("reminder", task);

        Log.d("brodcast", "" + System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Task Reminder")
                .setContentText("Task to do: " + task)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // Use a unique notification ID
        int notificationId = generateUniqueNotificationId();


        notificationManager.notify(notificationId, builder.build());

    }

    private int generateUniqueNotificationId() {
        return (int) System.currentTimeMillis();
        }


}
