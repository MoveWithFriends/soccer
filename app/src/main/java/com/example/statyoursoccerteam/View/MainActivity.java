package com.example.statyoursoccerteam.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statyoursoccerteam.Controller.GetJson;
import com.example.statyoursoccerteam.Data.ISoccerStatsApi;
import com.example.statyoursoccerteam.Data.Player;
import com.example.statyoursoccerteam.Data.Teams;
import com.example.statyoursoccerteam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;

    private String URLstring = ISoccerStatsApi.jsonUrl;
    private ArrayList<Teams> teamArrayList;
    private ArrayList<Player> playerArrayList;
    private ArrayList<String> teams = new ArrayList<>();
    private List<Player> players;
    private LinearLayoutManager linearLayoutManager;
    private Spinner spinner;
    private RecyclerView pList;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adapter;
    private Teams teamModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spTeams);
        pList = findViewById(R.id.rv);

        GetJson.retrieveTeams(MainActivity.this , spinner);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPlayerActivity.class);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedTeam = parent.getSelectedItemPosition();
                Log.d("Team", "Selected: " + selectedTeam);
                Log.d("Team", "URL: " + +selectedTeam);

                GetJson.retrievePlayers(URLstring,"players" + "/" + (selectedTeam + 1) ,MainActivity.this, pList, linearLayoutManager, dividerItemDecoration);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Team", "Nothing selected!");
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(pList.getContext(), linearLayoutManager.getOrientation());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start_game:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "You pressed game start", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


}