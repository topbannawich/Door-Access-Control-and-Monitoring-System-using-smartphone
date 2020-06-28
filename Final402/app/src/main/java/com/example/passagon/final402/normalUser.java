package com.example.passagon.final402;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationListener;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;

import java.util.Timer;
import java.util.TimerTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


public class normalUser extends AppCompatActivity implements LocationListener
{
    public DatabaseReference ref;
    public DatabaseReference snap;
    public FirebaseDatabase database;
    public DatabaseReference type;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    Long tsLong;
    String ts;
    WifiInfo wifiInfo;
    WifiManager wifiManager;
    TextView doorText;
    TextView conection;
    TextView battery;
    Timer timer;
    Timer timer2;
    ImageView image;
    double distanace;
    protected LocationManager locationManager;
    double latitude;
    double longitude;
    protected LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usernomal);


        timer = new Timer();
        timer.schedule(new normalUser.MyTask(), 0, 600000);

        conection = (TextView) findViewById(R.id.interntCo1n);
        doorText = (TextView) findViewById(R.id.door1);
        battery = (TextView) findViewById(R.id.battery1);


        CheckPermission();







        database = FirebaseDatabase.getInstance();
        ref = database.getReference("status");


        Button button = (Button) findViewById(R.id.ac);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();

                Toast.makeText(normalUser.this, " Invalid :"+distanace, Toast.LENGTH_SHORT).show();
                if (distanace <= 200.0) {
                    Intent intent = new Intent(normalUser.this, passcode2.class);
                    startActivity(intent);


                } else if (distanace > 200) {


                    Intent intent = new Intent(normalUser.this, passcode3.class);
                    startActivity(intent);


                }


                //startActivity(new Intent(MainActivity.this,addActivity.class));
            }
        });
        Button button4 = (Button) findViewById(R.id.ab);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(normalUser.this, history.class));

            }
        });



        Button logout = (Button) findViewById(R.id.logout1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.savePassword(null, normalUser.this);
                PreferenceUtils.saveUsername(null, normalUser.this);
                PreferenceUtils.saveType(null, normalUser.this);
                PreferenceUtils.savepasscode(null, normalUser.this);
                Intent intent = new Intent(normalUser.this, loginActivity.class);
                startActivity(intent);
            }
        });

    }
    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }



    public void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }





    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();

        Location lo1 = new Location("");
        lo1.setLongitude(location.getLongitude());
        lo1.setLatitude(location.getLatitude());

        Location lo2 = new Location("");

        //Lc2
        lo2.setLongitude(100.6053858);
        lo2.setLatitude(14.0736473);


        //lo2.setLongitude(100.5964738);
        //  lo2.setLatitude(14.0692763);
        distanace = lo1.distanceTo(lo2);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onResume() {
        super.onResume();
        getLocation();
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    class MyTask extends TimerTask{

        int i=0;
        @Override
        public void run() {
            normalUser.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ref = database.getReference("connection");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue().toString().equals("1")){

                                conection.setText("Internet : door Connection Wifi ");
                                ref.setValue(0);

                            }
                            else{
                                conection.setText("Internet : door Not Connection Wifi!! ");

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }
            });
        }
    }




}
