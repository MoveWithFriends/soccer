package com.example.statyoursoccerteam.View;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statyoursoccerteam.Data.Player;
import com.example.statyoursoccerteam.Data.PlayerList;
import com.example.statyoursoccerteam.Data.RetrofitInstance;
import com.example.statyoursoccerteam.Data.SoccerStatsApi;
import com.example.statyoursoccerteam.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyViewAdapter adapter;
    private List<Player> playerArrayList;

    public int NUM_ITEMS = 100;
    public static final String EXTRA_MESSAGE = "com.example.statyoursoccerteam.View.StartActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        recyclerView=(RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        loadJSON();
    }
private void loadJSON() {
    playerArrayList = new ArrayList<>();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build();
//    SoccerStatsApi service = RetrofitInstance.getRetrofitInstance().create(SoccerStatsApi.class);

//    Call<PlayerList> call = service.getPosts();

//    Log.wtf("URL Called", call.request().url() + "");
    SoccerStatsApi soccerStatsApi = retrofit.create(SoccerStatsApi.class);
    Call<List<Player>> call = soccerStatsApi.getPosts();
    call.enqueue(new Callback<List<Player>>() {
        @Override
        public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
            playerArrayList = response.body();
            adapter = new MyViewAdapter(getApplicationContext(), playerArrayList);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onFailure(Call<List<Player>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "Uhoh, something went wrong ...", Toast.LENGTH_LONG).show();
        }
    });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start_game:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "You pressed game start", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}