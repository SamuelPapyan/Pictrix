package com.example.pictrix.retrofit;

import com.google.gson.annotations.SerializedName;

public class Src {
    @SerializedName("original")
    String original;

    @SerializedName("medium")
    String mediumUrl;

    @SerializedName("small")
    String smallUrl;

    @SerializedName("large")
    String largeUrl;

    @SerializedName("portrait")
    String portraitUrl;

    @SerializedName("landscape")
    String landscapeUrl;

    public String getOriginal() {
        return original;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public String getLandscapeUrl() {
        return landscapeUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }
}
