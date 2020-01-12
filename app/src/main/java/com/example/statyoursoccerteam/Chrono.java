package com.example.statyoursoccerteam;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import androidx.appcompat.app.AlertDialog;

import com.example.statyoursoccerteam.View.StartActivity;


public class Chrono {
    private static final String TAG = "Chrono";
    private Context context;
    public boolean running = false;
    public long pauseOffset = 0;
    private long elapsedSecs;

    //Implement Chrono as a Singleton
    public static Chrono instance = null;


    private Chrono(Context context) {
        this.context = context;
    }

    public static Chrono getInstance() {
        return instance;
    }


    /**
     * Get an instance of the app's singleton chrono object
     *
     * @param context - the chrono context from which it is called
     * @return a Chrono instance
     */

    public static Chrono getInstance(StartActivity context) {
        if (instance == null) {
            Log.d(TAG, "getInstance: creating a new instance");
            instance = new Chrono(context);
        }
        return instance;
    }


    public void startChronometer(Chronometer chronometer) {
        Log.d(TAG, "startChronometer: running ? " + running);

        if (!running) {
//            Log.d(TAG, "startChronometer starttimer -: " + StartActivity.starttimer);
            if (pauseOffset == 0) {
                chronometer.setBase(SystemClock.elapsedRealtime() - (StartActivity.starttimer * 1000));
            } else {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            }
            chronometer.start();

            running = true;

        } else {

            chronometer.start();

        }

    }


    public void pauseChronometer(Chronometer chronometer) {

        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        } else {
            startChronometer(chronometer);
        }
    }

    public void stopChronometer(final Chronometer chronometer) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        } else {
            pauseChronometer(chronometer);
        }

    }

    public void showWarningMessage(Chronometer chronometer) {
        Log.d(TAG, "showWarningMessage: " + running);
        if (running) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Do you want to stop ?");
            builder.setIcon(R.drawable.ic_warning_black_24dp);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    Chrono.getInstance().stopChronometer(chronometer);
                    StartActivity.getInstance().startSummaryActivity();
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


    public long showElapsedTime(Chronometer chronometer) {
        if (chronometer != null) {
            if (running) {
                elapsedSecs = (long) (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
            } else {
                elapsedSecs = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
            }

        } else {
            elapsedSecs = 0;
        }
        return elapsedSecs;
    }

}
