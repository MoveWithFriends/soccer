package com.example.statyoursoccerteam.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statyoursoccerteam.Data.Player;
import com.example.statyoursoccerteam.Data.SoccerStatsApi;
import com.example.statyoursoccerteam.Data.Teams;
import com.example.statyoursoccerteam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_item;

public class MainActivity extends AppCompatActivity {

    private String URLstring = SoccerStatsApi.jsonUrl;
    private static ProgressDialog mProgressDialog;
    private ArrayList<Teams> teamArrayList;
    private ArrayList<Player> playerArrayList;
    private ArrayList<String> teams = new ArrayList<String>();
    private List<Player> players;
    private LinearLayoutManager linearLayoutManager;
    private Spinner spinner;
    private RecyclerView pList;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spTeams);
        pList = findViewById(R.id.rv);

        retrieveTeamJson("teams");


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedTeam = parent.getSelectedItemPosition();
                Log.d("Team", "Selected: " + selectedTeam);
                Log.d("Team", "URL: " + +selectedTeam);

                retrievePlayers("players" + "/" + (selectedTeam + 1));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Bitcoin", "Nothing selected!");
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(pList.getContext(), linearLayoutManager.getOrientation());
    }


    private void retrieveTeamJson(String url) {

        showSimpleProgressDialog(this, "Loading...", "Fetching Json", false);
//
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            JSONArray js = new JSONArray(response);

                            teamArrayList = new ArrayList<>();

                            for (int i = 0; i < js.length(); i++) {

                                Teams teamModel = new Teams();
                                JSONObject dataobj = js.getJSONObject(i);

                                teamModel.setTeamName(dataobj.getString("teamName"));

                                teamArrayList.add(teamModel);
                            }
                            for (int i = 0; i < teamArrayList.size(); i++) {
                                teams.add(teamArrayList.get(i).getTeamName());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, simple_spinner_item, teams);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinner.setAdapter(spinnerArrayAdapter);
                            removeSimpleProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    //displaying the error in toast if occurrs
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
        removeSimpleProgressDialog();


    }

    private void retrievePlayers(String url) {

        showSimpleProgressDialog(this, "Loading...", "Fetching Json", false);
//
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            JSONObject obj = new JSONObject(response);

                            playerArrayList = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("players");

                            for (int i = 0; i < dataArray.length(); i++) {

                                Player playerModel = new Player();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                playerModel.setFirstName(dataobj.getString("firstName"));
                                playerModel.setLastName(dataobj.getString("lastName"));
                                playerModel.setShirtNumber(dataobj.getString("backNumber"));

                                playerArrayList.add(playerModel);

                            }
                            adapter = new MyViewAdapter(getApplicationContext(), playerArrayList);


                            pList.setHasFixedSize(true);
                            pList.setLayoutManager(linearLayoutManager);
                            pList.addItemDecoration(dividerItemDecoration);
                            pList.setAdapter(adapter);
                            removeSimpleProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
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

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (Exception ie) {
            ie.printStackTrace();
        }
    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        }


    }
}