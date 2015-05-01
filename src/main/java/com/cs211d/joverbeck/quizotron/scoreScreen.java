package com.cs211d.joverbeck.quizotron;
/**
 * Name: Joseph Overbeck
 * Professor: Dr. Moghtanei
 * Class: CS211D
 * Assignment: 05
 * Fun Fact: Ants cannot be seriously injured from impact
 * with the ground after being dropped from any height,
 * because they simply don't have enough mass!
 */

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class scoreScreen extends ActionBarActivity
{
    DbHelper dbHandler;
    QuizGame game;
    int visScore;

    /**
     * *********************onCreate()*****************************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        Intent i = getIntent();
        game = (i.getSerializableExtra("game") != null) ?
                (QuizGame) i.getSerializableExtra("game") :
                null;
        visScore = 0;
        if (game != null)
        {
            updateScore();
            visScore = game.getScore();
        }
        dbHandler = new DbHelper(getApplicationContext());
        String[] highScores = getScores();
        int j = 1;
        for (String k : highScores)
        {
            TextView nameView = (TextView)
                    findViewById(getResources()
                            .getIdentifier("score" + (j),
                                    "id", getPackageName()));
            nameView.setText(k);
            j++;
        }
        TextView curScore = (TextView) findViewById(
                R.id.curScoreActual);
        curScore.setText(String.valueOf(visScore));
        Button button = (Button) findViewById(R.id.goToStart);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),
                        QuizSplashScreen.class);
                startActivity(i);
            }
        });
    }

    /**
     * *********************updateScore()**************************
     */
    private void updateScore()
    {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.UserEntry.COLUMN_NAME_SCORE,
                game.getScore());
        String selection = DbContract.UserEntry._ID + " is ?";
        db.update(
                DbContract.UserEntry.TABLE_NAME,
                values,
                selection,
                new String[]{String.valueOf(game.getUserId())});

    }

    /**
     * *********************getScores()****************************
     */
    private String[] getScores()
    {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String[] projection = {
        };
        String sortOrder =
                DbContract.UserEntry.COLUMN_NAME_SCORE +
                        " DESC";
        Cursor c = db.query(
                DbContract.UserEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder,
                "10");
        c.moveToFirst();
        String[] scores = new String[c.getCount()];
        int i = 0;
        while (c.isAfterLast() == false)
        {
            scores[i] = c.getString(c.getColumnIndex(DbContract.
                    UserEntry.COLUMN_NAME_USERNAME)) +
                    "          " + c.getString(c.getColumnIndex(
                    DbContract.UserEntry.COLUMN_NAME_SCORE));
            i++;
            c.moveToNext();
        }
        c.close();

        return scores;
    }
}
