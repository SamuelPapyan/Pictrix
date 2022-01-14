package com.example.pictrix.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PostWithComments {
    @Embedded
    public Images images;
    @Relation(
            parentColumn = "id",
            entityColumn = "postId"
    )
    public List<Comments> commentsList;
}
