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
import androidx.fragment.app.Fragment;

import com.example.pictrix.R;

public class FullSizeVideoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fullsize_video_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args != null){
            VideoView videoView = view.findViewById(R.id.video_view);
            String videoUrl = args.getString("videoUrl");
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
        }
    }
}
