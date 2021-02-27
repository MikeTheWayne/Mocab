package com.waynegames.mocab;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WordClass {

    //arraylists to store english and foreign dictionary
    final ArrayList<String> engWords = new ArrayList<String>();
    final ArrayList<String> foreignWords = new ArrayList<String>();


    public WordClass(String eng, String foreign, Context context){

        // read the words from the english file and store them in an array list
        Log.e("main", "read start");
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(context.getAssets().open(eng)));
            String line;
            while ((line = buffer.readLine())!=null){
                engWords.add(line);
            }
            buffer.close();
        }catch (Exception e){
            Log.e("main", " error is "+e.toString());
        }


        // read the words from the foreign file and store them in an array list
        Log.e("main", "read start");
        try {
            BufferedReader br2 = new BufferedReader(new InputStreamReader(context.getAssets().open(foreign)));
            String line;
            while ((line = br2.readLine())!=null){
                foreignWords.add(line);
            }
            br2.close();
        }catch (Exception e) {
            Log.e("main", " error is " + e.toString());
        }

    }
}
