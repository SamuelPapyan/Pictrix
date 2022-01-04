package com.example.pictrix.classes;

import com.example.pictrix.R;

import java.util.ArrayList;
import java.util.List;

public class VideoImage {

    private String videoImageUrl;
    private String videoUrl;

    public VideoImage(String videoImageUrl, String videoUrl) {
        this.videoImageUrl = videoImageUrl;
        this.videoUrl = videoUrl;
    }

    public VideoImage(String videoUrl){
        this.videoUrl = videoUrl;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public String getVideoImageUrl() {
        return videoImageUrl;
    }
}