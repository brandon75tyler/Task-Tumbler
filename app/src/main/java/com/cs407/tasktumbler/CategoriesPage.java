package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoriesPage extends AppCompatActivity {

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