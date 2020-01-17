package com.example.statyoursoccerteam.Controller;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.statyoursoccerteam.R;
import com.example.statyoursoccerteam.View.SummaryActivity;

import org.junit.Rule;
import org.junit.Test;

public class RecyclerViewTest2 {

    @Rule
    public ActivityTestRule<SummaryActivity> activity = new ActivityTestRule<>(SummaryActivity.class);

    @Test
    public void scrollToPosition(){
        Espresso.onView(ViewMatchers.withId(R.id.events_rv2))
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, ViewActions.longClick()));
    }

}