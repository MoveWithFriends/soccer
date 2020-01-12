package com.example.statyoursoccerteam.Data;

import android.content.Context;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISoccerStatsApi {

    String jsonUrl = "https://soccerstatsapp.azurewebsites.net/api/";
}
