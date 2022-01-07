package com.example.pictrix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.pictrix.MainActivity;
import com.example.pictrix.R;
import com.example.pictrix.adapters.GalleryAdapter;
import com.example.pictrix.adapters.ProfileImageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {
    GalleryAdapter galleryAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_layout,container,  false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show();
        AppCompatImageView profileImage = view.findViewById(R.id.profileImage);
        galleryAdapter = new GalleryAdapter(this);
        Bundle args = getArguments();
        if(args != null){
            galleryAdapter.setProfileQualifier(args.getString("profile"));
            AppCompatTextView profileName = view.findViewById(R.id.name_surname);
            profileName.setText(args.getString("profile"));
        }
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.images_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.videos_icon));
        viewPager2 = view.findViewById(R.id.pager);
        viewPager2.setAdapter(galleryAdapter);
        viewPager2.setOffscreenPageLimit(2);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Glide.with(this)
                .load("https://img.freepik.com/free-photo/this-is-beautiful-landscape-emerald-lake-canada-s-youhe-national-park_361746-26.jpg?size=626&ext=jpg")
                .centerCrop()
                .circleCrop()
                .into(profileImage);
        new TabLayoutMediator(tabLayout, viewPager2,(tab, position)->{
            switch(position){
                case 0:
                    tab.setIcon(R.drawable.images_icon);
                    break;
                case 1:
                    tab.setIcon(R.drawable.videos_icon);
                    break;
            }
        }).attach();
        AppCompatTextView appBarTitle = getActivity().findViewById(R.id.appBarHeading);
        appBarTitle.setText("Profile");
    }
}
