package com.cs407.tasktumbler;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class addToDo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        Button cancelButton = findViewById(R.id.addToDoCancelButton);
        Button submitButton = findViewById(R.id.addToDoCreateButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("TaskTumbler", Context.MODE_PRIVATE, null);
                DBHelper dbHelper = new DBHelper(sqLiteDatabase);

                //SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone1", Context.MODE_PRIVATE);

                TextView nameTextBox = findViewById(R.id.itemNameText);
                String name = nameTextBox.getText().toString();

                TextView detailsTextBox = findViewById(R.id.itemDetailsText);
                String details = detailsTextBox.getText().toString();

                TextView dateTextBox = findViewById(R.id.itemDateText);
                String date = dateTextBox.getText().toString();

                TextView timeTextBox = findViewById(R.id.itemTimeText);
                String time = timeTextBox.getText().toString();

                TextView categoryTextBox = findViewById(R.id.itemCategoryText);
                String category = categoryTextBox.getText().toString();

                dbHelper.saveToDoItem(name, details, date, time, category);

//                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
//
//
//                try {
//                    calendar.setTime(sdf.parse(time));
//
//                } catch (ParseException e) {
//                    Log.d("Error","Error occurred in getting time");
//                    return;
//                }
////                calendar.setTimeInMillis(System.currentTimeMillis()+ 10*1000);
//                Log.d("time", ""+time);
//                long reminderTimeInMillis = calendar.getTimeInMillis()  ;
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                String dateTimeString = date + " " + time;
                Date dateTime;
                try {
                    dateTime = dateTimeFormat.parse(dateTimeString);
                } catch (ParseException e) {
                    Log.d("Error", "Error occurred in parsing date and time");
                    return;
                }

                // Convert Date object to Calendar and set the desired date and time
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateTime);

                // Get the timestamp in milliseconds
                long reminderTimeInMillis = calendar.getTimeInMillis();
                Log.d("time in mili", ""+reminderTimeInMillis);
                createNotificationChannel();
                requestPermission();
                Schedule.setReminder(addToDo.this, name ,reminderTimeInMillis);
//                Schedule.setReminder(addToDo.this, name,calendar.getTimeInMillis());
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                goToActivity();
            }
        });
    }

    public void goToActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    Toast.makeText(this, R.string.please_allow_notification, Toast.LENGTH_LONG).show();
                }
            });

    private void requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return;
        }
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not granted
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

}