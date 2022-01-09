package com.example.pictrix.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("select * from comments where postId = :postId")
    List<Comments> getComments(int postId);

    @Insert
    void insertComment(Comments comment);
}
