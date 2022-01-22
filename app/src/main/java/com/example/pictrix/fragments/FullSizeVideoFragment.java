package com.example.pictrix.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.pictrix.MainActivity;
import com.example.pictrix.R;
import com.example.pictrix.databinding.FullsizeVideoLayoutBinding;

import java.io.FileNotFoundException;

public class FullSizeVideoFragment extends Fragment {

    private final String VIDEO_URL = "videoUrl";

    private FullsizeVideoLayoutBinding viewBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.hide();
        viewBinding = FullsizeVideoLayoutBinding.inflate(inflater,container,false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadVideo();
    }

    private void loadVideo(){
        Bundle args = getArguments();
        if (args != null) {
            try {
                String videoUrl = args.getString(VIDEO_URL);
                Uri uri = Uri.parse(videoUrl);
                viewBinding.videoView.setVideoURI(uri);
                viewBinding.videoView.setVideoPath(videoUrl);
                MediaController mediaController = new MediaController(viewBinding.videoView.getContext());
                mediaController.setAnchorView(viewBinding.videoView);
                mediaController.setMediaPlayer(viewBinding.videoView);
                viewBinding.videoView.setMediaController(mediaController);
                viewBinding.videoView.requestFocus();
                viewBinding.videoView.start();
                mediaController.show();
            }catch (Exception e){
                System.out.println("------------------File exception-----------------");
                e.printStackTrace();
            }
        }
    }
}
