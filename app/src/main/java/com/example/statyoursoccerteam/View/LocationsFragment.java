package com.example.statyoursoccerteam.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.statyoursoccerteam.Data.Event;
import com.example.statyoursoccerteam.R;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;


public class LocationsFragment extends Fragment {
    private static final String TAG = "LocationsFragment";
    private GoogleMap mMap;

    private EventsRecyclerViewAdapter mAdapter; // add adapter reference

    private ArrayList<Event> eventArrayList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_detailed, container, false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());

        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_summary_locations, container, false);

        return view;
    }


}
