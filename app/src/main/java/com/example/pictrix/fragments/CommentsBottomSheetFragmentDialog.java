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

import java.util.HashSet;
import java.util.Set;

public class CommentsBottomSheetFragmentDialog extends BottomSheetDialogFragment {

    SharedPreferences sp = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
    SharedPreferences.Editor myEdit = sp.edit();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.comments_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rcView = view.findViewById(R.id.rcComments);
        AppCompatEditText editText = view.findViewById(R.id.comment_input);
        CommentsAdapter rcAdapter = new CommentsAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        rcView.setLayoutManager(llm);
        rcView.setAdapter(rcAdapter);
        AppCompatButton submit = view.findViewById(R.id.submit_comment);
        submit.setOnClickListener(v->{
            dismiss();
            String commentText = editText.getText().toString();
            Set<String> commentsSet = sp.getStringSet("comments",null);
            if(commentsSet == null)
                commentsSet = new HashSet<>();
            commentsSet.add(commentText);
            myEdit.putStringSet("comments",commentsSet);
            myEdit.commit();
            rcAdapter.refreshData(commentsSet);
        });
    }
}
