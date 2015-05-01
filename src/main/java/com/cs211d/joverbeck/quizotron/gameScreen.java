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

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class gameScreen extends ActionBarActivity
{
    QuizGame game;
    int n, k;

    /**
     * *********************onCreate()*****************************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        Intent i = getIntent();
        game = (QuizGame) i.getSerializableExtra("game");
        TextView question1 = (TextView) findViewById(
                R.id.question1text);
        question1.setText("What is " + game.getTarget() + "?");
        TextView question2 = (TextView) findViewById(
                R.id.question2Text);
        question2.setText("What is " + game.getTarget() + "'s "
                + game.getTargetTypeOpposite() + "?");
        Button p1 = (Button) findViewById(R.id.question1button);

        p1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                next();
            }
        });

        RadioGroup r1 = (RadioGroup) findViewById(
                R.id.answerGroup1);
        r1.setOnCheckedChangeListener(new
              RadioGroup.OnCheckedChangeListener()
              {
                  @Override
                  public void onCheckedChanged(RadioGroup group,
                                               int checkedId)
                  {
                      if (checkedId == R.id.capital)
                      {
                          n = 1;
                          ((Button) findViewById(R.id.question1button))
                                  .setEnabled(true);
                      }
                      if (checkedId == R.id.state)
                      {
                          n = 0;
                          ((Button) findViewById(R.id.question1button))
                                  .setEnabled(true);
                      }
                  }
              });
        Button p2 = (Button) findViewById(R.id.question2button);

        p2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                next();
            }
        });
        RadioGroup r2 = (RadioGroup) findViewById(
                R.id.answerGroup2);
        r2.setOnCheckedChangeListener(new
              RadioGroup.OnCheckedChangeListener()
              {
                  @Override
                  public void onCheckedChanged(RadioGroup group,
                                               int checkedId)
                  {
                      if (checkedId == R.id.choice1)
                      {
                          k = 0;
                          ((Button) findViewById(R.id.question2button))
                                  .setEnabled(true);
                      }
                      if (checkedId == R.id.choice2)
                      {
                          k = 1;
                          ((Button) findViewById(R.id.question2button))
                                  .setEnabled(true);
                      }
                      if (checkedId == R.id.choice3)
                      {
                          k = 2;
                          ((Button) findViewById(R.id.question2button))
                                  .setEnabled(true);
                      }
                      if (checkedId == R.id.choice4)
                      {
                          k = 3;
                          ((Button) findViewById(R.id.question2button))
                                  .setEnabled(true);
                      }
                  }
              });
int z = 0;
        for (String k : game.getAnswers())
        {
            TextView nameView = (RadioButton)
                    findViewById(getResources()
                            .getIdentifier("choice" + (z + 1),
                                    "id", getPackageName()));
            nameView.setText(k);
            z++;
        }
        setScore();

    }

    /**
     * *********************next()*********************************
     */
    public void next()
    {
        setScore();
        View v1 = (View) findViewById(R.id.question1);
        View v2 = (View) findViewById(R.id.question2);
        if ((v1.getVisibility() == View.VISIBLE) &&
                (v2.getVisibility() == View.INVISIBLE))
        {
            if (!game.part1(n))
            {
                Intent i = getCorrectIntent();
                startActivity(i);

            }
            v1.setVisibility(View.INVISIBLE);
            v2.setVisibility(View.VISIBLE);
        } else
        {
            game.part2(k);
            Intent i = getCorrectIntent();
            startActivity(i);
        }

    }

    /**
     * *********************setScore()*****************************
     */
    public void setScore()
    {
        TextView t = (TextView) findViewById(R.id.score);
        t.setText(String.valueOf(game.getScore()));

    }

    /**
     * *********************getCorrectIntent()*********************
     */
    public Intent getCorrectIntent()
    {
        Intent i;
        if (game.advanceTurn())
        {
            i = new Intent(getApplicationContext(),
                    gameScreen.class);
        } else
        {
            i = new Intent(getApplicationContext(),
                    scoreScreen.class);
        }
        i.putExtra("game", game);
        return i;
    }

    /**
     * *********************onBackPressed()************************
     */
    public void onBackPressed()
    {
        Intent i = new Intent(getApplicationContext(),
                QuizSplashScreen.class);
        startActivity(i);
    }
}



