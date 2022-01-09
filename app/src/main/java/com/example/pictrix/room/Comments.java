package com.example.pictrix.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comments")
public class Comments {
    @PrimaryKey(autoGenerate = true)
    int id = 0;

    @ColumnInfo(name="postId")
    int postId;

    @ColumnInfo(name="contentText")
    String contentText;

    public int getId() {
        return id;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
