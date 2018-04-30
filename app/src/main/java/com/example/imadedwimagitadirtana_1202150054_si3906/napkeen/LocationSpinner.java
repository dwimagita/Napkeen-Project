package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class LocationSpinner extends AppCompatActivity {

    private Spinner spinnerLocation;
    private String[] lokasinya = {
            "Sukapura",
            "Sukabirus",
            "Mengger Hilir",
            "Ciganitri",
            "Lengkong",
            "Babakan Radio",
            "PGA",
            "Cikoneng",
            "Cijagra",
            "Radio Palasari"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_spinner);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_spinner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_daerah);

        spinnerLocation = (Spinner) findViewById(R.id.sp_name_2);

        // inisialiasi Array Adapter dengan memasukkan string array di atas
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, lokasinya);

        // mengeset Array Adapter tersebut ke Spinner
        spinnerLocation.setAdapter(adapter);

        // mengeset listener untuk mengetahui saat item dipilih
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)
                Toast.makeText(LocationSpinner.this, "Selected "+ adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}
