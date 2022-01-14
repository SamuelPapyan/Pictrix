package com.example.pictrix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pictrix.MainActivity;
import com.example.pictrix.R;

public class FullImageFragment extends Fragment {

    private final String IMAGE_SRC = "imageSrc";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.hide();
        View view = inflater.inflate(R.layout.full_image_layout,container,false);
        if(getArguments() != null){
            AppCompatImageView image = view.findViewById(R.id.single_image);
            String imageUrl;
            if(FullImageFragmentArgs.fromBundle(getArguments()).getImageUrl() != "null")
                imageUrl = FullImageFragmentArgs.fromBundle(getArguments()).getImageUrl();
            else
                imageUrl = getArguments().getString(IMAGE_SRC);
            Glide.with(getActivity())
                    .load(imageUrl)
                    .into(image);
        }
        return view;
    }
}
