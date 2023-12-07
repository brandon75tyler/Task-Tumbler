package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class addCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Button createButton = findViewById(R.id.createCategoryButton);
        Button cancelButton = findViewById(R.id.addCatCancelButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Context context = getApplicationContext();
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("TaskTumbler", Context.MODE_PRIVATE, null);
                DBHelper dbHelper = new DBHelper(sqLiteDatabase);

                TextView nameTextBox = findViewById(R.id.categoryNameText);
                String name = nameTextBox.getText().toString();

                dbHelper.saveCategory(name);
                goToActivity();
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
        Intent intent = new Intent(this, CategoriesPage.class);
        startActivity(intent);
    }


}