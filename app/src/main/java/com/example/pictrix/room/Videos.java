package com.example.pictrix.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "videos")
public class Videos {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "videoUrl")
    String videoUrl;

    @ColumnInfo(name = "videoImage")
    String videoImage;

    @ColumnInfo(name = "photographer")
    String photographer;

    public String getVideoImage() {
        return videoImage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
