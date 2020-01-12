package com.example.statyoursoccerteam.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statyoursoccerteam.Data.ISoccerStatsApi;
import com.example.statyoursoccerteam.Data.Player;
import com.example.statyoursoccerteam.Data.Teams;
import com.example.statyoursoccerteam.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import static android.R.layout.simple_spinner_item;
import static com.example.statyoursoccerteam.View.MainActivity.showSimpleProgressDialog;

public class AddPlayerActivity extends AppCompatActivity {

    private Spinner titleSpinner;
    int teamId = 0;
    private String URLstring = ISoccerStatsApi.jsonUrl;
    EditText editFirst, editShirtNumber, editLast;
    Button btn;
    String postUrl = ISoccerStatsApi.jsonUrl + "players";
    private ArrayList<Teams> teamArrayList;
    private ArrayList<String> teams = new ArrayList<String>();
    private Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);

        editFirst = findViewById(R.id.first_name);
        editLast = findViewById(R.id.last_name);
        editShirtNumber = findViewById(R.id.shirt_number);
        spinner = findViewById(R.id.choose_team);
        btn = findViewById(R.id.save_button);

        retrieveTeamJson("teams");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertPlayer();
                finish();
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedTeam = parent.getSelectedItemPosition();
                Log.d("Team", "Selected: " + selectedTeam);
                Log.d("Team", "URL: " + +selectedTeam);

                teamId = selectedTeam + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Bitcoin", "Nothing selected!");
            }
        });
    }

    private void InsertPlayer() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstName", editFirst.getText().toString());
                params.put("lastName", editLast.getText().toString());
                params.put("backNumber", editShirtNumber.getText().toString());
                params.put("teamId", String.valueOf(teamId));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(postRequest);
    }

    private void retrieveTeamJson(String url) {

        showSimpleProgressDialog(this, "Loading...", "Fetching Json", false);

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

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddPlayerActivity.this, simple_spinner_item, teams);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinner.setAdapter(spinnerArrayAdapter);
                            MainActivity.removeSimpleProgressDialog();

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
        MainActivity.removeSimpleProgressDialog();


    }
}

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//}
