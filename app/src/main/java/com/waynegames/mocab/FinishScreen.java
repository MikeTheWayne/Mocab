package com.waynegames.mocab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class FinishScreen extends AppCompatActivity {

    private TextView mTextView;
    static int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        Button button = findViewById(R.id.button4);
        TextView scoreText = findViewById(R.id.textView2);

        scoreText.setText(score + "");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishScreen.this, MainMenu.class);
                startActivity(intent);
            }
        });

    }
}