package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class addCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Button cancelButton = findViewById(R.id.addCatCancelButton);
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