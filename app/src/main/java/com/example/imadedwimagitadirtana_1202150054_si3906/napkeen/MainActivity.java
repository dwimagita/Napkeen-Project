package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void launchLoginActivity(View view) {
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
            }
    public void launchSignupActivity(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


}
