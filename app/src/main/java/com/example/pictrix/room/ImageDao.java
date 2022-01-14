package com.example.pictrix.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ImageDao {
    @Query("SELECT * FROM images")
    List<Images> getImages();

    @Transaction
    @Query("SELECT * FROM images")
    List<PostWithComments> getPostsWithComments();

    @Insert
    void insert(Images image);

    @Insert
    void insertAll(List<Images> images);

    @Delete
    void delete(Images image);

    @Query("delete from images")
    void deleteAll();
}
