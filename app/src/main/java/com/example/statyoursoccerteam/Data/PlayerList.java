package com.example.statyoursoccerteam.Data;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayerList {

    @SerializedName("playerList")
    private ArrayList<Player> playerList;

    public ArrayList<Player> getPlayerArrayList(){
        return playerList;
    }
}
