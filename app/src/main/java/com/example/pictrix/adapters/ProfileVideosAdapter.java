package com.example.pictrix.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pictrix.R;
import com.example.pictrix.classes.Image;
import com.example.pictrix.classes.VideoImage;
import com.example.pictrix.interfaces.ItemClick;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileVideosAdapter extends RecyclerView.Adapter<ProfileVideosAdapter.VideoViewHolder>{
    private final List<VideoImage> list = new ArrayList<>();
    private ItemClick itemClick = null;
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_videos_item,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.initDate(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        void initDate(VideoImage video){
            AppCompatImageView imageView = itemView.findViewById(R.id.some_video);
            Glide.with(itemView.getContext())
                    .load(video.getVideoImageUrl())
                    .into(imageView);
            imageView.setOnClickListener(v->{
                itemClick.onImageClick(video.getVideoUrl());
            });
        }
    }
    public void setList(ArrayList<VideoImage> arrayList){
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }
}
