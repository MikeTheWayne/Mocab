package com.waynegames.mocab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class FinishScreen extends AppCompatActivity {

    private TextView mTextView;
    static int score;

    String nameText = "";

    int[] serverInput;

    TextView leaderboardText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        Button button = findViewById(R.id.button4);
        TextView scoreText = findViewById(R.id.textView2);
        leaderboardText = findViewById(R.id.textView4);

        scoreText.setText(score + "");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishScreen.this, MainMenu.class);
                startActivity(intent);
                finish();
            }
        });

        // Display name prompt
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name!");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameText = input.getText().toString();

                Handler handler = new Handler();

                new Thread() {

                    @Override
                    public void run() {
                        super.run();

                        // Send data to server
                        final String SERVER_ADDRESS = "35.242.136.144";
                        final int SERVER_PORT = 40000;

                        Socket socket = null;

                        DataOutputStream dataOutputStream = null;
                        DataInputStream dataInputStream = null;

                        try {
                            socket = new Socket();
                            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT), 3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            dataOutputStream.writeInt(score);
                            dataOutputStream.writeUTF(nameText);
                            dataOutputStream.flush();

                            int position = dataInputStream.readInt();

                            serverInput = new int[10];
                            StringBuilder stringBuilder = new StringBuilder();

                            for(int i = 0; i < 10; i++) {
                                String name = dataInputStream.readUTF();
                                serverInput[i] = dataInputStream.readInt();

                                stringBuilder.append((i + 1) + ". " + name + " - " + serverInput[i] + "\n");
                            }

                            stringBuilder.append((position + 1) + ". " + nameText + " - " + score);

                            handler.post(new Runnable() {
                                 public void run() {
                                     // Set leaderboard text
                                     leaderboardText.setText(stringBuilder.toString());
                                 }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}