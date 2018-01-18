package com.example.leon_azraev.videostore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homepageactivity extends AppCompatActivity { // Manager Page that show user data
    DatabaseReference dbref; //Reference to user DB
    ListView listview;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepageactivity);
        listview = (ListView) findViewById(R.id.ListV);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        listview.setAdapter(adapter);
        dbref = FirebaseDatabase.getInstance().getReference("Users"); //Get Refernce to user and show the data in listview
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String usrVal;
                String passVal;
                int count = 1;
                for (DataSnapshot k : dataSnapshot.getChildren()) {
                    list.add("<><><>User" + count + "<><><>");
                    adapter.notifyDataSetChanged();
                    for (DataSnapshot d : k.getChildren()) {

                        if (d.getKey().equals("userName")) {
                            usrVal = d.getValue().toString();
                            list.add("User Name");
                            adapter.notifyDataSetChanged();
                            list.add(usrVal);
                            adapter.notifyDataSetChanged();
                        }
                        if (d.getKey().equals("password")) {
                            passVal = d.getValue().toString();
                            list.add("Password");
                            adapter.notifyDataSetChanged();
                            list.add(passVal);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    count++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
