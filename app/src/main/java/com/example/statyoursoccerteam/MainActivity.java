package com.example.statyoursoccerteam;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Choreographer;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView jurgen;
    private ImageView nick;
    private ImageView joost;
    private ImageView stijn;
    private static final String IMAGE_VIEW_TAG = "LAUNCHER LOGO";

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
        jurgen.setTag(IMAGE_VIEW_TAG);
        joost = (ImageView) findViewById(R.id.joost_view);
        joost.setTag(IMAGE_VIEW_TAG);
        nick = (ImageView) findViewById(R.id.nick_view);
        nick.setTag(IMAGE_VIEW_TAG);
        stijn = (ImageView) findViewById(R.id.stijn_view);
        stijn.setTag(IMAGE_VIEW_TAG);

    }


    //Implement long click and drag listener
    private void implementEvents() {
        //add or remove any view that you don't want to be dragged
        jurgen.setOnLongClickListener(this);
        joost.setOnLongClickListener(this);
        nick.setOnLongClickListener(this);
        stijn.setOnLongClickListener(this);

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
        findViewById(R.id.substitute_1).setOnDragListener(this);
        findViewById(R.id.substitute_2).setOnDragListener(this);
        findViewById(R.id.substitute_3).setOnDragListener(this);
        findViewById(R.id.substitute_4).setOnDragListener(this);
        findViewById(R.id.substitute_5).setOnDragListener(this);
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
        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept
                    // data.

                    //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);//set background color to your view

                    // Invalidate the view to force a redraw in the new tint
                    //  view.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

//            case DragEvent.ACTION_DRAG_ENTERED:
//                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
//                // Return true; the return value is ignored.
//
//                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
//
//                // Invalidate the view to force a redraw in the new tint
//                view.invalidate();
//
//                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
//            case DragEvent.ACTION_DRAG_EXITED:
//                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.
//
//                //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
//
//                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
//                view.getBackground().clearColorFilter();
//                // Invalidate the view to force a redraw in the new tint
//                view.invalidate();
//
//                return true;
            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();

                // Turns off any color tints
//                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                owner.removeView(v);//remove the dragged view
                LinearLayout container = (LinearLayout) view;//caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                container.addView(v);//Add the dragged view
                v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE

                // Returns true. DragEvent.getResult() will return true.
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
//                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                // Does a getResult(), and displays what happened.
                if (event.getResult())
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();


                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }


}