package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class BarFragment extends Fragment {


    RecyclerView BarRecycler;
    BarAdapter adapter;
    List<Post> posts;

    DatabaseReference databaseReference;

    public BarFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bar_fragment, container, false);

        posts = new ArrayList<>();

        BarRecycler = view.findViewById(R.id.BarRecycler);
        BarRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    if ( post.getPhotoJenis().equals("Bar") ) {
                        posts.add(post);
                    }
                }

                adapter = new BarAdapter(getActivity(), posts);
                BarRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}