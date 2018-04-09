package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RestoranAdapter extends RecyclerView.Adapter<RestoranAdapter.ViewHolder> {
    Context context;
    List<Post> posts;

    public RestoranAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restoran_fragment_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.RestoranPhotoName.setText(post.getPhotoTitle());
        holder.RestoranPhotoLoc.setText(post.getPhotoDaerah());

        Picasso.get().load(post.getPhoto()).into(holder.RestoranPhoto);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  RestoranPhotoLoc, RestoranPhotoName;
        ImageView RestoranPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            //inisialisasi
            RestoranPhotoName = itemView.findViewById(R.id.RestoranPhotoTitle);
            RestoranPhotoLoc = itemView.findViewById(R.id.RestoranPhotoLoc);
            RestoranPhoto = itemView.findViewById(R.id.RestoranPhoto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Post post = posts.get(position);
            String postId = post.getPostId();
            String title = post.getPhotoTitle();
            String alamat = post.getPhotoAlamat();
            String telepon = post.getPhotoTelepon();
            String harga = post.getPhotoharga();
            String informasi = post.getPhotoInformasi();
            String daerah = post.getPhotoDaerah();
            String buka = post.getPhotoBuka();


            String photoUrl = post.getPhoto();
            String userpost = post.getUsername();
            //untuk memberikan intent ke aktivitas lain

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("userpost",userpost);
            intent.putExtra("title", title);
            intent.putExtra("alamat", alamat);
            intent.putExtra("harga", harga);
            intent.putExtra("informasi", informasi);
            intent.putExtra("telepon", telepon);
            intent.putExtra("daerah", daerah);
            intent.putExtra("buka", buka);


            intent.putExtra("photo", photoUrl);
            intent.putExtra("id", postId);
            context.startActivity(intent);
        }
    }
}
