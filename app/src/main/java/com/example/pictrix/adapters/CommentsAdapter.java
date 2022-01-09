package com.example.pictrix.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pictrix.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder>{

    private static final List<String> items = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.initData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void refreshData(List<String> commentsList){
        items.clear();
        items.addAll(commentsList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void initData(String text){
            AppCompatTextView commentText = itemView.findViewById(R.id.comment_text);
            commentText.setText(text);
        }
    }
}
