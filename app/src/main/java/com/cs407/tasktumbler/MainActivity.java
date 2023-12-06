package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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