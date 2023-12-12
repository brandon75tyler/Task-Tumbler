package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditCategory extends AppCompatActivity {

    int categoryId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        EditText myNameText = (EditText) findViewById(R.id.categoryNameText);
        myNameText.setEnabled(false);

        Intent intent = getIntent();

        categoryId = intent.getIntExtra("categoryId", -1);

        if (categoryId != -1) {
            Category category = CategoriesPage.categories1.get(categoryId); //causing crash

            String itemName = category.getName();
            myNameText.setText(itemName.toString());
        }

        Button cancelButton = findViewById(R.id.cancelButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategoriesPage.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("TaskTumbler", Context.MODE_PRIVATE, null);
                DBHelper dbHelper = new DBHelper(sqLiteDatabase);

                dbHelper.deleteCategory(myNameText.getText().toString());

                Intent intent = new Intent(getApplicationContext(), CategoriesPage.class);
                startActivity(intent);
            }
        });
    }
}