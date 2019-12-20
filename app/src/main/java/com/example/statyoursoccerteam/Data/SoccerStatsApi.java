package com.example.statyoursoccerteam.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SoccerStatsApi {

    @GET("posts")
    Call<List<Player>> getPosts();
}
