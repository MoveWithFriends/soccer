package com.example.statyoursoccerteam.View;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statyoursoccerteam.Controller.GetJson;
import com.example.statyoursoccerteam.Data.ISoccerStatsApi;
import com.example.statyoursoccerteam.Data.Teams;
import com.example.statyoursoccerteam.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPlayerActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE_CAMERA = 1000;
    private static final int PERMISSION_CODE_GALLERY = 1002;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int GALLERY_CHOOSE_CODE = 1003;
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

        GetJson.retrieveTeams(AddPlayerActivity.this, spinner);

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
                        requestPermissions(permission, PERMISSION_CODE_CAMERA);
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
                        requestPermissions(permission, PERMISSION_CODE_GALLERY);
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
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_CHOOSE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        switch (requestCode) {
            case PERMISSION_CODE_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    openCamera();
                }
                else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
            case PERMISSION_CODE_GALLERY: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //called when image was captured from camera

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            //set the image captured to our ImageView
            playerImage.setImageURI(image_uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(resultCode == RESULT_OK && requestCode == GALLERY_CHOOSE_CODE){
            image_uri = data.getData();
            playerImage.setImageURI(image_uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
