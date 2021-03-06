package com.example.statyoursoccerteam.View;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.statyoursoccerteam.Chrono;
import com.example.statyoursoccerteam.R;
import com.example.statyoursoccerteam.Scores;

import static androidx.recyclerview.widget.RecyclerView.INVISIBLE;
import static androidx.recyclerview.widget.RecyclerView.OnLongClickListener;


public class StartActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnDragListener {

    Scores scores_pro = new Scores();
    private static final String TAG = "startactivity";
    private ImageView jurgen;
    private ImageView nick;
    private ImageView joost;
    private ImageView stijn;
    private ImageView frans;
    private LinearLayout goals;
    private LinearLayout assists;
    private LinearLayout substitute1;
    private LinearLayout substitute2;
    private LinearLayout substitute3;
    private LinearLayout substitute4;
    private LinearLayout substitute5;
    private LinearLayout goals_against;
    private LinearLayout yellowcard;
    private LinearLayout redcard;
    private static final String FRANS_VIEW_TAG = "Frans";
    private static final String JOOST_VIEW_TAG = "Joost";
    private static final String NICK_VIEW_TAG = "Nick";
    private static final String STIJN_VIEW_TAG = "Stijn";
    private static final String JURGEN_VIEW_TAG = "Jurgen";
    private static final String GOALS_LAYOUT_TAG = "Goals";
    private static final String ASSISTS_LAYOUT_TAG = "Assists";
    private static final String SUBSTITUTE_LAYOUT_TAG = "Wissel";
    private static final String CHRONOMETER_TAG = "Chrono";
    private static final String GOALS_AGAINST_TAG = "Goals against";
    private static final String YELLOW_CARD_LAYOUT_TAG = "Yellow card";
    private static final String RED_CARD_LAYOUT_TAG = "Red card";

    public static long starttimer = 0;
    public Chrono chrono = Chrono.getInstance(this);
    Chronometer chronometer;
    private static StartActivity instanceStartActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (chronometer!=null) {
            chrono.stopChronometer(chronometer);
        }




        findViews();
        implementEvents();
        Log.d(TAG, "onCreate: implemented events");

        LinearLayout scorefield = (LinearLayout) findViewById(R.id.score_oponent);
        scorefield.setOnLongClickListener(new OnLongClickListener() {
            TextView score = (TextView) findViewById(R.id.score_against);
            Scores scores = new Scores();

            @Override
            public boolean onLongClick(View v) {
                score.setText(scores.scoreAgainst());
                return false;
            }
        });
        instanceStartActivity = this;

    }

    public static StartActivity getInstance(){
        return instanceStartActivity;
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        chrono.running = false;

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + chrono.running);
        chrono.running = false;
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: is being called");
        chrono.running = false;

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: being called");
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.start, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {


        switch (menuItem.getItemId()) {

            case R.id.play_action:
                chronometer = (Chronometer) findViewById(R.id.chronometer_view3);
                Toast.makeText(this, "You pressed start", Toast.LENGTH_SHORT).show();
                chrono.startChronometer(chronometer);
                return true;

            case R.id.pause_action:
                chrono.pauseChronometer(chronometer);
                Toast.makeText(this, "This event took place in minute " + (chrono.showElapsedTime(chronometer)), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.stop_action:
                Chrono.getInstance().showWarningMessage(chronometer);
                Toast.makeText(this, "This event took place in minute " + (chrono.showElapsedTime(chronometer)), Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(menuItem);
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
        substitute1.setTag(SUBSTITUTE_LAYOUT_TAG);
        substitute2 = (LinearLayout) findViewById(R.id.substitute_2);
        substitute2.setTag(SUBSTITUTE_LAYOUT_TAG);
        substitute3 = (LinearLayout) findViewById(R.id.substitute_3);
        substitute3.setTag(SUBSTITUTE_LAYOUT_TAG);
        substitute4 = (LinearLayout) findViewById(R.id.substitute_4);
        substitute4.setTag(SUBSTITUTE_LAYOUT_TAG);
        substitute5 = (LinearLayout) findViewById(R.id.substitute_5);
        substitute5.setTag(SUBSTITUTE_LAYOUT_TAG);
        goals_against = (LinearLayout) findViewById(R.id.score_oponent);
        goals_against.setTag(GOALS_AGAINST_TAG);
        yellowcard = (LinearLayout) findViewById(R.id.yellow_card);
        yellowcard.setTag(YELLOW_CARD_LAYOUT_TAG);
        redcard = (LinearLayout) findViewById(R.id.red_card);
        redcard.setTag(RED_CARD_LAYOUT_TAG);

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
        yellowcard.setOnDragListener(this);
        redcard.setOnDragListener(this);
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
        view.setVisibility(View.INVISIBLE);
        return true;
    }

    // This is the method that the system calls when it dispatches a drag event to the
    // listener.

    @Override
    public boolean onDrag(View view, DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();
        TextView score = (TextView) findViewById(R.id.score_in_advance);
        View dragged = (View) event.getLocalState();
        Log.d(TAG, "onDrag: action is " + event.getResult());
        boolean remove = false;

        // Gets a handle to the Clipboard Manager
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

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


                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();

                View vr = (View) event.getLocalState();
                Log.d(TAG, "onDrag: view" + view.toString());
                LinearLayout container = (LinearLayout) view;
                Log.d(TAG, "onDrag: container" + container.toString());


                if (container.getTag() != null) {
                    switch (container.getTag().toString()) {
                        case RED_CARD_LAYOUT_TAG:
                            remove = true;
                            Log.d(TAG, "onDrag: red card remove " + remove);
                            Toast.makeText(this, "" + dragData + " received a red card in minute "+ chrono.showElapsedTime(chronometer), Toast.LENGTH_SHORT).show();
                            break;
                        case YELLOW_CARD_LAYOUT_TAG:
                            Toast.makeText(this, "" + dragData + " received a yellow card in minute "+ chrono.showElapsedTime(chronometer), Toast.LENGTH_SHORT).show();
                            break;
                        case GOALS_LAYOUT_TAG:
                            Toast.makeText(this, "" + dragData + " made a goal in minute " + chrono.showElapsedTime(chronometer), Toast.LENGTH_SHORT).show();
                            score.setText(scores_pro.scorePro());
                            break;
                        case ASSISTS_LAYOUT_TAG:
                            Toast.makeText(this, "" + dragData + " made an assist ", Toast.LENGTH_SHORT).show();
                            break;
                        case SUBSTITUTE_LAYOUT_TAG:
                            swapPlayers(dragged, vr, container);
                            Toast.makeText(this, "" + dragData + " got substituted ", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    swapPlayers(dragged, vr, container);
                }

                vr.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                if(remove){
                    Log.d(TAG, "onDrag: remove? " + remove);
                    dragged.setVisibility(INVISIBLE);
                }
                // Returns true. DragEvent.getResult() will return true.
                return true;


            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                // An unknown action type was received.

        }
        return false;
    }


    private void swapPlayers(View dragged, View vr, LinearLayout container) {
        if (container.getChildAt(0) != null) {
            View target = container.getChildAt(0);

            LinearLayout oldOwner = (LinearLayout) dragged.getParent();
            if (oldOwner != container) {
                oldOwner.removeView(dragged);
                container.removeView(target);
                container.addView(dragged);
                oldOwner.addView(target);
            }
        }
        //remove the dragged view
        ViewGroup oldOwners = (ViewGroup) vr.getParent();
        oldOwners.removeView(vr);
        container.addView(vr);//Add the dragged view
        vr.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
    }

    @Override
    public void onBackPressed() {
        if (chronometer != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Do you want to go back and lose all changes ?");
            builder.setIcon(R.drawable.ic_warning_black_24dp);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    restartMainActivity();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }

            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            restartMainActivity();
        }

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    public void restartMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startSummaryActivity(){
        Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }

}
