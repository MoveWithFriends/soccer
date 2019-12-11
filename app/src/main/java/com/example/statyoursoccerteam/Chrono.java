package com.example.statyoursoccerteam;

import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

public class Chrono {

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;

    public void startChronometer(View v){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();;
            running  = true;
        }
    }


    public void pauseChronometer(View v){
        if(running){

            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;

        }
    }
}
