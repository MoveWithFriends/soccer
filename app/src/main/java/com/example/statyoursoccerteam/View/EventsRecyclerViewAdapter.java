package com.example.statyoursoccerteam.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statyoursoccerteam.Data.Event;
import com.example.statyoursoccerteam.R;

import java.util.ArrayList;
import java.util.List;

public class EventsRecyclerViewAdapter extends RecyclerView.Adapter<EventsRecyclerViewAdapter.EventsViewHolder> {

    private static final String TAG = "EventsRecyclerViewAdapter";
    private Context context;
    private List<Event> eventList = new ArrayList<>();

    public EventsRecyclerViewAdapter(ArrayList<Event> events) {
        Log.d(TAG, "EventsRecyclerViewAdapter: starts");

        this.eventList = events;
        Log.d(TAG, "EventsRecyclerViewAdapter: " + eventList.get(0).getPlayerName());
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: starts");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listevents, parent, false);

        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + eventList.get(position).getPlayerName());
        final int d = Log.d(TAG, "onBindViewHolder: " + holder.eventMinute.toString());


        final String eventMinute = eventList.get(position).getEventMinute() + "'";
        Log.d(TAG, "onBindViewHolder: " + eventMinute);
        final String eventName = eventList.get(position).getEventName();
        Log.d(TAG, "onBindViewHolder: " + eventName);
        holder.eventMinute.setText(eventMinute);
        holder.eventName.setText(eventName);
        holder.eventPlayer.setText(eventList.get(position).getPlayerName());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventsViewHolder extends RecyclerView.ViewHolder {

        TextView eventMinute, eventName, eventPlayer;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "EventsViewHolder: starts");
            eventMinute = itemView.findViewById(R.id.eventMinute);
            this.eventName = itemView.findViewById(R.id.eventType);
            this.eventPlayer = itemView.findViewById(R.id.eventPlayer);

        }
    }
}
