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
    public static List<VideoImage> getVideos(){
        ArrayList<VideoImage> videoImages = new ArrayList<>();
        videoImages.add(new VideoImage("https://static-videos.pexels.com/videos/1093662/pictures/preview-0.jpg","https://pin.it/5DM9lrv" ));
        videoImages.add(new VideoImage("https://static-videos.pexels.com/videos/1093662/pictures/preview-0.jpg","https://pin.it/5DM9lrv" ));
        videoImages.add(new VideoImage("https://static-videos.pexels.com/videos/1093662/pictures/preview-0.jpg","https://pin.it/5DM9lrv" ));
        videoImages.add(new VideoImage("https://static-videos.pexels.com/videos/1093662/pictures/preview-0.jpg","https://pin.it/5DM9lrv" ));
        videoImages.add(new VideoImage("https://static-videos.pexels.com/videos/1093662/pictures/preview-0.jpg","https://pin.it/5DM9lrv" ));
        videoImages.add(new VideoImage("https://static-videos.pexels.com/videos/1093662/pictures/preview-0.jpg","https://pin.it/5DM9lrv" ));
        videoImages.add(new VideoImage("https://static-videos.pexels.com/videos/1093662/pictures/preview-0.jpg","https://pin.it/5DM9lrv" ));
        videoImages.add(new VideoImage("https://static-videos.pexels.com/videos/1093662/pictures/preview-0.jpg","https://pin.it/5DM9lrv" ));
        videoImages.add(new VideoImage("https://static-videos.pexels.com/videos/1093662/pictures/preview-0.jpg","https://pin.it/5DM9lrv" ));
        return videoImages;
    }
}