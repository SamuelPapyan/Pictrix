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
        Bundle args = getArguments();
        if(args != null){
            AppCompatImageView image = view.findViewById(R.id.single_image);
            Glide.with(getActivity()).load(args.getString(IMAGE_SRC)).into(image);
        }
        return view;
    }
}
