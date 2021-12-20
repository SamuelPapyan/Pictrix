package com.example.pictrix.retrofit;

import com.google.gson.annotations.SerializedName;

public class VideoFiles {
    @SerializedName("id")
    int id;

    @SerializedName("quality")
    String quality;

    @SerializedName("file_type")
    String filetype;

    @SerializedName("width")
    int width;

    @SerializedName("height")
    int height;

    @SerializedName("link")
    String link;

    public int getId() {
        return id;
    }

    public String getQuality() {
        return quality;
    }

    public String getFiletype() {
        return filetype;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getLink() {
        return link;
    }
}