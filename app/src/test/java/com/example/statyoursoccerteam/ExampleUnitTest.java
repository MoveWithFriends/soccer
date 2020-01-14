package com.example.statyoursoccerteam;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Chronometer;

import com.example.statyoursoccerteam.View.StartActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    private Object StartActivity;
    private Context Activity;



    Chronometer mockChronometer;
    Context mockContext;
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void startChrono() {
        //Arrange
        chrono.startChronometer(mockChronometer);



    }

    @Test
    public void timeDifferenceToCheckCalculation() {
        //Arrange
        chrono.startChronometer(mockChronometer);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        chrono.stopChronometer(mockChronometer);
        //Act
        long diff = chrono.showElapsedTime(mockChronometer);


        //Assert
        assertEquals(diff, 3);

    }
}