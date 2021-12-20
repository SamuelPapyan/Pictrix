package com.example.pictrix.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Video {
    @SerializedName("id")
    int id;

    @SerializedName("width")
    int width;

    @SerializedName("height")
    int height;

    @SerializedName("url")
    String url;

    @SerializedName("image")
    String image;

    @SerializedName("duration")
    int duration;

    @SerializedName("src")
    Src src;

    public Src getSrc() {
        return src;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public int getDuration() {
        return duration;
    }

    @SerializedName("video_files")
    List<VideoFiles> videoFiles;

    public List<VideoFiles> getVideoFiles() {
        return videoFiles;
    }

    public List<VideoPictures> getVideoPictures() {
        return videoPictures;
    }

    @SerializedName("video_pictures")
    List<VideoPictures> videoPictures;

}