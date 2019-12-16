package com.example.statyoursoccerteam;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statyoursoccerteam.Data.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public int NUM_ITEMS = 100;
    public static final String EXTRA_MESSAGE = "com.example.statyoursoccerteam.StartActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create fake News
        ArrayList<Person> data = new ArrayList<>();

        data.add(new Person("Joost Oomen", "0618194626"));
        data.add(new Person("Jurgen Paapen", "0617896534"));
        data.add(new Person("Nick Sluijters", "0678965432"));
        data.add(new Person("Stijn Pijman", "0698765213"));
        data.add(new Person("Corne Snoijs", "0642536711"));
        data.add(new Person("Wouter Plaatman", "0664646464"));
        data.add(new Person("Johan Potters", "0699786754"));
        data.add(new Person("Paul de Mast", "0611223344"));
        data.add(new Person("Frans Spijkerman", "0610101010"));
        data.add(new Person("Wouter Plaatman", "0664646464"));
        data.add(new Person("Johan Potters", "0699786754"));
        data.add(new Person("Paul de Mast", "0611223344"));
        data.add(new Person("Frans Spijkerman", "0610101010"));
        data.add(new Person("Wouter Plaatman", "0664646464"));
        data.add(new Person("Johan Potters", "0699786754"));
        data.add(new Person("Paul de Mast", "0611223344"));
        data.add(new Person("Frans Spijkerman", "0610101010"));
        data.add(new Person("Wouter Plaatman", "0664646464"));
        data.add(new Person("Johan Potters", "0699786754"));
        data.add(new Person("Paul de Mast", "0611223344"));
        data.add(new Person("Frans Spijkerman", "0610101010"));

// Get a reference of our RecyclerView from xml
        // It allows us the do things like set the Adapter of the RecyclerView and toggle the
        // visibility
        RecyclerView mPersonList = (RecyclerView) findViewById(R.id.rv_persons);

        // GridLayoutManager is responsible for measuring and positioning item views within a RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPersonList.setLayoutManager(layoutManager);


        // Don't change the size of the content
        mPersonList.setHasFixedSize(true);
        MyViewAdapter adapter = new MyViewAdapter(getApplicationContext(), NUM_ITEMS, data);

        mPersonList.setAdapter(adapter);


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