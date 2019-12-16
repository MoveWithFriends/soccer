package com.example.statyoursoccerteam;

import android.os.SystemClock;
import android.widget.Toast;

public class Scores {
    private int scoreAgainst = 0;
    private int scorePro = 0;


    public String scoreAgainst(){
            scoreAgainst ++;
            String goals = String.valueOf(scoreAgainst);
            return goals;
    }

    public String scorePro(){
        scorePro ++;
        String goal = String.valueOf(scorePro);
        return goal;
    }


}
