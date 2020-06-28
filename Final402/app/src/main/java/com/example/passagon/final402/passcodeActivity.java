package com.example.passagon.final402;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.goodiebag.pinview.Pinview;
public class passcodeActivity extends AppCompatActivity {
    String passcode;
    Pinview p;
    String ans;
    Context tmp;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

         p= (Pinview) findViewById(R.id.pinview);
       p.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {


               ans = pinview.getValue();
                if (ans.equals(PreferenceUtils.getPasscode(passcodeActivity.this))){

                    finish();

                }
                else
                {


                        Toast.makeText(passcodeActivity.this, "Passcode Invalid", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = getIntent();
                        startActivity(intent);


                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;


    }

    public boolean checkPasscode(String ans)
    {
        if(passcode.equals(ans))
        {
            return true;
        }

        return false;
    }


}
