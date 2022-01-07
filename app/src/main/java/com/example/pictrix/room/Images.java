package com.example.pictrix.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "images")
public class Images {
    @PrimaryKey(autoGenerate = true)
    int id = 0;

    @ColumnInfo(name = "imageUrl")
    String imageUrl;

    @ColumnInfo(name = "photographer")
    String photographer;

    @ColumnInfo(name = "littleImageUrl")
    String littleImageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhotographer() {
        return photographer;
    }

    public String getLittleImageUrl() {
        return littleImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public void setLittleImageUrl(String littleImageUrl) {
        this.littleImageUrl = littleImageUrl;
    }
}
