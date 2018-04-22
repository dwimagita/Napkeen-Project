package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
<<<<<<< HEAD
=======
import android.support.v4.widget.SwipeRefreshLayout;
>>>>>>> a0af9f15a85f738276b9bccae28c3a3ca4cc959c
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestoranFragment extends Fragment {

<<<<<<< HEAD
=======
    private ProgressBar progressBar;
>>>>>>> a0af9f15a85f738276b9bccae28c3a3ca4cc959c

    RecyclerView RestoranRecycler;
    RestoranAdapter adapter;
    List<Post> posts;
<<<<<<< HEAD

    DatabaseReference databaseReference;

=======
    DatabaseReference databaseReference;

>>>>>>> a0af9f15a85f738276b9bccae28c3a3ca4cc959c
    public RestoranFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.restoran_fragment, container, false);

<<<<<<< HEAD
        posts = new ArrayList<>();
=======


        posts = new ArrayList<>();

        RestoranRecycler = view.findViewById(R.id.RestoranRecycler);
        RestoranRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    posts.add(post);
                }

                adapter = new RestoranAdapter(getActivity(), posts);
                RestoranRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
>>>>>>> a0af9f15a85f738276b9bccae28c3a3ca4cc959c

        RestoranRecycler = view.findViewById(R.id.RestoranRecycler);
        RestoranRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    posts.add(post);
                }

                adapter = new RestoranAdapter(getActivity(), posts);
                RestoranRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}


