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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "quizotron.db";
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + DbContract.UserEntry.TABLE_NAME + " (" +
                    DbContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    DbContract.UserEntry.COLUMN_NAME_USERNAME + " TEXT, " +
                    DbContract.UserEntry.COLUMN_NAME_SCORE + " INTEGER);";
    private static final String STATCAPTABLE_CREATE =
            "CREATE TABLE " + DbContract.StateEntry.TABLE_NAME + " (" +
                    DbContract.StateEntry._ID + " INTEGER PRIMARY KEY," +
                    DbContract.StateEntry.COLUMN_NAME_STATES + " TEXT, " +
                    DbContract.StateEntry.COLUMN_NAME_CAPITALS + " TEXT);";

    private static final String DELETE_USERS =
            "DROP TABLE IF EXISTS " +
                    DbContract.UserEntry.TABLE_NAME;
    private static final String DELETE_STATCAP =
            "DROP TABLE IF EXISTS " +
                    DbContract.StateEntry.TABLE_NAME;

    /**
     * *********************DbHelper()*****************************
     */
    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * *********************deleteEverything()*********************
     */
    public void deleteEverything(SQLiteDatabase db)
    {
        db.execSQL(DELETE_USERS);
        db.execSQL(DELETE_STATCAP);
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(STATCAPTABLE_CREATE);

    }
    public void dropStates(SQLiteDatabase db){
        db.execSQL(DELETE_STATCAP);
        db.execSQL(STATCAPTABLE_CREATE);
    }

    /**
     * *********************onCreate()*****************************
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(STATCAPTABLE_CREATE);
    }

    /**
     * *********************onUpgrade()****************************
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion)
    {
        db.execSQL(DELETE_USERS);
        db.execSQL(DELETE_STATCAP);
        onCreate(db);

    }
}
