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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommentsBottomSheetFragmentDialog extends BottomSheetDialogFragment {

    SharedPreferences sp;
    SharedPreferences.Editor myEdit;
    CommentsAdapter rcAdapter;
    AppCompatButton submit;
    List<String> commentsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.comments_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sp = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        myEdit = sp.edit();
        initRecyclerView(view);
        submit = view.findViewById(R.id.submit_comment);
        loadComments();
        submit.setOnClickListener(v->{
            AppCompatEditText editText = view.findViewById(R.id.comment_input);
            String commentText = editText.getText().toString();
            saveComment(commentText);
            dismiss();
        });
    }
    void loadComments(){
        if(getActivity() != null){
            Gson gson = new Gson();
            String jsonData = sp.getString("comments",null);
            if(jsonData == null){
                List<String> list = new ArrayList<>();
                list.add("Comment 1");
                list.add("Comment 2");
                list.add("Comment 3");
                String json = gson.toJson(list);
                myEdit.putString("comments",json);
                myEdit.commit();
                jsonData = json;
            }
            Type type = new TypeToken<List<String>>(){}.getType();
            commentsList = gson.fromJson(jsonData,type);
            rcAdapter.refreshData(commentsList);
        }
    }
    void saveComment(String comment){
        Gson gson = new Gson();
        commentsList.add(comment);
        String json = gson.toJson(commentsList);
        myEdit.putString("comments",json);
        myEdit.commit();
    }
    void initRecyclerView(View view){
        RecyclerView rcView = view.findViewById(R.id.rcComments);
        rcAdapter = new CommentsAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        rcView.setLayoutManager(llm);
        rcView.setAdapter(rcAdapter);
    }
}
