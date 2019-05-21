package com.upgrad.ChatApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                SharedPreferences sharedPreferences =  getSharedPreferences(String.valueOf(R.string.appname), Context.MODE_PRIVATE);
                String fetchedPhone =   sharedPreferences.getString("PHONE","1234567890");
                if(!fetchedPhone.equals("1234567890")){
                    //old user
                    String fetchedName = sharedPreferences.getString("NAME","error");
                    Intent intent = new Intent(SplashActivity.this, DisplaySociety.class);
                    if(!fetchedName.equals("error")) {
                        FriendlyMessage friendlyMessage = new FriendlyMessage();
                        friendlyMessage.setName(fetchedName);
                        MessageAdapter.setMyUsername(fetchedName);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                }else {
                   // new user
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }, SPLASH_TIME_OUT);
    }
}

