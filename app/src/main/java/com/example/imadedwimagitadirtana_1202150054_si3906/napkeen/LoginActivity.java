package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            user = (EditText) findViewById(R.id.username);
            pass = (EditText) findViewById(R.id.password);

        }

    public void Login(View view) {
        String username = user.getText().toString();
        String password = pass.getText().toString();

        if (username.equals("admin") && password.equals("admin")){
            Toast.makeText(getApplicationContext(),"LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"LOGIN FAILED", Toast.LENGTH_SHORT).show();
        }
    }
}

