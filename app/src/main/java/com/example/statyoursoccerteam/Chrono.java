package com.example.statyoursoccerteam;

import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

import androidx.appcompat.app.AlertDialog;

public class Chrono  {

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    private Context StartActivity;

    public void startChronometer(){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running  = true;
        }
    }


    public void pauseChronometer(){
        if(running){

            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;

        }
    }

    public void stopChronometer(){
        if(running){

            AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Do you want to stop ?");
            builder.setIcon(R.drawable.ic_warning_black_24dp);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();


        }
    }

}
