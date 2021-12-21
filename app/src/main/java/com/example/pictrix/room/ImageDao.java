package com.example.pictrix.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ImageDao {
    @Query("select * from images")
    List<Images> getImage();

    @Insert
    void insert(Images image);

    @Delete
    void delete(Images image);
}
