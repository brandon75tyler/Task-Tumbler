package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
                TextView task_name = findViewById(R.id.itemNameText);
                String task = task_name.getText().toString();

                TextView task_time = findViewById(R.id.itemTimeText);
                String time = task_time.getText().toString();


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

                try {
                    calendar.setTime(sdf.parse(time));
                } catch (ParseException e) {
                    Log.d("Error","Error occurred in getting time");
                    return;
                }


                long reminderTimeInMillis = calendar.getTimeInMillis() + 10 * 1000;

                Schedule.setReminder(addToDo.this, task, reminderTimeInMillis);
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