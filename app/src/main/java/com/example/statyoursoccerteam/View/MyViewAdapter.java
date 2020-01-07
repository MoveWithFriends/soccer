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

import java.util.List;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.PersonViewHolder> {

    private Context context;
    private List<Player> playerList;

    public MyViewAdapter(Context context, List<Player> playerList) {
        this.context = context;
        this.playerList = playerList;
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_player, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.IdTv.setText(playerList.get(position).getId());
        holder.UserIdTv.setText(playerList.get(position).getUserId());
        holder.TitleTv.setText(playerList.get(position).getTitle());
        holder.TextTv.setText(playerList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView UserIdTv, IdTv, TitleTv, TextTv;

        public PersonViewHolder(View itemView) {
            super(itemView);
            UserIdTv = itemView.findViewById(R.id.list_item_userId);
            IdTv = itemView.findViewById(R.id.list_item_Id);
            TitleTv = itemView.findViewById(R.id.list_item_Title);
            TextTv = itemView.findViewById(R.id.list_item_Text);
        }
    }
}
