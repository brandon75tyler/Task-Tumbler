package com.cs407.tasktumbler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button firstFragmentButton = findViewById(R.id.firstFragmentButton);
        firstFragmentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                fragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, FirstFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("Showing First")
                        .commit();
            }
        });

        Button secondFragmentButton = findViewById(R.id.secondFragmentButton);
        secondFragmentButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Log.d("Tag", "I ran!");
                fragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, SecondFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("Showing Second")
                        .commit();
            }
        });









    }
}