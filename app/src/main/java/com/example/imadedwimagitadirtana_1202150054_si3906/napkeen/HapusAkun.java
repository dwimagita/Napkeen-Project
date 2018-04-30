package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HapusAkun extends AppCompatActivity {

    private Button btnHapusAkun, btnkembalipengaturan;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_akun);
        btnkembalipengaturan = (Button) findViewById(R.id.btn_kembali_hapus_akun);
        btnHapusAkun = (Button) findViewById(R.id.btn_hapus_akun);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(HapusAkun.this, LoginActivity.class));
                    finish();
                }
            }
        };

        //untuk kembali ke pengaturan
        btnkembalipengaturan.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void
                                                    onClick(View view) {
                                                        //memindahkan ke aktivitas pengaturan
                                                        Intent kembali = new Intent( HapusAkun.this , Pengaturan.class);
                                                        startActivity(kembali);
                                                    }
                                                });
                //untuk menghapus akun
                btnHapusAkun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);

                        //jika terdapat user login maka user akan dihapus
                                        if (user != null) {
                                            user.delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(HapusAkun.this, "Akun anda sudah terhapus: Buat akun baru sekarang!", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(HapusAkun.this, SignupActivity.class));
                                                                finish();
                                                                progressBar.setVisibility(View.GONE);
                                                            } else {
                                                                Toast.makeText(HapusAkun.this, "Gagal Menghapus Akun anda!", Toast.LENGTH_SHORT).show();
                                                                progressBar.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }




    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
