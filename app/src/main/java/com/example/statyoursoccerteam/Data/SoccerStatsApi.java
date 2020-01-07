package com.example.statyoursoccerteam.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SoccerStatsApi {

    String jsonUrl = "https://soccerstatsapp.azurewebsites.net/api/";

    @GET("players/{id}")
    Call<List<Player>> getPlayers(String id);

    @GET("teams")
    Call<List<Teams>> getTeams();

}
