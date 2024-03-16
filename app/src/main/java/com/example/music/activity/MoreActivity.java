package com.example.music.activity;

import static com.example.music.activity.MainActivity.categoriesFiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.music.R;
import com.example.music.adapter.moreAdapter;

public class MoreActivity extends AppCompatActivity {
    ImageView btnBack;
    private RecyclerView mRecyclerView;
    private moreAdapter more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        mRecyclerView=findViewById(R.id.moreAlbum);
        btnBack=findViewById(R.id.btnBack);
        if(!(categoriesFiles.size()<1)){
            more= new moreAdapter(this,categoriesFiles);
            mRecyclerView.setAdapter(more);

            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(layoutManager);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}