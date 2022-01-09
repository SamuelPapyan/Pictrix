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

import java.io.FileNotFoundException;

public class FullSizeVideoFragment extends Fragment {

    private VideoView videoView;
    private final String VIDEO_URL = "videoUrl";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fullsize_video_layout,container,false);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.hide();
        videoView = view.findViewById(R.id.video_view);
        loadVideo();
        return view;
    }

    private void loadVideo(){
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            try {
                String videoUrl = bundle.getString(VIDEO_URL);
                Uri uri = Uri.parse(videoUrl);
                videoView.setVideoURI(uri);
                videoView.setVideoPath(videoUrl);
                MediaController mediaController = new MediaController(videoView.getContext());
                mediaController.setAnchorView(videoView);
                mediaController.setMediaPlayer(videoView);
                videoView.setMediaController(mediaController);
                videoView.requestFocus();
                videoView.start();
                mediaController.show();
            }catch (Exception e){
                System.out.println("------------------File exception-----------------");
                e.printStackTrace();
            }
        }
    }
}
