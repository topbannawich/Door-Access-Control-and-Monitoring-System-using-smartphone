package com.example.passagon.final402;

import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class history extends AppCompatActivity {
    private RecyclerView recyclerView;
    private historyAdapter adapter;
    private List< Information> artistList;

    int i ;
    private static final String TAG = "MyActivity";
    Information tmp;
    DatabaseReference dbArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistList = new ArrayList<>();
        adapter = new historyAdapter(this,artistList);
        recyclerView.setAdapter(adapter);

        dbArtists = FirebaseDatabase.getInstance().getReference("history");
        dbArtists.addListenerForSingleValueEvent(valueEventListener);


    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            artistList.clear();

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tmp = new  Information();

                    // tmp = snapshot.getValue(user.class);
                    // artistList.add(tmp);
                    //i++;*/
                    // Toast.makeText(deleteActivity.this, "Success  "+snapshot.getKey(), Toast.LENGTH_SHORT).show();

                    tmp.id=snapshot.getKey();
                    tmp.name=(snapshot.child("name").getValue().toString());

                    tmp.surname=(snapshot.child("surname").getValue().toString());


                    String[] datasp = snapshot.child("date").getValue().toString().split(":");
                    String one = datasp[0]+":"+datasp[1]+":"+datasp[2]+" น.";
                    String two = datasp[3]+"/"+datasp[4]+"/"+datasp[5];
                    String suc = two + "\n" + one;
                    tmp.date=(suc);
                    tmp.note = (snapshot.child("note").getValue().toString());
                    tmp.type = (snapshot.child("type").getValue().toString());
                    tmp.picture = (snapshot.child("picture").getValue().toString());
                    // Toast.makeText(deleteActivity.this, "Success  "+snapshot.child("name").getValue(), Toast.LENGTH_SHORT).show();
                    artistList.add(tmp);


                }
                Collections.reverse(artistList);
                adapter.notifyDataSetChanged();
                //Toast.makeText(deleteActivity.this, "Success  "+i, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
