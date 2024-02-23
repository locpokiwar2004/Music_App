package com.example.music.tab_music;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.music.fragment_menu.AccountFragment;
import com.example.music.fragment_menu.MusicFragment;

public class MusicViewPagerAdapter extends FragmentStatePagerAdapter {
    public MusicViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SongsFragment();
            case 1:
                return new AlbumFragment();
            default:
                return new SongsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Songs";
            case 1:
                return "Album";
            default:
                return "Songs";
        }
    }
}
