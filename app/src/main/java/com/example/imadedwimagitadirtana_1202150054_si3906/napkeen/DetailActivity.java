package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    //inisialisasi
    TextView  detailnamarestoran, detailalamat, detailbuka,detaildaerah,detailharga, detailinformasi, detailtelepon ;
    ImageView detailPhoto;
    EditText detailComment;
    Button buttonComment;
    //recyclerview inisialisasi
    RecyclerView recyclerComment;
    //arraylist
    List<Comment> comments;

    DatabaseReference database;
    String username = "", postId = "";
    String message = "";

    FirebaseUser user;
    // dari firebase
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restoran);

        database = FirebaseDatabase.getInstance().getReference("comments");

        referencing();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail();
        username = userEmail.substring(0, userEmail.indexOf("@"));
//jika terdapat intent
        if (getIntent() != null) {
            detailnamarestoran.setText(getIntent().getStringExtra("title"));
            detailalamat.setText(getIntent().getStringExtra("alamat"));
            detailtelepon.setText(getIntent().getStringExtra("telepon"));
            detailbuka.setText(getIntent().getStringExtra("buka"));
            detailinformasi.setText(getIntent().getStringExtra("informasi"));
            detailharga.setText(getIntent().getStringExtra("harga"));
            detaildaerah.setText(getIntent().getStringExtra("daerah"));

            postId = getIntent().getStringExtra("id");

            Picasso.get().load(getIntent().getStringExtra("photo")).into(detailPhoto);
        }
//array data comment
        comments = new ArrayList<>();

        recyclerComment.setLayoutManager(new LinearLayoutManager(DetailActivity.this));

        new LoadComment().execute();

        // posting comment
        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PostComment().execute();
            }
        });
    }
// melakukan inisialisasi pada textview dan lain sebagainya dari layout

    private void referencing() {
        detailnamarestoran = findViewById(R.id.DetailNamaRestoran);
        detailalamat = findViewById(R.id.DetailAlamatRestoran);
        detailPhoto = findViewById(R.id.detailphoto);
        detailharga = findViewById(R.id.detailharga);
        detailinformasi = findViewById(R.id.Detailtambahan);
        detailbuka = findViewById(R.id.DetailWaktubuka);
        detaildaerah = findViewById(R.id.DetailDaerahRestoran);
        detailtelepon = findViewById(R.id.noTelepon);


        detailComment = findViewById(R.id.detailComment);
        buttonComment = findViewById(R.id.buttonPostComment);
        recyclerComment = findViewById(R.id.recyclerComment);
    }

    public class PostComment extends AsyncTask<Void, String, Void> {
//untuk mendapatkan data dari database yang dilakukan pada background task

        @Override
        protected Void doInBackground(Void... voids) {
            message = detailComment.getText().toString();

            String commentId = database.push().getKey();

            Comment comment = new Comment();
            comment.setPostId(postId);
            comment.setUsername(username);
            comment.setMessage(message);

            database.child(commentId).setValue(comment);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            detailComment.setText("");
            new LoadComment().execute();
        }
    }

    public class LoadComment extends AsyncTask<Void, String, Void> {

        DatabaseReference databaseReference;
        CommentAdapter adapter;
        //untuk mendapatkan data dari database yang dilakukan pada background task
        @Override
        protected Void doInBackground(Void... voids) {
            comments.clear();
            databaseReference = FirebaseDatabase.getInstance().getReference("comments");
            databaseReference.addValueEventListener(new ValueEventListener() {
                //mengubah data yang ada pada layout jika sudah berhasil mereferensi dari database
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Comment comment = snapshot.getValue(Comment.class);
                        if (comment.getPostId().equalsIgnoreCase(postId)) {
                            comments.add(comment);
                        }
                    }
//intent ke detail activity bagian comment
                    adapter = new CommentAdapter(DetailActivity.this, comments);
                    recyclerComment.setAdapter(adapter);
                }
                //jika database error
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }
}
