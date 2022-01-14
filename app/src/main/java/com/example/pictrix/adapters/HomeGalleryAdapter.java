package com.example.pictrix.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pictrix.R;
import com.example.pictrix.classes.Image;
import com.example.pictrix.interfaces.CommentClick;
import com.example.pictrix.interfaces.ItemClick;
import com.example.pictrix.interfaces.ProfileClick;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeGalleryAdapter extends RecyclerView.Adapter<HomeGalleryAdapter.GalleryItemViewHolder>{
    private final List<Image> list = new ArrayList<>();
    private ItemClick itemClick = null;
    private ProfileClick profileClick = null;
    private CommentClick commentClick = null;
    @NonNull
    @Override
    public GalleryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_gallery_item,parent,false);
        return new GalleryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryItemViewHolder holder, int position) {
        holder.initData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GalleryItemViewHolder extends RecyclerView.ViewHolder {
        public GalleryItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        void initData(Image image){
            AppCompatImageView profilePhoto = itemView.findViewById(R.id.gallery2ProfileImage);
            AppCompatTextView profileName = itemView.findViewById(R.id.gallery2ProfileName);
            AppCompatImageView imageView = itemView.findViewById(R.id.gallery2Image);
            AppCompatImageView like = itemView.findViewById(R.id.imageLike);
            AppCompatImageView shareButton = itemView.findViewById(R.id.shareButton);
            AppCompatImageView commentButton = itemView.findViewById(R.id.imageComment);

            Glide.with(itemView.getContext()).load(image.getProfileImage()).centerCrop().circleCrop().into(profilePhoto);
            Glide.with(itemView.getContext()).load(image.getImageSrc()).centerCrop().into(imageView);
            profileName.setText(image.getProfileName());
            like.setOnClickListener(v->{
                image.setLiked(!image.getIsLiked());
                if(image.getIsLiked())
                    like.setImageResource(R.drawable.like_on);
                else
                    like.setImageResource(R.drawable.like_off);
                notifyDataSetChanged();
            });
            imageView.setOnClickListener(v->{
                itemClick.onImageClick(v, image.getImageSrc());
            });
            profileName.setOnClickListener(v->{
                profileClick.onProfileClick(v, profileName.getText().toString());
            });
            shareButton.setOnClickListener(v->{
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String imageUrl = String.valueOf(image.getImageSrc());
                intent.putExtra(Intent.EXTRA_TEXT, imageUrl);
                Intent modeIntent = Intent.createChooser(intent, "Share with...");
                itemView.getContext().startActivity(modeIntent);
            });
            commentButton.setOnClickListener(v->{
                commentClick.openBottomSheet(image.getId());
            });
        }
    }
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }
    public void setProfileClick(ProfileClick profileClick){
        this.profileClick = profileClick;
    }

    public void setCommentClick(CommentClick commentClick) {
        this.commentClick = commentClick;
    }

    public void setList(ArrayList<Image> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
