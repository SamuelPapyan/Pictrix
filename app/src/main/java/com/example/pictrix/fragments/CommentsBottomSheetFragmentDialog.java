package com.example.pictrix.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pictrix.MainActivity;
import com.example.pictrix.R;
import com.example.pictrix.adapters.CommentsAdapter;
import com.example.pictrix.databinding.CommentsLayoutBinding;
import com.example.pictrix.room.AppDatabase;
import com.example.pictrix.room.CommentDao;
import com.example.pictrix.room.Comments;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommentsBottomSheetFragmentDialog extends BottomSheetDialogFragment {

    private CommentsAdapter rcAdapter;
    private final String POST_ID = "postId";
    private List<String> commentsList = new ArrayList<>();

    private CommentsLayoutBinding viewBindnig = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBindnig = CommentsLayoutBinding.inflate(inflater, container,false);
        return viewBindnig.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        Bundle args = getArguments();
        if(args != null){
            int postId = args.getInt(POST_ID,-1);
            loadComments(postId);
            viewBindnig.submitComment.setOnClickListener(v->{
                String commentText = viewBindnig.commentInput.getText().toString();
                saveComment(commentText,postId);
                dismiss();
            });
        }
    }
    void loadComments(int postId){
        if(getActivity() != null){
            AppDatabase db = AppDatabase.getInstance(getContext());
            CommentDao commentDao = db.getCommentDao();
            List<Comments> comments = commentDao.getComments(postId);
            commentsList = new ArrayList<>();
            for(Comments comment : comments){
                commentsList.add(comment.getContentText());
            }
            rcAdapter.refreshData(commentsList);
        }
    }
    void saveComment(String comment, int postId){
        AppDatabase db = AppDatabase.getInstance(getContext());
        CommentDao commentDao = db.getCommentDao();
        Comments newComment = new Comments();
        newComment.setPostId(postId);
        newComment.setContentText(comment);
        commentDao.insertComment(newComment);
        dismiss();
    }
    void initRecyclerView(View view){
        rcAdapter = new CommentsAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        viewBindnig.rcComments.setLayoutManager(llm);
        viewBindnig.rcComments.setAdapter(rcAdapter);
    }
}
