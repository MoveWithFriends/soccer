package com.example.statyoursoccerteam;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


public class Chrono {
    private static final String TAG = "Chrono";
    private final Context context;


    public long pauseOffset;


    public Chrono(Context context) {
        pauseOffset = 0;
        this.context = context;
        running = false;
    }

    public boolean running;
    private long elapsedSecs;

    //Implement Chrono as a Singleton
    private static Chrono instance = null;


    /**
     * Get an instance of the app's singleton chrono object
     * @param context - the chrono context from which it is called
     * @return a Chrono object
     */

    public static Chrono getInstance(Context context) {
        if (instance == null){
            Log.d(TAG, "getInstance: creating a new instance");
            instance = new Chrono(context);
        }
        return instance;
    }


    public void startChronometer(Chronometer chronometer){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running  = true;
        }
    }



    public void pauseChronometer(Chronometer chronometer){
//        showElapsedTime(chronometer);
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        } else {
            startChronometer(chronometer);
        }
    }

    public void stopChronometer(final Chronometer chronometer){
        if(running){
            //TODO make warning massage to confirm stopping the registration of game events
            // if confirmed then show summary activity
            // if confirmation is negative then time will continue
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        } else {

            pauseChronometer(chronometer);
        }

    }

    public void showWarningMessage(final Chronometer chronometer ){
        if(running) {
            //TODO make context startactivity to show the message
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Do you want to stop ?");
            builder.setIcon(R.drawable.ic_warning_black_24dp);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    stopChronometer(chronometer);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    ;
                }

            });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }



    public long showElapsedTime(Chronometer chronometer) {
            elapsedSecs = (long) (SystemClock.elapsedRealtime()-chronometer.getBase())/1000;
        return elapsedSecs;
    }

}
