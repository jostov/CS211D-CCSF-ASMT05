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
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class QuizSplashScreen extends ActionBarActivity
{

    DbHelper dbHandler;

    /**
     * *********************getTarget()****************************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_splash_screen);
        dbHandler = new DbHelper(getApplicationContext());
        checkForDb();
        //Start game listener
        final Button startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),
                        gameScreen.class);
                long user = addUser(((EditText) findViewById(
                        R.id.nameInput)).getText().toString());
                QuizGame quiz = startGame(user); //Good god, man. Variables shouldn't share names with methods. I'll be leaving this here for posterity's sake.
                i.putExtra("game", quiz);
                startActivity(i);
            }
        });
        //Score button listener
        Button scoreButton = (Button) findViewById(R.id.scoreScreen);
        scoreButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),
                        scoreScreen.class);
                startActivity(i);
            }
        });
    }

    /**
     * *********************getTarget()****************************
     */
    private void populateDatabase()
    {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        Resources res = getResources();
        String[] statelist = res.getStringArray(R.array.DataBase);
        for (String i : statelist)
        {
            String[] part = i.split(",");
            values.put(DbContract.StateEntry.COLUMN_NAME_STATES,
                    part[0]);
            values.put(DbContract.StateEntry.COLUMN_NAME_CAPITALS,
                    part[1]);
            db.insert(DbContract.StateEntry.TABLE_NAME, "null",
                    values);
            values.clear();
        }
    }

    /**
     * *********************getTarget()****************************
     */
    private QuizGame startGame(long userid)
    {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String[] projection = {
        };
        String sortOrder =
                "RANDOM()";
        Cursor c = db.query(
                DbContract.StateEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);
        c.moveToFirst();
        String[][] states = new String[2][c.getCount()];
        int i = 0;
        while (c.isAfterLast() == false)
        {
            states[0][i] = c.getString(c.getColumnIndex(
                    DbContract.StateEntry.COLUMN_NAME_STATES));
            states[1][i] = c.getString(c.getColumnIndex(
                    DbContract.StateEntry.COLUMN_NAME_CAPITALS));
            i++;
            c.moveToNext();
        }
        c.close();
        return new QuizGame(userid, states);
    }

    /**
     * *********************getTarget()****************************
     */
    private long addUser(String username)
    {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.UserEntry.COLUMN_NAME_USERNAME,
                username);
        long newRowId = db.insert(DbContract.UserEntry
                        .TABLE_NAME, "null",
                values);
        return newRowId;

    }

    /**
     * *********************getTarget()****************************
     */
    private void getUser(String id)
    {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String[] projection = {
                DbContract.UserEntry._ID,
                DbContract.UserEntry.COLUMN_NAME_USERNAME,
                DbContract.UserEntry.COLUMN_NAME_SCORE
        };
        String selection = DbContract.UserEntry._ID + " is ?";
        String sortOrder =
                DbContract.UserEntry.COLUMN_NAME_USERNAME +
                        " DESC";
        Cursor c = db.query(
                DbContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                new String[]{id},
                null,
                null,
                sortOrder);
        c.moveToFirst();
        Log.d("db woes", c.getString(c.getColumnIndex(
                DbContract.UserEntry.COLUMN_NAME_USERNAME)));
        c.close();


    }

    public void checkForDb()
    {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(
                DbContract.StateEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        int cnt = cursor.getCount();
        cursor.close();
        if (cnt != 50)
        {
            dbHandler.dropStates(db);
            populateDatabase();
        }
    }
}



