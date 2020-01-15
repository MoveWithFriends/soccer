package com.example.statyoursoccerteam.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statyoursoccerteam.Data.ISoccerStatsApi;
import com.example.statyoursoccerteam.Data.Player;
import com.example.statyoursoccerteam.Data.Teams;
import com.example.statyoursoccerteam.View.MyViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.layout.simple_spinner_item;
import static com.android.volley.VolleyLog.TAG;

public class GetJson {

    private ArrayList<Teams> teamArrayList;

    public static void retrievePlayers(String mainString, String url, Context context, RecyclerView pList, LinearLayoutManager linearLayoutManager, DividerItemDecoration dividerItemDecoration) {
        ProgressDialog.showSimpleProgressDialog(context, "Loading...", "Retrieving players", false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, mainString + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, ">>" + response);

                        try {

                            JSONObject obj = new JSONObject(response);
                            ArrayList playerArrayList = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("players");

                            for (int i = 0; i < dataArray.length(); i++) {

                                Player playerModel = new Player();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                playerModel.setFirstName(dataobj.getString("firstName"));
                                playerModel.setLastName(dataobj.getString("lastName"));
                                playerModel.setShirtNumber(dataobj.getString("backNumber"));
                                if (dataobj.getString("image") != null) {
                                    playerModel.setImage(BitMapToString(dataobj.getString("image")));
                                }

                                playerArrayList.add(playerModel);

                            }
                            MyViewAdapter adapter = new MyViewAdapter(context, playerArrayList);


                            pList.setHasFixedSize(true);
                            pList.setLayoutManager(linearLayoutManager);
                            pList.addItemDecoration(dividerItemDecoration);
                            pList.setAdapter(adapter);
                            com.example.statyoursoccerteam.Controller.ProgressDialog.removeSimpleProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        com.example.statyoursoccerteam.Controller.ProgressDialog.removeSimpleProgressDialog();
    }

    public static void retrieveTeams(Context context, Spinner spinner) {

        ProgressDialog.showSimpleProgressDialog(context, "Loading...", "Retrieving players", false);
        ArrayList<String> teams = new ArrayList<>();
//
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ISoccerStatsApi.jsonUrl + "teams",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, ">>" + response);
                        try {
                            JSONArray js = new JSONArray(response);
                            ArrayList<Teams> teamArrayList = new ArrayList<>();

                            for (int i = 0; i < js.length(); i++) {

                                Teams teamModel = new Teams();
                                JSONObject dataobj = js.getJSONObject(i);

                                teamModel.setTeamName(dataobj.getString("teamName"));

                                teamArrayList.add(teamModel);
                            }
                            for (int i = 0; i < teamArrayList.size(); i++) {
                                teams.add(teamArrayList.get(i).getTeamName());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, simple_spinner_item, teams);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinner.setAdapter(spinnerArrayAdapter);
                            ProgressDialog.removeSimpleProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        ProgressDialog.removeSimpleProgressDialog();
    }

    private static Bitmap BitMapToString(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
