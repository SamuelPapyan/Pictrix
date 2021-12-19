package com.example.pictrix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pictrix.R;
import com.example.pictrix.adapters.CommentsAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CommentsBottomSheetFragmentDialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.comments_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rcView = view.findViewById(R.id.rcComments);
        CommentsAdapter rcAdapter = new CommentsAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        rcView.setLayoutManager(llm);
        rcView.setAdapter(rcAdapter);
        AppCompatButton submit = view.findViewById(R.id.submit_comment);
        submit.setOnClickListener(v->{
            dismiss();
        });
    }
}
