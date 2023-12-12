package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditToDoItem extends AppCompatActivity {

    private int toDoItemId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do_item);

        EditText myNameText = (EditText) findViewById(R.id.itemNameText);
        myNameText.setEnabled(false);
        EditText myDetailsText = (EditText) findViewById(R.id.itemDetailsText);
        EditText myDateText = (EditText) findViewById(R.id.itemDateText);
        EditText myTimeText = (EditText) findViewById(R.id.itemTimeText);
        EditText myCategoryText = (EditText) findViewById(R.id.itemCategoryText);


        Intent intent = getIntent();

        toDoItemId = intent.getIntExtra("toDoItemId", -1);

        if (toDoItemId != -1) {
            ToDoItem toDoItem = MainActivity.toDoItems1.get(toDoItemId); //causing crash

            String itemName = toDoItem.getName();
            myNameText.setText(itemName.toString());

            String itemDetails = toDoItem.getDetails();
            myDetailsText.setText(itemDetails.toString());

            String itemDate = toDoItem.getDate();
            myDateText.setText(itemDate.toString());

            String itemTime = toDoItem.getTime();
            myTimeText.setText(itemTime.toString());

            String itemCategory = toDoItem.getCategory();
            myCategoryText.setText(itemCategory.toString());
        }

        Button saveButton = findViewById(R.id.addToDoCreateButton);
        Button cancelButton = findViewById(R.id.addToDoCancelButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText myNameText = (EditText) findViewById(R.id.itemNameText);
                EditText myDetailsText = (EditText) findViewById(R.id.itemDetailsText);
                EditText myDateText = (EditText) findViewById(R.id.itemDateText);
                EditText myTimeText = (EditText) findViewById(R.id.itemTimeText);
                EditText myCategoryText = (EditText) findViewById(R.id.itemCategoryText);

                Context context = getApplicationContext();
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("TaskTumbler", Context.MODE_PRIVATE, null);
                DBHelper dbHelper = new DBHelper(sqLiteDatabase);


                if (toDoItemId == -1) {
                    dbHelper.saveToDoItem(myNameText.getText().toString(), myDetailsText.getText().toString(), myDateText.getText().toString(), myTimeText.getText().toString(), myCategoryText.getText().toString());
                }
                else {
                    dbHelper.updateToDoItem(myNameText.getText().toString(), myDetailsText.getText().toString(), myDateText.getText().toString(), myTimeText.getText().toString(), myCategoryText.getText().toString());
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schedule.cancelReminder(EditToDoItem.this);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText myNameText = (EditText) findViewById(R.id.itemNameText);
                EditText myDetailsText = (EditText) findViewById(R.id.itemDetailsText);

                Context context = getApplicationContext();
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("TaskTumbler", Context.MODE_PRIVATE, null);
                DBHelper dbHelper = new DBHelper(sqLiteDatabase);

                dbHelper.deleteToDoItem(myNameText.getText().toString(), myDetailsText.getText().toString());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}