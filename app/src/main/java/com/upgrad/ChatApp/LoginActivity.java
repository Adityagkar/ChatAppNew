package com.upgrad.ChatApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,name;
    private Button login;
    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        login = findViewById(R.id.login);
        name = findViewById(R.id.nameuser);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(email);
        ccp.setNumberAutoFormattingEnabled(false);
        ccp.isValidFullNumber();

//        Typeface typeFace=Typeface.createFromAsset(getApplicationContext().getAssets(),"acme.ttf");
//        ccp.setTypeFace(typeFace);

     

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = email.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    email.setError("Enter a valid mobile");
                    email.requestFocus();
                    return;
                }
                mobile = ccp.getFullNumberWithPlus();



                Log.d("TESTING","Full number is"+ccp.getFullNumberWithPlus());
                MessageAdapter.setMyUsername(name.getText().toString());

                Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);

            }
        });

    }


}
