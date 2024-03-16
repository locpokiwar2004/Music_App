package com.example.music.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.activity.PlayerActivity;
import com.example.music.R;
import com.example.music.model.MusicFiles;

import java.io.IOException;
import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    private Context mcontext;
    private ArrayList<MusicFiles> mFiles;
    MusicAdapter (Context mcontext,ArrayList<MusicFiles>mFiles){
        this.mcontext=mcontext;
        this.mFiles=mFiles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.music_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.music_name.setText(mFiles.get(position).getTitle());

        try{
            byte[] img=getIMGSong(mFiles.get(position).getPath());
            if(img !=null)
                Glide.with(mcontext).asBitmap().load(img).into(holder.album_img);
            else
                Glide.with(mcontext).load(R.drawable.disc).into(holder.album_img);
        }
        catch (Exception e){
            Log.e("error",e.toString());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mcontext, PlayerActivity.class);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView music_name;
        ImageView album_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            music_name=itemView.findViewById(R.id.music_name);
            album_img=itemView.findViewById(R.id.music_img);
        }

    }
    private byte[] getIMGSong(String uri){
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art=retriever.getEmbeddedPicture();
        try {
            retriever.release();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return art;
    }
}
