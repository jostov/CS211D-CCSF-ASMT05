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

import android.util.Log;

import java.io.Serializable;
import java.util.Random;


@SuppressWarnings("serial")
public class QuizGame implements Serializable
{

    private int score;
    private long userId;
    private int turncounter;
    QuizCard[] deck;

    /**
     * *********************OnCreate*******************************
     */
    public QuizGame(Long userid, String[][] states)
    {
        turncounter = 0;
        userId = userid;
        score = 0;
        deck = new QuizCard[5];
        String[][] instDeck = new String[2][4];
        for (int i = 0; i < 5; i++)
        {
            for (int k = 0; k < 4; k++)
            {
                instDeck[0][k] = states[0][(i * 5) + k];
                instDeck[1][k] = states[1][(i * 5) + k];
            }
            deck[i] = new QuizCard(instDeck);
            Log.d("Card bullshit", deck[i].getAnswers()[0] +" " + deck[i].getAnswers()[1] + " " + deck[i].getAnswers()[2] +" " + deck[i].getAnswers()[3]); //Serious shenanigans here
        }

    }

    /**
     * *********************getTarget()****************************
     */
    public String getTarget()
    {
        return deck[turncounter].getTarget();
    }

    /**
     * *********************getTargetTypeOpposite()****************
     */
    public String getTargetTypeOpposite()
    {
        if (deck[turncounter].correctCat(0))
        {
            return "capital";
        }
        return "state";
    }

    /**
     * *********************getAnswers()***************************
     */
    public String[] getAnswers()
    {
        return deck[turncounter].getAnswers();
    }

    /**
     * *********************part1()********************************
     */
    public boolean part1(int n)
    {
        if (deck[turncounter].correctCat(n))
        {
            score += 10;
            return true;
        }
        return false;
    }

    /**
     * *********************part2()********************************
     */
    public boolean part2(int n)
    {
        if (deck[turncounter].checkAns(n))
        {
            score += 10;
            return true;
        }
        return false;
    }

    /**
     * *********************advanceTurn()**************************
     */
    public boolean advanceTurn()
    {
        turncounter++;
        if (turncounter >= 5)
        {
            return false;
        }
        return true;
    }

    /**
     * *********************getDeck()******************************
     */
    public QuizCard[] getDeck()
    {
        return deck;
    }

    /**
     * *********************getUserId()****************************
     */
    public long getUserId()
    {
        return userId;
    }


    /**
     * *********************getScore()*****************************
     */

    public int getScore()
    {
        return score;
    }


    /**
     * *********************QuizCard()*****************************
     */
    @SuppressWarnings("serial")
    public class QuizCard implements Serializable
    {

        int x, y;
        String[][] states;
        Random r;

        /**
         * *********************QuizCard()*****************************
         */
        public QuizCard(String[][] statesIn)
        {
            r = new Random();
            states = statesIn;
            x = (r.nextBoolean()) ? 0 : 1;
            y = r.nextInt(4);
        }

        /**
         * *********************correctCat()***************************
         */

        public boolean correctCat(int n)
        {
            return (x == n) ? true : false;
        }

        /**
         * *********************checkAns()*****************************
         */
        public boolean checkAns(int n)
        {
            if (states[x][n].equals(states[x][y]))
            {
                return true;
            }
            ;
            return false;
        }

        /**
         * *********************getTarget()****************************
         */
        public String getTarget()
        {
            return states[x][y];
        }

        /**
         * *********************getAnswers()***************************
         */
        public String[] getAnswers()
        {
            String[] returnStates = new String[4];
            for (int i = 0; i < 4; i++)
            {
                returnStates[i] = states[1 - x][i];
            }
            return returnStates;
        }
    }


}
