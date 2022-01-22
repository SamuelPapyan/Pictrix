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
import com.example.pictrix.databinding.ProfileLayoutBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {
    private GalleryAdapter galleryAdapter;

    private ProfileLayoutBinding viewBinding = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = ProfileLayoutBinding.inflate(inflater,container,false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show();
        if(getActivity() != null){
            getActivity().findViewById(R.id.back_button).setVisibility(View.VISIBLE);
        }
        galleryAdapter = new GalleryAdapter(this);
        if(getArguments() != null){
            AppCompatTextView profileName = view.findViewById(R.id.name_surname);
            profileName.setText(ProfileFragmentArgs.fromBundle(getArguments()).getProfileName());
        }
        viewBinding.tabLayout.addTab(viewBinding.tabLayout.newTab().setIcon(R.drawable.images_icon));
        viewBinding.tabLayout.addTab(viewBinding.tabLayout.newTab().setIcon(R.drawable.videos_icon));
        viewBinding.pager.setAdapter(galleryAdapter);
        viewBinding.pager.setOffscreenPageLimit(2);
        viewBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewBinding.pager.setCurrentItem(tab.getPosition());
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
                .into(viewBinding.profileImage);
        new TabLayoutMediator(viewBinding.tabLayout, viewBinding.pager,(tab, position)->{
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
