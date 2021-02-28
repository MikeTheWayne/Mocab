package com.waynegames.mocab;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class WordClass {

    // Arraylists to store english and foreign dictionary
    private final ArrayList<String> engWords = new ArrayList<String>();
    private final ArrayList<String> foreignWords = new ArrayList<String>();

    public WordClass(String eng, String foreign, Context context) {

        // Read the words from the english file and store them in an array list
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(context.getAssets().open(eng)));

            String line;
            while ((line = buffer.readLine())!=null){
                engWords.add(line);
            }

            buffer.close();
        } catch (Exception e) {
            Log.e("Main", "Error is " + e.toString());
        }

        // Read the words from the foreign file and store them in an array list
        try {
            BufferedReader br2 = new BufferedReader(new InputStreamReader(context.getAssets().open(foreign)));

            String line;
            while ((line = br2.readLine())!=null){
                foreignWords.add(line);
            }

            br2.close();
        } catch (Exception e) {
            Log.e("Main", "Error is " + e.toString());
        }

    }

    /**
     * Returns 10 randomly generated words, along with their translations
     *
     * @return 10 Word objects, containing the English and Foreign translations
     */
    public Word[] getTenWords() {

        Random random = new Random();

        Word[] returnWords = new Word[10];

        // Generate positions
        ArrayList<Position> positions = new ArrayList<Position>();

        for(int i = 65; i < 295; i += 10) {
            for(int j = -20; j < 80; j += 10) {
                positions.add(new Position(i, j));
            }
        }

        // Generate words
        for(int i = 0; i < 10; i++) {
            int position = random.nextInt(engWords.size());
            int engPos = random.nextInt(positions.size());
            int trnPos;

            // Avoid generating same number
            do {
                trnPos = random.nextInt(positions.size());
            } while(trnPos == engPos);

            returnWords[i] = new Word(engWords.get(position), foreignWords.get(position), positions.get(engPos).x, positions.get(engPos).y, positions.get(trnPos).x, positions.get(trnPos).y);

            // Remove repeats
            positions.remove(engPos);
            positions.remove((trnPos < engPos) ? trnPos : trnPos - 1);
        }

        return returnWords;
    }

    private static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
