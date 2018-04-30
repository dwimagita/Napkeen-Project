package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class TambahRestoran extends AppCompatActivity {

    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    EditText postAlamat, postTitle, postDaerah, postHarga, postTelepon, postInformasi, postBuka;
    ImageView postPhoto;
    Button btnChoose, btndaftar;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Uri filePath;

    FirebaseUser user;
    // dari firebase
    String userEmail;

    // yang bakal dikirim
    String username, title, alamat, telepon, informasi, daerah, buka, harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_restoran);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tambahrestoran);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_add);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseDatabase.getInstance().getReference("app_title").setValue("Napkeen");

        postTitle = findViewById(R.id.EdtTextnamarestoran);
        postAlamat = findViewById(R.id.EdtTextAlamatRestoran);
        postTelepon = findViewById(R.id.EdtTextTelponRestoran);
        postBuka = findViewById(R.id.EdtTextwaktubuka);
        postInformasi = findViewById(R.id.EdtTextinfo);
        postDaerah = findViewById(R.id.EdtTextDaerah);
        postHarga = findViewById(R.id.EdtTextharga);
        postPhoto = findViewById(R.id.postPhoto);

        btnChoose = findViewById(R.id.buttonChoose);
        btndaftar = findViewById(R.id.tomboldaftar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail();
        username = userEmail.substring(0, userEmail.indexOf("@"));

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE_REQUEST);
            }
        });

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPost();
            }
        });
    }

    private void uploadPost() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(TambahRestoran.this);
            progressDialog.setTitle("Upload Post");
            progressDialog.show();

            title = postTitle.getText().toString();
            alamat = postAlamat.getText().toString();
            daerah = postDaerah.getText().toString();
            buka = postBuka.getText().toString();
            informasi = postInformasi.getText().toString();
            telepon = postTelepon.getText().toString();
            harga = postHarga.getText().toString();


            // database
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference("posts");

            // storage
            StorageReference riversRef = storageReference.child("image").child(filePath.getLastPathSegment());
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            final Uri downloadUri = taskSnapshot.getDownloadUrl();
                            String postId = database.push().getKey();

                            Post post = new Post();
                            post.setPostId(postId);
                            post.setUsername(username);
                            post.setPhoto(downloadUri.toString());
                            post.setPhotoTitle(title);
                            post.setPhotoAlamat(alamat);
                            post.setPhotoDaerah(daerah);
                            post.setPhotoTelepon(telepon);
                            post.setPhotoInformasi(informasi);
                            post.setPhotoBuka(buka);
                            post.setPhotoharga(harga);


                            database.child(postId).setValue(post);
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),
                                    "File uploaded!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(TambahRestoran.this, Home.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setMessage("Uploading...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(),
                    "No files!", Toast.LENGTH_SHORT).show();
        }
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                postPhoto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
