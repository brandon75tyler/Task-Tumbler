package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

//Shake event stuff.
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
import java.util.Objects;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Shake Event Stuff
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    private ArrayList<String> displayToDoItems = new ArrayList<>();
    static ArrayList<ToDoItem> toDoItems1;

    public void categoriesClicked(){
        Intent intent = new Intent(this, CategoriesPage.class);
        startActivity(intent);
    }

    public void timelineAddClicked(){
        Intent intent2 = new Intent(this, addToDo.class);
        startActivity(intent2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Shake Event Stuff
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("TaskTumbler", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        toDoItems1 = dbHelper.readToDoItems();

        ArrayList<String> displayToDoItems = new ArrayList<>();

        for (ToDoItem toDoItem: toDoItems1){
            displayToDoItems.add(String.format("Name: %s\nDetails: %s\nDate: %s\nTime: %s\nCategory: %s\n", toDoItem.getName(), toDoItem.getDetails(), toDoItem.getDate(), toDoItem.getTime(), toDoItem.getCategory()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayToDoItems);
        ListView toDoItemsListView = (ListView) findViewById(R.id.listView);
        toDoItemsListView.setAdapter(adapter);

        toDoItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), EditToDoItem.class);
                intent.putExtra("toDoItemId", i);
                startActivity(intent);
            }
        });

        Button categoriesButton = findViewById(R.id.categoriesButton);
        categoriesButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                categoriesClicked();
            }
        });

        Button timelineAdd = findViewById(R.id.timelineAdd);
        timelineAdd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                timelineAddClicked();
            }

        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


    }


    /*
    Shake Event Sensor Event Listener
    Citation -
    Title: How to detect shake event in Android app?
    Author: Azhar
    Date: 15-Nov-2019
    Availability: https://www.tutorialspoint.com/how-to-detect-shake-event-in-android-app
     */
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if(mAccel > 12) {
                shakeDetected();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void shakeDetected(){
        Toast.makeText(getApplicationContext(), "Shake event detected",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause(){
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}