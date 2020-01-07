package com.example.statyoursoccerteam.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teams {
    public Teams() {
        this.teamName = teamName;
        this.id = id;
    }

    @SerializedName("teamName")
    @Expose
    private String teamName;
    @SerializedName("matches")
    @Expose
    private Object matches;
    @SerializedName("players")
    @Expose
    private Object players;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Object getMatches() {
        return matches;
    }

    public void setMatches(Object matches) {
        this.matches = matches;
    }

    public Object getPlayers() {
        return players;
    }

    public void setPlayers(Object players) {
        this.players = players;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}