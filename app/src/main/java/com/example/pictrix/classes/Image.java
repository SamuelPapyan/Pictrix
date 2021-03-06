package com.example.pictrix.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Image implements Serializable {

    private int id;
    private String profileImage;
    private String profileName;
    private String imageSrc;
    private String littleImageSrc;
    private boolean isLiked;

    public Image(int id, String profileImage, String profileName, String imageSrc, String littleImageSrc){
        this.id = id;
        this.imageSrc = imageSrc;
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.littleImageSrc = littleImageSrc;
        this.isLiked = false;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getLittleImageSrc() {
        return littleImageSrc;
    }

    public boolean getIsLiked(){
        return isLiked;
    }

    public int getId() {
        return id;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
