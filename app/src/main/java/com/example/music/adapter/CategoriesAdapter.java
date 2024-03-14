package com.example.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.R;
import com.example.music.model.Categories;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.cateViewHolder> {
    private Context Ccontext;
    private ArrayList<Categories> cate;
    public CategoriesAdapter(Context context, ArrayList<Categories> cate){
        this.Ccontext=context;
        this.cate=cate;
    }
    @NonNull
    @Override
    public cateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Ccontext).inflate(R.layout.categories_recyclerview,parent,false);
        return new CategoriesAdapter.cateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cateViewHolder holder, int position) {
        holder.txt_cate.setText(cate.get(position).getTenCate());
        Glide.with(Ccontext).load(cate.get(position).getImgCate()).into(holder.img_cate);
    }

    @Override
    public int getItemCount() {
        return cate.size();
    }

    public class cateViewHolder extends RecyclerView.ViewHolder {
        ImageView img_cate;
        TextView txt_cate;
        public cateViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cate=itemView.findViewById(R.id.img_categories);
            txt_cate=itemView.findViewById(R.id.name_categories);
        }
    }
}
