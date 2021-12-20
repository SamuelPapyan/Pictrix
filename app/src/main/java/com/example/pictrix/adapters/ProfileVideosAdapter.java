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
    List<VideoImage> list = Collections.emptyList();
    String profileQualifier;
    ItemClick itemClick = null;
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_gallery_item,parent,false);
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
            AppCompatImageView imageView = itemView.findViewById(R.id.some_image);
            Glide.with(itemView.getContext())
                    .load(video.getVideoImageUrl())
                    .into(imageView);
            imageView.setOnClickListener(v->{
                itemClick.onImageClick(video.getVideoUrl());
            });
        }
    }
    public void setList(ArrayList<VideoImage> arrayList){
        this.list = arrayList;
        notifyDataSetChanged();
    }
    public void setProfileQualifier(String profileQualifier){
        this.profileQualifier = profileQualifier;
    }
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }
}
