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
import com.example.pictrix.interfaces.ItemClick;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileImageAdapter extends RecyclerView.Adapter<ProfileImageAdapter.ImageViewHolder>{
    private final List<Image> list = new ArrayList<>();
    private ItemClick itemClick = null;
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_gallery_item,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.initDate(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        void initDate(Image image){
            AppCompatImageView imageView = itemView.findViewById(R.id.some_image);

            Glide.with(itemView.getContext())
                    .load(image.getLittleImageSrc())
                    .into(imageView);
            imageView.setOnClickListener(v->{
                itemClick.onImageClick(image.getImageSrc());
            });
        }
    }
    public void setList(ArrayList<Image> arrayList){
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }
}
