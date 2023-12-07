package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

    }
}