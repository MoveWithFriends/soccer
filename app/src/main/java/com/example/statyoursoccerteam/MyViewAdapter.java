package com.example.statyoursoccerteam;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statyoursoccerteam.Data.Person;

import java.util.ArrayList;
import java.util.List;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.PersonViewHolder> {
    private static final String TAG = MyViewAdapter.class.getSimpleName();

    private int  mNumberItems;;
    private ArrayList<Person> mPersons;



    class PersonViewHolder extends RecyclerView.ViewHolder {


        // Create a TextView variable called listItemNumberView
        TextView listItemPersonNameView;
        TextView listItemPersonPhoneView;
        ImageView listItemPersonImage;


        // Create a constructor for PersonViewHolder that accepts a View called itemView as a parameter

        public PersonViewHolder(View itemView) {
            super(itemView);
            listItemPersonNameView = (TextView) itemView.findViewById(R.id.list_item_name);
            listItemPersonPhoneView =(TextView) itemView.findViewById(R.id.list_item_phone);
            listItemPersonImage = (ImageView) itemView.findViewById(R.id.list_item_image);
        }

        void bind(int listIndex){
            listItemPersonNameView.setText(String.valueOf(listIndex));
            listItemPersonPhoneView.setText(String.valueOf(listIndex));
        }


    }

    /*
    ADAPTER
 */
// Specify how many views adapter hold

    private Context context;

// Store a member variable for the titles


    // Populate that var in the constructor
    public MyViewAdapter(Context context, int numberOfItems, ArrayList<Person> persons) {
        this.mNumberItems = numberOfItems;
        mPersons = persons;
        this.context = context;
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.v("onCreateViewHolder", "onCreateViewHolder is called !");
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recycler_view_item;

        // Inflate our new item view using a LayoutInflater. It takes the ID of layout in xml.
        // Then --> inflates or converts this collection of view groups and views.
        LayoutInflater inflater = LayoutInflater.from(context);


        // Set to false, so that the inflated layout will not be
        // immediately attached to its parent viewgroup.
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false );
        PersonViewHolder viewHolder = new PersonViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        // Get the data model based on position
        Log.d(TAG, "onBindViewHolder: " + mPersons.toString());
        Person persons = mPersons.get(position);
        // Set item views based on your views and data model
        TextView textView1 = holder.listItemPersonNameView;
        textView1.setText(persons.getName());
        TextView textView2 = holder.listItemPersonPhoneView;
        textView2.setText(persons.getPhone());
        ImageView imageView = holder.listItemPersonImage;
//        imageView.setImageBitmap(joosticon.png);

    }


    @Override
    public int getItemCount() {
        mNumberItems = mPersons.size();
        return mNumberItems;
    }


}
