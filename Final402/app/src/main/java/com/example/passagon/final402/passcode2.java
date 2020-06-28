package com.example.passagon.final402;

import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class passcode2 extends AppCompatActivity  {
    String passcode;
    Pinview p;
    String ans;
    Context tmp;

    public DatabaseReference ref;
    public FirebaseDatabase database;
    TextView lo, dis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode2);
        lo = (TextView) findViewById(R.id.location);
        dis = (TextView) findViewById(R.id.location2);





        database = FirebaseDatabase.getInstance();
        ref = database.getReference("status");

        p= (Pinview) findViewById(R.id.pinview2);
        p.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {


                ans = pinview.getValue();
                if (ans.equals(PreferenceUtils.getPasscode(passcode2.this))){
                    int x=1;
                    ref.setValue(x);
                   ref = database.getReference("tmp");
                   ref.setValue(PreferenceUtils.getUsername(passcode2.this));
                    finish();

                }
                else
                {

                    Toast.makeText(passcode2.this, "Passcode Invalid", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = getIntent();
                    startActivity(intent);
                }
            }
        });
    }





}
