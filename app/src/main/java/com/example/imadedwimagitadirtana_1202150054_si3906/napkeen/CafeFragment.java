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
public class CafeFragment extends Fragment {



    RecyclerView CafeRecycler;
    CafeAdapter adapter;
    List<Post> posts;

    DatabaseReference databaseReference;

    public CafeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cafe_fragment, container, false);

        posts = new ArrayList<>();

        CafeRecycler = view.findViewById(R.id.CafeRecycler);
        CafeRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    if ( post.getPhotoJenis().equals("Cafe") ) {
                        posts.add(post);
                    }
                }

                adapter = new CafeAdapter(getActivity(), posts);
                CafeRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
