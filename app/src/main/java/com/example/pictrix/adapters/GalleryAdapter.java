package com.example.pictrix.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pictrix.fragments.ProfileFragment;
import com.example.pictrix.fragments.ProfileGalleryFragment;

public class GalleryAdapter extends FragmentStateAdapter {
    private String profileQualifier;
    public GalleryAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                ProfileGalleryFragment fragment1 = new ProfileGalleryFragment();
                fragment1.setProfileQualifier(profileQualifier);
                return fragment1;
            case 1:
                ProfileGalleryFragment fragment2 = new ProfileGalleryFragment();
                fragment2.setProfileQualifier(profileQualifier);
                return fragment2;
        }
        return new ProfileGalleryFragment();
    }

    public void setProfileQualifier(String profileQualifier){
        this.profileQualifier = profileQualifier;
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}