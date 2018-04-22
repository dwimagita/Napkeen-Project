package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class TempatMakanTerdekat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_makan_terdekat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tempat_makan_terdekat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_location);
    }
}
