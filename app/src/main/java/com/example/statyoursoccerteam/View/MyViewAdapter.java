package com.example.statyoursoccerteam.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statyoursoccerteam.Data.Player;
import com.example.statyoursoccerteam.R;
import java.util.ArrayList;
import java.util.List;
import java.util.List;


public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.PersonViewHolder> {

    private Context context;
    private List<Player> playerList = new ArrayList<>();

    public MyViewAdapter(Context context, List<Player> playerList) {
        this.context = context;
        this.playerList = playerList;
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent,false);

        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.firstName.setText(playerList.get(position).getFirstName());
        holder.shirtNumber.setText(playerList.get(position).getShirtNumber());
        holder.lastName.setText(playerList.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        public TextView firstName, shirtNumber, lastName;


        public PersonViewHolder(View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.playerFirstName);
            lastName = itemView.findViewById(R.id.playerLastName);
            shirtNumber = itemView.findViewById(R.id.shirtNumber);

        }
    }
}
