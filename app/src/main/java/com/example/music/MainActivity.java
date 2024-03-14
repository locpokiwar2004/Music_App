package com.example.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.music.adapter.ViewPagerAdapter;
import com.example.music.model.Categories;
import com.example.music.model.MusicFiles;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    public static ArrayList<MusicFiles> musicFiles;
    public static ArrayList<Categories> categoriesFiles;
    public static ArrayAdapter<Categories> cateAdapter;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    public static String DATABASE_NAME="data5";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();
        prcesscopy();
    }


    // gerDataBase
    private void prcesscopy() {
        File dbFile= getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try
            {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }
    private void CopyDataBaseFromAsset() {
        try {
            InputStream input;
            input = getAssets().open(DATABASE_NAME);
            String outFile = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists())
                f.mkdir();
            OutputStream outputStream = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int length;
            while((length = input.read(buffer)) > 0){
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            input.close();

        } catch (Exception ex) {
            Log.e("Error",ex.toString());
        }
    }

    //Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                musicFiles = getAudio(this);
                MviewPager();
            }
            else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_MEDIA_AUDIO},REQUEST_CODE);}
        }
    }

    private void permission() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_MEDIA_AUDIO)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_MEDIA_AUDIO},REQUEST_CODE);
        }
        else {
            musicFiles = getAudio(this);
            MviewPager();
        }

    }
//botNav
    private void MviewPager() {
        viewPager =findViewById(R.id.viewpaper);
        bottomNavigationView = findViewById(R.id.BottomNAV);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_music).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_account).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_music) {
                    viewPager.setCurrentItem(0);
                }
                else if(item.getItemId()==R.id.menu_account) {
                    viewPager.setCurrentItem(1);
                }
                return true;
            }
        });
    }
    //Songoff
    public static ArrayList<MusicFiles> getAudio(Context context)
    {
        ArrayList<MusicFiles> tempAudioList = new ArrayList<>();
        Uri url = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };
        Cursor cursor = context.getContentResolver().query(url,projection,
                null,null,null);
        if(cursor!=null){
            while(cursor.moveToNext())
            {
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);
                MusicFiles musicFiles= new MusicFiles(path,title,artist,album,duration);
                Log.e("Path: "+ path,"Album: "+album);
                tempAudioList.add(musicFiles);
            }
            cursor.close();
        }
        return tempAudioList;
    }
    //getCate
    public ArrayList<Categories> getCate( Context context) {
        ArrayList<Categories> tempCate=new ArrayList<>();
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursorCate= database.rawQuery("select*from Theloai",null);
        while(cursorCate.moveToNext()){
            String maCate=cursorCate.getString(0);
            String tenCate=cursorCate.getString(1);
            byte[] imgCate=cursorCate.getBlob(2);
            byte[] bannerCate=cursorCate.getBlob(3);
            Categories categories= new Categories(maCate,tenCate,imgCate,bannerCate);
            tempCate.add(categories);
        }
        cursorCate.close();
        return tempCate;
    }
//Songon



}