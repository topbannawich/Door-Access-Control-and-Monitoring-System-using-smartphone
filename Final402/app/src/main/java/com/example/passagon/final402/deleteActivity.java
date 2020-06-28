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
import java.util.List;

public class deleteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private userAdapter adapter;
    private List<user> artistList;

int i ;
    private static final String TAG = "MyActivity";
    user tmp;
    DatabaseReference dbArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistList = new ArrayList<>();
        adapter = new userAdapter(this, artistList);
        recyclerView.setAdapter(adapter);

        dbArtists = FirebaseDatabase.getInstance().getReference("User");
        dbArtists.addListenerForSingleValueEvent(valueEventListener);

        adapter.setOnItemClickListener(new userAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(deleteActivity.this);
                    i = position;
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        DatabaseReference db;

                        db = FirebaseDatabase.getInstance().getReference("User").child(artistList.get(i).getUsername());
                         db.removeValue();
                        artistList.remove(i);
                        dialog.dismiss();
                        Toast.makeText(deleteActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();




            }

        });
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            artistList.clear();

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                     tmp = new user();

                // tmp = snapshot.getValue(user.class);
                  // artistList.add(tmp);
                    //i++;*/
                   // Toast.makeText(deleteActivity.this, "Success  "+snapshot.getKey(), Toast.LENGTH_SHORT).show();
                   tmp.setUsername(snapshot.getKey());
                    tmp.setName(snapshot.child("name").getValue().toString());

                    tmp.setSurname(snapshot.child("surname").getValue().toString());
                    tmp.setPassword(snapshot.child("password").getValue().toString());
                    tmp.setPasscode(snapshot.child("passcode").getValue().toString());
                    tmp.setType(snapshot.child("type").getValue().toString());
                   // Toast.makeText(deleteActivity.this, "Success  "+snapshot.child("name").getValue(), Toast.LENGTH_SHORT).show();
                    artistList.add(tmp);

                }
                adapter.notifyDataSetChanged();
                //Toast.makeText(deleteActivity.this, "Success  "+i, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
