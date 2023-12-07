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

public class CategoriesPage extends AppCompatActivity {

    private ArrayList<String> displayCategories = new ArrayList<>();
    static ArrayList<Category> categories1;

    public void timelineClicked(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void categoriesAddClicked(){
        Intent intent2 = new Intent(this, addCategory.class);
        startActivity(intent2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_page);

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("TaskTumbler", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        categories1 = dbHelper.readCategories();

        ArrayList<String> displayCategories = new ArrayList<>();

        for (Category category: categories1){
            displayCategories.add(String.format("%s\n", category.getName()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayCategories);
        ListView categoriesListView = (ListView) findViewById(R.id.listView);
        categoriesListView.setAdapter(adapter);

        Button timelineButton = findViewById(R.id.timelineButton2);
        timelineButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timelineClicked();
            }
        });

        Button categoriesAddButton = findViewById(R.id.categoriesAdd);
        categoriesAddButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                categoriesAddClicked();
            }
        });

    }
}