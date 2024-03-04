package com.example.music;

import static com.example.music.MainActivity.musicFiles;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.music.tab_music.MusicFiles;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        intViews();
        getIntentMethod();

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
        }
        catch (Exception exception){
            Log.e("error",exception.toString());
        }
    }

    private void intViews() {
        song_name=findViewById(R.id.music_name);
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
    public void Data(Uri uri){
        MediaMetadataRetriever retriever =new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal=Integer.parseInt(listSong.get(position).getDuration());
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