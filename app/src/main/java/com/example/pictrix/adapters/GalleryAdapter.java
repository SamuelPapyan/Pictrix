package com.example.pictrix.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pictrix.fragments.ProfileFragment;
import com.example.pictrix.fragments.ProfileGalleryFragment;
import com.example.pictrix.fragments.ProfileVideosFragment;

public class GalleryAdapter extends FragmentStateAdapter {

    public GalleryAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new ProfileGalleryFragment();
            case 1:
                return new ProfileVideosFragment();
        }
        return new ProfileGalleryFragment();
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}