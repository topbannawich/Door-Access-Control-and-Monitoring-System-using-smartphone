package com.example.passagon.final402;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registerActivity extends AppCompatActivity {
    public DatabaseReference ref;
    public DatabaseReference rvp;
    public DatabaseReference tmpRigister;
    public FirebaseDatabase database;
    user user;
    EditText name,surname,username,password,passcode1, confirmpassword,confirmpasscode ;
    CountDownTimer cdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button button = (Button) findViewById(R.id.btnRegister);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User");
         name = (EditText)  findViewById(R.id.eName);
         surname = (EditText)  findViewById(R.id.esurName);
          username = (EditText)  findViewById(R.id.etUsername);
         password = (EditText)  findViewById(R.id.etPassword);
         passcode1 = (EditText)  findViewById(R.id.ePasscode);

         confirmpassword = (EditText)  findViewById(R.id.etConfirmPassword);
        confirmpasscode= (EditText)  findViewById(R.id.eConfirmPasscode);
                user = new user();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



              rvp =  database.getReference("register");
                tmpRigister= database.getReference("tmpRegister");
                tmpRigister.setValue(username.getText().toString());

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           // Toast.makeText(registerActivity.this, "finish = "+  dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

if(password.getText().toString().equals(confirmpassword.getText().toString())&&passcode1.getText().toString().equals(confirmpasscode.getText().toString())) {


    AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);

    builder.setTitle("Register");
    builder.setMessage("Register Success");


    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            getValue();
            ref.child(username.getText().toString()).setValue(user);

            dialog.dismiss();
            finish();
        }
    });

    AlertDialog alert = builder.create();
    alert.show();

}else  if(password.getText().toString().equals(confirmpassword.getText().toString())==false)
{
    AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);

    builder.setTitle("Error");
    builder.setMessage("Password and comfirm Password Not equals please Try Again");


    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {


            dialog.dismiss();

        }
    });

    AlertDialog alert = builder.create();
    alert.show();

}else if(passcode1.getText().toString().equals(confirmpasscode.getText().toString())==false){
    AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);

    builder.setTitle("Error");
    builder.setMessage("Passcode and comfirm Passcode Not equals please Try Again");


    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {


            dialog.dismiss();

        }
    });

    AlertDialog alert = builder.create();
    alert.show();

}



               // rvp.setValue(1);
               // MyCountDown countdown = new MyCountDown(120000,1000);
               // countdown.start();

            /*  cdt= new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                      //  mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);

                        //Toast.makeText(registerActivity.this, "seconds remaining: "+ millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                        if(Integer.valueOf(regis.getKey())==0)
                        {

                            getValue();

                            ref.child(username.getText().toString()).setValue(user);

                           // Toast.makeText(registerActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            finish();
                        }

                    }

                    public void onFinish() {
                       // Toast.makeText(registerActivity.this, "การสมัครสมาชิกล้มเหลวกรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }.start();*/




            }
        });
    }
    private void getValue(){
        user.setName(name.getText().toString());
        user.setSurname(surname.getText().toString());
        //user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setPasscode(passcode1.getText().toString());
        user.setType("0");

    }
    public class MyCountDown extends CountDownTimer {

        public MyCountDown(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);

// TODO Auto-generated constructor stub
        }
        @Override
        public void onFinish() {

// TODO Auto-generated method stub
          //  Toast.makeText(registerActivity.this, "finish", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onTick(long remain) {
            rvp =  database.getReference("register");
            //Toast.makeText(registerActivity.this, " seconds remaining: " +remain/1000, Toast.LENGTH_SHORT).show();



          rvp.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  if(dataSnapshot.getValue().toString().equals("0"))
                  {
                     // Toast.makeText(registerActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                      getValue();
                      ref.child(username.getText().toString()).setValue(user);

                      cancel();
                      finish();


                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });
        }

    }

}
