package com.example.pictrix.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "images")
public class Images {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "imageUrl")
    String imageUrl;

    @ColumnInfo(name = "photographer")
    String photographer;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }
}
