package com.example.pictrix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pictrix.MainActivity;
import com.example.pictrix.R;
import com.example.pictrix.databinding.FullImageLayoutBinding;

public class FullImageFragment extends Fragment {

    private final String IMAGE_SRC = "imageSrc";

    private FullImageLayoutBinding viewBinding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.hide();
        viewBinding = FullImageLayoutBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){
            String imageUrl;
            if(FullImageFragmentArgs.fromBundle(getArguments()).getImageUrl() != "null")
                imageUrl = FullImageFragmentArgs.fromBundle(getArguments()).getImageUrl();
            else
                imageUrl = getArguments().getString(IMAGE_SRC);
            Glide.with(getActivity())
                    .load(imageUrl)
                    .into(viewBinding.singleImage);
        }
    }
}
