package com.example.statyoursoccerteam;

import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


public class Chrono {
    private static final String TAG = "Chrono";


    public long pauseOffset;
    private Context context;
    Chronometer chronometer;
    public boolean running;
    private long elapsedMillis;

//    public interface Observer {
//        public void update(Observable o, Object arg);
//    }
//
//    // public interface die bij verandering de startactivity update.


    public Chrono(Context context) {
            pauseOffset = 0;
            running = false;
//        chronometer.setBase(SystemClock.elapsedRealtime());

    }



    public void startChronometer(Chronometer chronometer){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
        Log.d(TAG, "startChronometer: en chronometer is" + SystemClock.elapsedRealtime());
            running  = true;
    }



    public void pauseChronometer(Chronometer chronometer){
            showElapsedTime(chronometer);
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
    }

    public void stopChronometer(Chronometer chronometer){
        Log.d(TAG, "stopChronometer: " + pauseOffset);
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
        Log.d(TAG, "stopChronometer: " + pauseOffset);

    }

    public void showWarningMessage( ){

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
                            dialog.dismiss();;
                        }

                    });
                    AlertDialog alert = builder.create();
                    alert.show();


    }



    public long showElapsedTime(Chronometer chronometer) {
        Log.d(TAG, "showElapsedTime: pauseoffset = " + pauseOffset);
        return elapsedMillis = (long) SystemClock.elapsedRealtime()
                - pauseOffset;


    }
}
