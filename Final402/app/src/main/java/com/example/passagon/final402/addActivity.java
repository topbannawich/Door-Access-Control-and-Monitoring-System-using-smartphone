package com.example.passagon.final402;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addActivity extends AppCompatActivity {
    public DatabaseReference ref;
    public TextView tt;
    public Button but;
    String x;
    private List<user> artistList;
    int i ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        tt = (TextView) findViewById(R.id.test);
        artistList = new ArrayList<>();
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        ref = data.getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map map = (Map) dataSnapshot.getValue();
                String tmp = String.valueOf(map.get("status"));
                tt.setText(tmp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        but = (Button) findViewById(R.id.button5);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, Object> m = new HashMap<String, Object>();
                x = "[ON]";
                m.put("status", x);
                ref.updateChildren(m);


            }
        });

        //ref = FirebaseDatabase.getInstance().getReference("User");
      //  ref.addListenerForSingleValueEvent(valueEventListener);
        //Toast.makeText(addActivity.this, artistList.size(), Toast.LENGTH_SHORT).show();

    }


}


