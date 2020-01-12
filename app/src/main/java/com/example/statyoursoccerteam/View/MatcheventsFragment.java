package com.example.statyoursoccerteam.View;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statyoursoccerteam.Data.Event;
import com.example.statyoursoccerteam.R;

import java.util.ArrayList;


public class MatcheventsFragment extends Fragment {
    private static final String TAG = "MatcheventsFragment";
    private EventsRecyclerViewAdapter mAdapter; // add adapter reference

    private ArrayList<Event> eventArrayList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;


    public MatcheventsFragment() {
        Log.d(TAG, "MatcheventsFragment: starts");
    }

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
        View view = inflater.inflate(R.layout.fragment_summary_matchevents, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.events_rv2);
        recyclerView.setLayoutManager(linearLayoutManager);

        Event first = new Event("Joost", "Goal", "1");
        Event second = new Event("Jurgen", "Assist", "2");
        Event third = new Event("Nick", "Goal", "3");
        Event fourth = new Event("Stijn", "Goal", "4");
        Event fifth = new Event("Kees", "Goal", "5");
        Event sixth = new Event("Frans", "Goal", "10");
        Event seventh = new Event("Frans", "Goal", "41");
        Event eight = new Event("Frans", "Red card", "45");
        Event nineth = new Event("Jurgen", "Goal", "65");

        eventArrayList = new ArrayList<>();


        eventArrayList.add(first);
        eventArrayList.add(second);
        eventArrayList.add(third);
        eventArrayList.add(fourth);
        eventArrayList.add(fifth);
        eventArrayList.add(sixth);
        eventArrayList.add(seventh);
        eventArrayList.add(eight);
        eventArrayList.add(nineth);

        
        mAdapter = new EventsRecyclerViewAdapter(eventArrayList);

            Log.d(TAG, "onCreate: madapter items " + mAdapter.getItemId(1));


        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(dividerItemDecoration);


        if (mAdapter == null) {
            mAdapter = new EventsRecyclerViewAdapter(null);
        }

        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
