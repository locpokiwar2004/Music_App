package com.example.music.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.music.fragment_menu.AccountFragment;
import com.example.music.fragment_menu.MusicFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MusicFragment();
            case 1:
                return new AccountFragment();
            default:
                return new MusicFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
