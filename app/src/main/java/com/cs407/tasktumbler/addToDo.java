package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
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
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
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

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

                try {
                    calendar.setTime(sdf.parse(time));
                } catch (ParseException e) {
                    Log.d("Error","Error occurred in getting time");
                    return;
                }



                long reminderTimeInMillis = calendar.getTimeInMillis() + 10 * 1000;

                Schedule.setReminder(addToDo.this, name, reminderTimeInMillis);
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
}