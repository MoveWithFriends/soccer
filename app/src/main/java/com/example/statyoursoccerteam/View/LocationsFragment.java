package com.example.statyoursoccerteam.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.statyoursoccerteam.Data.Event;
import com.example.statyoursoccerteam.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class LocationsFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "LocationsFragment";
    private GoogleMap mMap;

    private EventsRecyclerViewAdapter mAdapter; // add adapter reference
    View view;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    MapView mapView;
    GoogleMap map;
    Marker marker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.mapview);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_summary_locations, container, false);

        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        Log.d(TAG, "onMapReady: starts");
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        SummaryActivity summaryActivity = new SummaryActivity();


        LatLng POS_LD = new LatLng(40.698247, -74.044502);
//        Log.d(TAG, "createMap: " + POS_LD);

        mMap.addMarker(new MarkerOptions()
                .position(POS_LD));


        // Add a marker in at location and move the camera
        UiSettings settings = mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setTiltGesturesEnabled(true);
        settings.setMyLocationButtonEnabled(true);

        marker = mMap.addMarker(new MarkerOptions()
                .position(POS_LD)
                .title("Your location")
        );
        marker.setTag(0);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(POS_LD, 16f));

        mMap.setMinZoomPreference(10f);
        mMap.setMaxZoomPreference(22f);

        // Construct a CameraPosition focusing on LD and animate the camera to that position.
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(POS_LD)      // Sets the center of the map to LD
                .zoom(16)            // Sets the zoom
                .bearing(90)         // Sets the orientation of the camera to east
                .tilt(45)            // Sets the tilt of the camera to 45 degrees
                .build();            // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mapView.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

//        LatLng POS_LD = new LatLng(summaryActivity.currentLatitude, summaryActivity.currentLongitude);
//        Log.d(TAG, "createMap: " + POS_LD);
//
////        // Add a marker in Sydney and move the camera
////        UiSettings settings = mMap.getUiSettings();
////        settings.setZoomControlsEnabled(true);
////        settings.setTiltGesturesEnabled(true);
////        settings.setMyLocationButtonEnabled(true);
//
//        marker = mMap.addMarker(new MarkerOptions()
//                .position(POS_LD)
//                .title("Your location")
//        );
//        marker.setTag(0);
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(POS_LD, 16f));
//
//        mMap.setMinZoomPreference(10f);
//        mMap.setMaxZoomPreference(22f);
//
//        // Construct a CameraPosition focusing on LD and animate the camera to that position.
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(POS_LD)      // Sets the center of the map to LD
//                .zoom(16)            // Sets the zoom
//                .bearing(90)         // Sets the orientation of the camera to east
//                .tilt(45)            // Sets the tilt of the camera to 45 degrees
//                .build();            // Creates a CameraPosition from the builder
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
