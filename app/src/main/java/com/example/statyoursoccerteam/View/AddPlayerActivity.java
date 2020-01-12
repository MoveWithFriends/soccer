package com.example.statyoursoccerteam.View;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.layout.simple_spinner_item;
import static com.example.statyoursoccerteam.View.MainActivity.showSimpleProgressDialog;

public class AddPlayerActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private Spinner titleSpinner;
    int teamId = 0;
    Bitmap bitmap;
    private String URLstring = ISoccerStatsApi.jsonUrl;

    CircleImageView playerImage;
    EditText editFirst, editShirtNumber, editLast;
    Button btn, pictureBtn, imageBtn;
    Uri image_uri;

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
        pictureBtn = findViewById(R.id.picture_button);
        imageBtn = findViewById(R.id.image_button);
        playerImage = findViewById(R.id.player_image);

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

        pictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if system osvis >= marshmallow, request runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                        //permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //shown popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        //permissions already granted
                        openCamera();
                    }
                }
                else {
                    openCamera();
                    //system os < marshmallow
                }
            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if system osvis >= marshmallow, request runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                        //permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //shown popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        //permissions already granted
                        pickImageFromGallery();
                    }
                }
                else {
                    pickImageFromGallery();
                    //system os < marshmallow
                }
            }
        });
    }

    private void InsertPlayer() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
            }
        ){
            @Override
            protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("firstName", editFirst.getText().toString());
                    params.put("lastName", editLast.getText().toString());
                    params.put("backNumber", editShirtNumber.getText().toString());
                    params.put("teamId", String.valueOf(teamId));
                    params.put("image", BitMapToString(bitmap));
                    return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(postRequest);
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    openCamera();
                }
                else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //called when image was captured from camera

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //set the image captured to our ImageView
            playerImage.setImageURI(image_uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
