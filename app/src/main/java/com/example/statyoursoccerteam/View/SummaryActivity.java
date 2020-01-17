package com.example.statyoursoccerteam.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.statyoursoccerteam.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SummaryActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "SummaryActivity";
    private boolean mTwoPane = false;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView latTextView, lonTextView;
    public double lastLatitude;
    public double lastLongitude;
    private GoogleMap mMap;
    Marker marker;
    public LatLng lastLocation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: starts");
        setContentView(R.layout.activity_summary);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        mTwoPane = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ||
                (getResources().getDisplayMetrics().widthPixels > 1200);

        Log.d(TAG, "onCreate: twoPane is " + mTwoPane);

        View locationsFragment = findViewById(R.id.map_fragment);
        View eventsFragment = findViewById(R.id.fragment);

        if(mTwoPane) {
            Log.d(TAG, "onCreate: twoPane mode");
            eventsFragment.setVisibility(View.VISIBLE);
            locationsFragment.setVisibility(View.VISIBLE);
        }  else {
            Log.d(TAG, "onCreate: single pane, not editing");
            // Show left hand fragment
            eventsFragment.setVisibility(View.VISIBLE);
            // Hide the location fragment
            locationsFragment.setVisibility(View.GONE);
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();


    }


    @SuppressLint("MissingPermission")
    public void getLastLocation(){
        Log.d(TAG, "getLastLocation: ");
        if (checkPermissions()) {
            Log.d(TAG, "getLastLocation: check permission = " + checkPermissions());
            if (isLocationEnabled()) {
                Log.d(TAG, "getLastLocation: check location enabled = " + isLocationEnabled());
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Log.d(TAG, "onComplete: taskResult " + task.getResult());
                                Location location = task.getResult();
                                if (location == null) {
                                    Log.d(TAG, "onComplete: location = null");
                                    requestNewLocationData();
                                } else {
                                    Log.d(TAG, "onComplete: permission gives LatLng");
                                    lastLatitude = location.getLatitude();
                                    lastLongitude = location.getLongitude();
                                    Log.d(TAG, "onComplete: lat " + lastLatitude + " long " + lastLongitude);
                                    createMap();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){
        Log.d(TAG, "requestNewLocationData: called");

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d(TAG, "requestNewLocationData: " + mFusedLocationClient);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
        Log.d(TAG, "request latituded: " + lastLatitude);
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Log.d(TAG, "onLocationResult: LocationCallBack called");
            Location mLastLocation = locationResult.getLastLocation();
            lastLatitude = mLastLocation.getLatitude();
            lastLongitude = mLastLocation.getLongitude();
//            onMapReady(mMap);

        }
    };

    private boolean checkPermissions() {
        Log.d(TAG, "checkPermissions: ");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        Log.d(TAG, "requestPermissions: ");
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        Log.d(TAG, "isLocationEnabled: ");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        Log.d(TAG, "onResume: ");
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: starts");
        mMap = googleMap;

        createMap();
    }

    private void createMap() {
        Log.d(TAG, "createMap: starts");

        lastLocation = new LatLng(lastLatitude, lastLongitude);
        Log.d(TAG, "createMap: " + lastLocation);
        // Add a marker in Sydney and move the camera
        UiSettings settings = mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setTiltGesturesEnabled(true);
        settings.setMyLocationButtonEnabled(true);

        marker = mMap.addMarker(new MarkerOptions()
                .position(lastLocation)
                .title("Your location")
        );
        marker.setTag(0);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, 16f));

        mMap.setMinZoomPreference(10f);
        mMap.setMaxZoomPreference(22f);

        // Construct a CameraPosition focusing on LD and animate the camera to that position.
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(lastLocation)      // Sets the center of the map to LD
                .zoom(16)            // Sets the zoom
                .bearing(90)         // Sets the orientation of the camera to east
                .tilt(45)            // Sets the tilt of the camera to 45 degrees
                .build();            // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}



