package com.example.music.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.R;
import com.example.music.activity.DetailcateActivity;
import com.example.music.model.Categories;

import java.util.ArrayList;

public class moreAdapter extends RecyclerView.Adapter<moreAdapter.moreViewholder>{
    Context context;
    private ArrayList<Categories> cate;

    public moreAdapter(Context context, ArrayList<Categories> cate) {
        this.context = context;
        this.cate = cate;
    }
    @NonNull
    @Override
    public moreViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_recyclerview,parent,false);
        return new moreAdapter.moreViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull moreViewholder holder, int position) {
        holder.txt_cate.setText(cate.get(position).getTenCate());
        Glide.with(context).load(cate.get(position).getImgCate()).into(holder.img_cate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, DetailcateActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cate.size();
    }

    public class moreViewholder extends RecyclerView.ViewHolder {
        ImageView img_cate;
        TextView txt_cate;
        public moreViewholder(@NonNull View itemView) {
            super(itemView);
            img_cate=itemView.findViewById(R.id.img_categories);
            txt_cate=itemView.findViewById(R.id.name_categories);
        }
    }
}
