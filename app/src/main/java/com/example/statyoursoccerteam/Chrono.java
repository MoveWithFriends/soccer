package com.example.statyoursoccerteam;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Observable;

public class Chrono {
    private static final String TAG = "Chrono";


    public long pauseOffset;
    private Context context;
    Chronometer chronometer;
    private boolean running;

    public interface Observer {
        public void update(Observable o, Object arg);
    }

    // public interface die bij verandering de startactivity update.


    public Chrono(Context context) {


    }



    public void startChronometer( ){
        Log.d(TAG, "startChronometer: " + pauseOffset);

                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();

        Log.d(TAG, "startChronometer: " + pauseOffset);
    }


    public void pauseChronometer(){

        Log.d(TAG, "pauseChronometer: " + pauseOffset);


            showElapsedTime(chronometer);

            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();

         Log.d(TAG, "pauseChronometer: " + pauseOffset);




    }

    public void stopChronometer(){
        Log.d(TAG, "stopChronometer: " + pauseOffset);

                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();



    }

    public void showWarningMessage( ){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Do you want to stop ?");
                    builder.setIcon(R.drawable.ic_warning_black_24dp);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            stopChronometer();
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



    public void showElapsedTime(Chronometer chronometer) {

        long elapsedMillis = SystemClock.elapsedRealtime()
                - pauseOffset;
        Toast.makeText(this.context,
                "Elapsed milliseconds: " + elapsedMillis, Toast.LENGTH_SHORT).show();
    }
}
