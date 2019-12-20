package com.example.statyoursoccerteam;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statyoursoccerteam.Data.Person;
import com.example.statyoursoccerteam.Data.PersonBuilder;

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

        data.add(new PersonBuilder().setFirstName("Joost").setLastName("Oomen").createPerson());
        data.add(new PersonBuilder().setFirstName("Jurgen ").setLastName("Paapen").createPerson());
        data.add(new PersonBuilder().setFirstName("Nick ").setLastName("Sluijters").createPerson());
        data.add(new PersonBuilder().setFirstName("Stijn ").setLastName("Pijman").createPerson());
        data.add(new PersonBuilder().setFirstName("Corne ").setLastName("Snoijs").createPerson());
        data.add(new PersonBuilder().setFirstName("Wouter ").setLastName("Plaatman").createPerson());
        data.add(new PersonBuilder().setFirstName("Johan ").setLastName("Potters").createPerson());
        data.add(new PersonBuilder().setFirstName("Paul ").setLastName("de Mast").createPerson());
        data.add(new PersonBuilder().setFirstName("Frans ").setLastName("Spijkerman").createPerson());
        data.add(new PersonBuilder().setFirstName("Joost").setLastName("Oomen").createPerson());
        data.add(new PersonBuilder().setFirstName("Jurgen ").setLastName("Paapen").createPerson());
        data.add(new PersonBuilder().setFirstName("Nick ").setLastName("Sluijters").createPerson());
        data.add(new PersonBuilder().setFirstName("Stijn ").setLastName("Pijman").createPerson());
        data.add(new PersonBuilder().setFirstName("Corne ").setLastName("Snoijs").createPerson());
        data.add(new PersonBuilder().setFirstName("Wouter ").setLastName("Plaatman").createPerson());
        data.add(new PersonBuilder().setFirstName("Johan ").setLastName("Potters").createPerson());
        data.add(new PersonBuilder().setFirstName("Paul ").setLastName("de Mast").createPerson());
        data.add(new PersonBuilder().setFirstName("Frans ").setLastName("Spijkerman").createPerson());


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