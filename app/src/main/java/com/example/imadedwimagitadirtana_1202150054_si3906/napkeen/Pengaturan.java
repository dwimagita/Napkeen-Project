package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Pengaturan extends AppCompatActivity {


    private CardView ubahpassword, ubahemail, hapusakun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pengaturan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_settings);

        ubahpassword = (CardView) findViewById(R.id.ubahpasswordcardview);
        ubahemail = (CardView) findViewById(R.id.ubahemailcardview);
        hapusakun = (CardView) findViewById(R.id.hapusakuncarview);

        ubahpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ubahpwd = new Intent(Pengaturan.this, UbahPassword.class);
                startActivity(ubahpwd);
            }
        });
        ubahemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ubahpwd = new Intent(Pengaturan.this, UbahEmail.class);
                startActivity(ubahpwd);
            }
        });
        hapusakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ubahpwd = new Intent(Pengaturan.this, HapusAkun.class);
                startActivity(ubahpwd);
            }

        });
    }
}
