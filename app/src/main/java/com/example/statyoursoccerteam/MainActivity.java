package com.example.statyoursoccerteam;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

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
    private static final String JOOST_VIEW_TAG = "Joost";
    private static final String NICK_VIEW_TAG = "Nick";
    private static final String STIJN_VIEW_TAG = "Stijn";
    private static final String JURGEN_VIEW_TAG = "Jurgen";
    private static final String GOALS_LAYOUT_TAG = "Goals";
    private static final String ASSISTS_LAYOUT_TAG = "Assists";
    private static final String SUBSTITUTE_LAYOUT_TAG = "Wissel";

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setFormat("Time\n%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        findViews();
        implementEvents();


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

//        chronometer2 = (Chronometer) findViewById(R.id.counter);
//        chronometer2.setFormat("Time\n%s");
//        chronometer2.setBase(SystemClock.elapsedRealtime());
        return super.onCreateOptionsMenu(menu);

    }

    public void startChronometer(View v){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();;
            running  = true;
        }
    }


    public void pauseChronometer(View v){
        if(running){

            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;

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
        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept
                    // data.

//                      view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);//set background color to your view

                    // Invalidate the view to force a redraw in the new tint
//                      view.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                // Return true; the return value is ignored.

                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

                //TODO check whether entered field is assist or goal field. If so. add assist or goal to dragged player

                // Invalidate the view to force a redraw in the new tint
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
                //TODO IF statement to add goals and assists (return to original position (only IF field players)
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