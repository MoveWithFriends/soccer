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

public class Chrono extends AppCompatActivity {
    private static final String TAG = "Chrono";


    public long pauseOffset;
    private Context context;


    public Chrono( Context context, long pauseOffset) {
        this.context = context;
        this.pauseOffset = pauseOffset;
    }

    public void startChronometer(Chronometer chronometer){
        Log.d(TAG, "startChronometer: " + pauseOffset);


            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();


        Log.d(TAG, "startChronometer: " + pauseOffset);
    }


    public void pauseChronometer(Chronometer chronometer){

        Log.d(TAG, "pauseChronometer: " + pauseOffset);


            showElapsedTime(chronometer);

            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();

         Log.d(TAG, "pauseChronometer: " + pauseOffset);




    }

    public void stopChronometer(Chronometer chronometer){
        Log.d(TAG, "stopChronometer: " + pauseOffset);

                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();



    }

    public void showWarningMessage(final Chronometer chronometer){

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



    public void showElapsedTime(Chronometer chronometer) {

        long elapsedMillis = SystemClock.elapsedRealtime()
                - pauseOffset;
        Toast.makeText(this.context,
                "Elapsed milliseconds: " + elapsedMillis, Toast.LENGTH_SHORT).show();
    }
}
