package com.example.pictrix.retrofit;

import com.google.gson.annotations.SerializedName;

public class VideoPictures {

    @SerializedName("id")
    int id;

    @SerializedName("picture")
    String picture;

    @SerializedName("nr")
    int nr;

    public int getId() {
        return id;
    }
    public String getPicture() {
        return picture;
    }
    public int getNr() {
        return nr;
    }
}