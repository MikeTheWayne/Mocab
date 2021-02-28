package com.waynegames.mocab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        Button button = findViewById(R.id.button);
        ImageButton italian = findViewById(R.id.imageButtonItalian);
        ImageButton spanish = findViewById(R.id.imageButton2);
        ImageButton russian = findViewById(R.id.imageButtonRussian);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        spanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.engFile = "English_Spanish_words";
                MainActivity.langFile = "Spanish_word";
            }
        });

        italian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.engFile = "italian";
                MainActivity.langFile = "italian_english";
            }
        });

        russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.engFile = "russian_english";
                MainActivity.langFile = "russian";
            }
        });


    }
}