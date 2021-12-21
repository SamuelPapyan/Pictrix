package com.example.pictrix.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VideoDao {
    @Query("select * from videos")
    List<Videos> getVideos();
    @Insert
    void insert(Videos video);
    @Delete
    void delete(Videos video);
}
