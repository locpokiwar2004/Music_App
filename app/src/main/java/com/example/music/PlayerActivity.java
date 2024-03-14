package com.example.music;

import static com.example.music.MainActivity.musicFiles;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.music.model.MusicFiles;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    TextView song_name,artist_name,duration_played,duration_total;
    ImageView cover_art,nextBtn,prevBtb,backBtn,shuffleBtn,repeatBtn;
    FloatingActionButton play_pause;
    SeekBar seekBar;
    int position=-1;
    static ArrayList<MusicFiles> listSong= new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private final Handler handler=new Handler();
    private Thread playT,prevT,nextT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        intViews();
        getIntentMethod();
        try {
            song_name.setText(listSong.get(position).getTitle());
            artist_name.setText(listSong.get(position).getArtist());
        }
        catch (Exception e){
            Log.e("Error",e.toString());
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!= null){
                    int duration = mediaPlayer.getDuration() /1000;
                    seekBar.setMax(duration);
                    duration_total.setText(formattedTime(duration));
                    int currentPosition=mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(currentPosition);
                    duration_played.setText(formattedTime(currentPosition));
                }
                handler.postDelayed(this,1000);
             }
        });
    }
    private String formattedTime(int currentPosition) {
        String totalout = "";
        String totalNew = "";
        String seconds = String.valueOf(currentPosition % 60);
        String minutes = String.valueOf(currentPosition / 60);
        totalout = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1)
        {
            return totalNew;
        }
        else
        {
            return totalout;
        }
    }

    private void getIntentMethod() {

        try {
            position = getIntent().getIntExtra("position", -1);
            listSong = musicFiles;
            if(listSong != null){
                play_pause.setImageResource(R.drawable.icon_pause);
                uri= Uri.parse(listSong.get(position).getPath());
            }
            if(mediaPlayer !=null){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer =MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
            }
            else {
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
            }
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            Data(uri);
        }
        catch (Exception exception){
            Log.e("error",exception.toString());
        }
    }

    private void intViews() {
        song_name=findViewById(R.id.SongName);
        artist_name=findViewById(R.id.Artist);
        duration_played=findViewById(R.id.durationPlayed);
        duration_total=findViewById(R.id.durationTotal);
        cover_art=findViewById(R.id.img_card);
        nextBtn=findViewById(R.id.next);
        prevBtb=findViewById(R.id.prev);
        backBtn=findViewById(R.id.back_btn);
        shuffleBtn=findViewById(R.id.shuffle);
        repeatBtn=findViewById(R.id.repeat);
        play_pause=findViewById(R.id.play_pause);
        seekBar=findViewById(R.id.seek_bar);
    }

    @Override
    protected void onResume() {
        playTBtn();
        prevTBtn();
        nextTBtn();
        super.onResume();
    }

    private void nextTBtn() {
        nextT=new Thread()
        {
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextClicked();
                    }
                });
            }
        };
        nextT.start();
    }

    private void nextClicked() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position=(position+1)%listSong.size();
            uri=Uri.parse(listSong.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            try {
                Data(uri);
            }
            catch (Exception ex){
                Log.e("Error",ex.toString());
            }
            song_name.setText(listSong.get(position).getTitle());
            artist_name.setText(listSong.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!= null){
                        int currentPosition=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            play_pause.setImageResource(R.drawable.icon_pause);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position=(position+1%listSong.size());
            uri=Uri.parse(listSong.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            Data(uri);
            song_name.setText(listSong.get(position).getTitle());
            artist_name.setText(listSong.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!= null){
                        int currentPosition=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            play_pause.setImageResource(R.drawable.icon_play);
        }
    }

    private void prevTBtn() {
        prevT=new Thread()
        {
            @Override
            public void run() {
                super.run();
                prevBtb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevClicked();
                    }
                });
            }
        };
        prevT.start();
    }

    private void prevClicked() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position-1)<0?(listSong.size()-1) :(position -1)) ;
            uri=Uri.parse(listSong.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            try {
                Data(uri);
            }
            catch (Exception ex){
                Log.e("Error",ex.toString());
            }
            song_name.setText(listSong.get(position).getTitle());
            artist_name.setText(listSong.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!= null){
                        int currentPosition=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            play_pause.setImageResource(R.drawable.icon_pause);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position-1)<0?(listSong.size()-1) :(position -1)) ;
            uri=Uri.parse(listSong.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            Data(uri);
            song_name.setText(listSong.get(position).getTitle());
            artist_name.setText(listSong.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!= null){
                        int currentPosition=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            play_pause.setImageResource(R.drawable.icon_play);
        }
    }

    private void playTBtn() {
        playT=new Thread()
        {
            @Override
            public void run() {
                super.run();
                play_pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        play_pauseClicked();
                    }
                });
            }
        };
        playT.start();
    }

    private void play_pauseClicked() {
        if(mediaPlayer.isPlaying()){
            play_pause.setImageResource(R.drawable.icon_play);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!= null){
                        int currentPosition=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
        }
        else {
            play_pause.setImageResource(R.drawable.icon_pause);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!= null){
                        int currentPosition=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(currentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
        }
    }

    public void Data(Uri uri){
        MediaMetadataRetriever retriever =new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal=Integer.parseInt(listSong.get(position).getDuration())/1000;
        duration_total.setText(formattedTime(durationTotal));
        byte[]art = retriever.getEmbeddedPicture();
        if(art!=null){
            Glide.with(this).asBitmap().load(art).into(cover_art);
        }
        else {
            Glide.with(this).asBitmap().load(R.drawable.disc).into(cover_art);
        }
    }

}