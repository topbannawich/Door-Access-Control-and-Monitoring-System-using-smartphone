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


public class MainActivity extends AppCompatActivity implements LocationListener
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
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.imageView);

        timer = new Timer();
        timer.schedule(new MyTask(), 0, 600000);

        timer2 = new Timer();
        timer2.schedule(new MyTask2(), 0, 1000);


        conection = (TextView) findViewById(R.id.interntCon);
        doorText = (TextView) findViewById(R.id.door);
        battery = (TextView) findViewById(R.id.battery);

        //battery.setText();

        /*if (Build.VERSION.SDK_INT >= 23) {
            Log.d("mylog", "Getting Location Permission");
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("mylog", "Not granted");
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
            }
            //requestLocation();
        } else {
        }*/
        // requestLocation();

        CheckPermission();

        /*int length = 6;


        Toast.makeText(MainActivity.this, " Invalid :"+OTP(length), Toast.LENGTH_SHORT).show();*/


        database = FirebaseDatabase.getInstance();
        ref = database.getReference("status");


        Button button = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();

                Toast.makeText(MainActivity.this, " Invalid :"+distanace, Toast.LENGTH_SHORT).show();
                if (distanace <= 200.0) {
                    Intent intent = new Intent(MainActivity.this, passcode2.class);
                    startActivity(intent);


                } else if (distanace > 200) {


                    Intent intent = new Intent(MainActivity.this, passcode3.class);
                    startActivity(intent);


                }


                //startActivity(new Intent(MainActivity.this,addActivity.class));
            }
        });
        Button button4 = (Button) findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, history.class));

            }
        });

        Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, registerActivity.class));
                startActivity(new Intent(MainActivity.this, passcodeActivity.class));
            }
        });
        Button button44 = (Button) findViewById(R.id.button);

        button44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, deleteActivity.class));
                startActivity(new Intent(MainActivity.this, passcodeActivity.class));
            }
        });

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.savePassword(null, MainActivity.this);
                PreferenceUtils.saveUsername(null, MainActivity.this);
                PreferenceUtils.saveType(null, MainActivity.this);
                PreferenceUtils.savepasscode(null, MainActivity.this);
                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
     /*   Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });*/

        /*Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tsLong = System.currentTimeMillis()/1000;
                ts = tsLong.toString();
                Glide.with(MainActivity.this)
                        .load("http://project402.ddns.net:8000/cgi-bin/snapshot.cgi?chn=1/"+ts+"mono78.jpg")
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(500, 500) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                                StorageReference mountainImagesRef = storageRef.child("image/"+ts+".jpg");
                                BitmapDrawable bm = new BitmapDrawable(getResources(), resource);
                                Bitmap bitmap = bm.getBitmap();

                                image.setImageBitmap(bitmap);

                                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();
                                //mountainImagesRef.putBytes(data);

                                UploadTask  uploadTask = mountainImagesRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                        // ...
                                    }
                                });

                            }
                        });
            }
        });*/


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


    public void checksnap() {

        snap = database.getReference("snapshot");
        snap.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("1")) {
                    snap.setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveImage() {
        tsLong = System.currentTimeMillis() / 1000;
        ts = tsLong.toString();
        Glide.with(MainActivity.this)
                .load("http://project402.ddns.net:8000/cgi-bin/snapshot.cgi?chn=1/"+ts+".jpg")
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(500, 500) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        StorageReference mountainImagesRef = storageRef.child("image/" + ts + ".jpg");
                        BitmapDrawable bm = new BitmapDrawable(getResources(), resource);
                        Bitmap bitmap = bm.getBitmap();

                       // image.setImageBitmap(bitmap);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();
                        //mountainImagesRef.putBytes(data);

                        UploadTask uploadTask = mountainImagesRef.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                // ...
                            }
                        });

                    }
                });
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


               // lo2.setLongitude(100.5964738);
            //    lo2.setLatitude(14.0692763);
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



   /* private void requestLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        //String provider = locationManager.getBestProvider(criteria, true);
        //Location location = locationManager.getLastKnownLocation(provider);
        Log.d("mylog", "In Requesting Location");
        if (location != null && (System.currentTimeMillis() - location.getTime()) <= 1000 * 2) {
            LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
            String cityName = getCityName(myCoordinates);
            Toast.makeText(this, cityName, Toast.LENGTH_SHORT).show();
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setNumUpdates(1);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            Log.d("mylog", "Last location too old getting new location!");
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.requestLocationUpdates(locationRequest,
                    mLocationCallback, Looper.myLooper());
        }
    }*/

    /*@Override
    public void onLocationChanged(Location location) {
        Location lo1 = new Location("");
        lo1.setLatitude(location.getLatitude());
        lo1.setLongitude(location.getLongitude());

        Location lo2 = new Location("");
        lo2.setLatitude(14.0690935);
        lo2.setLongitude(100.5969919);


        doorText.setText("longitude : " +location.getLongitude()+"\n" + "latitude : "+location.getLatitude()+"\n dis = "+lo1.distanceTo(lo2));






    }*/



    class MyTask extends TimerTask{

            int i=0;
         @Override
         public void run() {
             MainActivity.this.runOnUiThread(new Runnable() {
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

            class MyTask2 extends TimerTask{


                @Override
                public void run() {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            snap= database.getReference("snapshot");
                            snap.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(Integer.valueOf(dataSnapshot.getValue().toString())==1){
                                        snap.setValue(0);
                                        saveImage();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                                        type=database.getReference("test");
                                        type.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(Integer.valueOf(dataSnapshot.getValue().toString())==587){
                                                    type.setValue(0);
                                                    saveImage();
                                                }
                                                else if(Integer.valueOf(dataSnapshot.getValue().toString())==989){
                                                    type.setValue(0);
                                                    saveImage();
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
