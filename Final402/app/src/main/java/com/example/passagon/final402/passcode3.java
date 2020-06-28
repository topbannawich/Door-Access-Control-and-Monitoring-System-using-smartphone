package com.example.passagon.final402;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class passcode3 extends AppCompatActivity  {
    String passcode;
    Pinview p;
    String ans;
    Context tmp;

    public DatabaseReference ref;
    public FirebaseDatabase database;
    TextView lo, dis;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode3);





        database = FirebaseDatabase.getInstance();
        ref = database.getReference("OTP");
        notificationManager = NotificationManagerCompat.from(this);

        p= (Pinview) findViewById(R.id.pinview3);
        p.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {


                ans = pinview.getValue();
                if (ans.equals(PreferenceUtils.getPasscode(passcode3.this))){


                 //   Toast.makeText(passcode3.this, " Invalid :", Toast.LENGTH_SHORT).show();
                   // String title = editTextTitle.getText().toString();
                  //  String message = editTextMessage.getText().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(passcode3.this);
                    String otp=OTP(6);
                    ref.setValue(otp);
                    builder.setTitle("OTP");
                    builder.setMessage("your OTP is :"+otp);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            dialog.dismiss();
                            finish();


                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }
                else
                {

                    Toast.makeText(passcode3.this, "Passcode Invalid", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = getIntent();
                    startActivity(intent);
                }
            }
        });
    }

    static String OTP(int len)
    {

        String ot = "";
        // Using numeric values
        String numbers = "0123456789";

        // Using random method
        Random rndm_method = new Random();

        char[] otp = new char[len];

        for (int i = 0; i < len; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            ot= ot+ numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return ot;
    }



}