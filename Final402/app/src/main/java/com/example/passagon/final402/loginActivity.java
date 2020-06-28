package com.example.passagon.final402;

import android.content.Intent;
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

public class loginActivity extends AppCompatActivity {
    DatabaseReference db;
    String username;
    String password;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button but = (Button) findViewById(R.id.btnLogin);
        final EditText etusername = (EditText) findViewById(R.id.etLoginUsername);
        final EditText etpassword = (EditText) findViewById(R.id.etLoginPassword);
        PreferenceUtils utils = new PreferenceUtils();

        if (utils.getUsername(this) != null ){
         /*   Intent intent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(intent);*/
            if (utils.getType(this).equals("1") ){

            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(intent);
            }
            else if(utils.getType(this).equals("0"))
            {
                Intent intent = new Intent(loginActivity.this, normalUser.class);
                startActivity(intent);
            }
        }else{

        }
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 username = etusername.getText().toString();
                 password = etpassword.getText().toString();
                db = FirebaseDatabase.getInstance().getReference("User");
                db.addListenerForSingleValueEvent(valueEventListener);




            }
        });

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {


            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.getKey().equals(username)){
                    //    Toast.makeText(loginActivity.this, "Username  valid!!", Toast.LENGTH_SHORT).show();
                            if(snapshot.child("password").getValue().toString().equals(password)){
                                PreferenceUtils.saveUsername(username,loginActivity.this);
                                PreferenceUtils.savePassword(password,loginActivity.this);
                                type = snapshot.child("type").getValue().toString();
                                PreferenceUtils.saveType(type,loginActivity.this);
                                PreferenceUtils.savepasscode(snapshot.child("passcode").getValue().toString(),loginActivity.this);

                                if(type.equals("1")){
                                //Toast.makeText(loginActivity.this, "Username or Password valid!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(loginActivity.this,MainActivity.class));

                                }
                                else{

                                    startActivity(new Intent(loginActivity.this,normalUser.class));
                                }
                            }
                            else{

                                Toast.makeText(loginActivity.this, "Username or Password Invalid!!", Toast.LENGTH_SHORT).show();
                            }

                    }

                    // tmp = snapshot.getValue(user.class);
                    // artistList.add(tmp);
                    //i++;*/
                    // Toast.makeText(deleteActivity.this, "Success  "+snapshot.getKey(), Toast.LENGTH_SHORT).show();

                  /*  tmp.id=snapshot.getKey();
                    tmp.name=(snapshot.child("name").getValue().toString());

                    tmp.surname=(snapshot.child("surname").getValue().toString());
                    tmp.date=(snapshot.child("date").getValue().toString());
                    tmp.note = (snapshot.child("note").getValue().toString());
                    tmp.type = (snapshot.child("type").getValue().toString());
                    tmp.picture = (snapshot.child("picture").getValue().toString());
                    // Toast.makeText(deleteActivity.this, "Success  "+snapshot.child("name").getValue(), Toast.LENGTH_SHORT).show();
                    artistList.add(tmp);*/

                }

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
