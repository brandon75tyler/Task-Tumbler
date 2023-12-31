package com.cs407.tasktumbler;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private ArrayList<String> displayToDoItems = new ArrayList<>();
    static ArrayList<ToDoItem> toDoItems1;


    @Override
    public void onReceive(Context context, Intent intent) {


        Intent launchIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, PendingIntent.FLAG_IMMUTABLE);
//        String name = intent.getStringExtra("reminder");


        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("TaskTumbler", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        toDoItems1 = dbHelper.readToDoItems();
        String name = "";

        for (ToDoItem toDoItem: toDoItems1){name = toDoItem.getName();}
//        name = intent.getStringExtra("reminder");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("You have a task to complete")
                .setContentText("Please " +name)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(0xFF0000FF);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // Use a unique notification ID
        int notificationId = 123;
        notificationManager.notify(notificationId, builder.build());

    }



}
