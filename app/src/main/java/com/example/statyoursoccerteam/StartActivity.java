package com.example.statyoursoccerteam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class StartActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnDragListener {

    Scores scores_pro = new Scores();
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView jurgen;
    private ImageView nick;
    private ImageView joost;
    private ImageView stijn;
    private LinearLayout goals;
    private LinearLayout assists;
    private LinearLayout substitute1;
    private LinearLayout substitute2;
    private LinearLayout substitute3;
    private LinearLayout substitute4;
    private LinearLayout substitute5;
    private LinearLayout goals_against;
    private static final String JOOST_VIEW_TAG = "Joost";
    private static final String NICK_VIEW_TAG = "Nick";
    private static final String STIJN_VIEW_TAG = "Stijn";
    private static final String JURGEN_VIEW_TAG = "Jurgen";
    private static final String GOALS_LAYOUT_TAG = "Goals";
    private static final String ASSISTS_LAYOUT_TAG = "Assists";
    private static final String SUBSTITUTE_LAYOUT_TAG = "Wissel";
    private static final String CHRONOMETER_TAG = "Chrono";
    private static final String GOALS_AGAINST_TAG = "Goals against";

    private Chronometer chronometer2;
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        chronometer = (Chronometer) findViewById(R.id.chronometer_view);
        chronometer.setFormat("Time\n%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        findViews();
        implementEvents();
        Log.d(TAG, "onCreate: implemented events");

        LinearLayout scorefield = (LinearLayout )findViewById(R.id.score_oponent);
        scorefield.setOnLongClickListener(new View.OnLongClickListener() {
            TextView score = (TextView) findViewById(R.id.score_against);
            Scores scores = new Scores();
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onClick: Pressed field");
                score.setText(scores.scoreAgainst());
                return false;
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start, menu);

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Chrono chrono = new Chrono();


        switch (item.getItemId()) {
            case R.id.play_action:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "You pressed start", Toast.LENGTH_SHORT).show();
                startChronometer();
                return true;

            case R.id.pause_action:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Toast.makeText(this, "You pressed pause", Toast.LENGTH_SHORT).show();
                pauseChronometer();
                return true;

            case R.id.stop_action:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Toast.makeText(this, "You pressed stop", Toast.LENGTH_SHORT).show();
                    stopChronometer();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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




    //Find all views and set Tag to all draggable views
    private void findViews() {
        jurgen = (ImageView) findViewById(R.id.jurgen_view);
        jurgen.setTag(JURGEN_VIEW_TAG);
        joost = (ImageView) findViewById(R.id.joost_view);
        joost.setTag(JOOST_VIEW_TAG);
        nick = (ImageView) findViewById(R.id.nick_view);
        nick.setTag(NICK_VIEW_TAG);
        stijn = (ImageView) findViewById(R.id.stijn_view);
        stijn.setTag(STIJN_VIEW_TAG);

    }


    //Implement long click and drag listener
    private void implementEvents() {
        //add or remove any view that you don't want to be dragged
        jurgen.setOnLongClickListener(this);
        joost.setOnLongClickListener(this);
        nick.setOnLongClickListener(this);
        stijn.setOnLongClickListener(this);

        goals = (LinearLayout) findViewById(R.id.goal_a);
        goals.setTag(GOALS_LAYOUT_TAG);

        assists = (LinearLayout) findViewById(R.id.assist_a);
        assists.setTag(ASSISTS_LAYOUT_TAG);

        substitute1 = (LinearLayout) findViewById(R.id.substitute_1);
        substitute2 = (LinearLayout) findViewById(R.id.substitute_2);
        substitute3 = (LinearLayout) findViewById(R.id.substitute_3);
        substitute4 = (LinearLayout) findViewById(R.id.substitute_4);
        substitute5 = (LinearLayout) findViewById(R.id.substitute_5);
        goals_against = (LinearLayout) findViewById(R.id.score_oponent);
        goals_against.setTag(GOALS_AGAINST_TAG);

//        View.OnClickListener onClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if()
//            }
//        };
//        goals_against.setOnClickListener(onClickListener);

        //add or remove any layout view that you don't want to accept dragged view
        findViewById(R.id.a_1_layout).setOnDragListener(this);
        findViewById(R.id.a_2_layout).setOnDragListener(this);
        findViewById(R.id.a_3_layout).setOnDragListener(this);
        findViewById(R.id.a_4__layout).setOnDragListener(this);
        findViewById(R.id.a_5_layout).setOnDragListener(this);
        findViewById(R.id.b_1_layout).setOnDragListener(this);
        findViewById(R.id.b_2_layout).setOnDragListener(this);
        findViewById(R.id.b_3_layout).setOnDragListener(this);
        findViewById(R.id.b_4__layout).setOnDragListener(this);
        findViewById(R.id.b_5_layout).setOnDragListener(this);
        findViewById(R.id.c_1_layout).setOnDragListener(this);
        findViewById(R.id.c_2_layout).setOnDragListener(this);
        findViewById(R.id.c_3_layout).setOnDragListener(this);
        findViewById(R.id.c_4__layout).setOnDragListener(this);
        findViewById(R.id.c_5_layout).setOnDragListener(this);
        findViewById(R.id.d_1_layout).setOnDragListener(this);
        findViewById(R.id.d_2_layout).setOnDragListener(this);
        findViewById(R.id.d_3_layout).setOnDragListener(this);
        findViewById(R.id.d_4__layout).setOnDragListener(this);
        findViewById(R.id.d_5_layout).setOnDragListener(this);

        substitute1.setOnDragListener(this);
        substitute2.setOnDragListener(this);
        substitute3.setOnDragListener(this);
        substitute4.setOnDragListener(this);
        substitute5.setOnDragListener(this);
        assists.setOnDragListener(this);
        goals.setOnDragListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        // Create a new ClipData.
        // This is done in two steps to provide clarity. The convenience method
        // ClipData.newPlainText() can create a plain text ClipData in one step.

        // Create a new ClipData.Item from the ImageView object's tag
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

        // Instantiates the drag shadow builder.
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Starts the drag
        view.startDrag(data//data to be dragged
                , shadowBuilder //drag shadow
                , view//local data about the drag and drop operation
                , 0//no needed flags
        );

        //Set view visibility to INVISIBLE as we are going to drag the view
        view.setVisibility(View.VISIBLE);
        return true;
    }

    // This is the method that the system calls when it dispatches a drag event to the
    // listener.

    @Override
    public boolean onDrag(View view, DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();
        TextView score = (TextView) findViewById(R.id.score_in_advance);

        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    return true;

                }

                return false;

            case DragEvent.ACTION_DRAG_ENTERED:

                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DROP:
                //TODO exchange with players (On pitch AND with substitutes)


                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();

                View vr = (View) event.getLocalState();
                LinearLayout container = (LinearLayout) view;

                if(container.getTag() == null) {
                    ViewGroup owner = (ViewGroup) vr.getParent();
                    owner.removeView(vr);//remove the dragged view
                    container.addView(vr);//Add the dragged view
                    vr.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE

                } else {
                    if (container.getTag().toString().equals("Goals")) {
                        Toast.makeText(this, "" + dragData + " made a goal", Toast.LENGTH_SHORT).show();
                            score.setText(scores_pro.scorePro());
                    } else if (container.getTag().toString().equals("Assists")) {
                        Toast.makeText(this, "" + dragData + " made an assist", Toast.LENGTH_SHORT).show();
                    }
                }

                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();



                // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }


}
