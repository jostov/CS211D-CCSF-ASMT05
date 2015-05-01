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

import android.provider.BaseColumns;

public final class DbContract
{
    public DbContract()
    {
    }

    /**
     * *********************UserEntry()****************************
     */
    public static abstract class UserEntry implements BaseColumns
    {
        public static final String
                TABLE_NAME = "users";
        public static final String
                COLUMN_NAME_USERNAME = "usernames";
        public static final String
                COLUMN_NAME_SCORE = "score";
    }

    /**
     * *********************StateEntry()***************************
     */
    public static abstract class StateEntry implements BaseColumns
    {
        public static final String
                TABLE_NAME = "statcaptable";
        public static final String
                COLUMN_NAME_STATES = "states";
        public static final String
                COLUMN_NAME_CAPITALS = "capitals";
    }
}
