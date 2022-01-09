package com.example.pictrix.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pictrix.classes.Image;
import com.example.pictrix.retrofit.Images;
import com.example.pictrix.retrofit.Photo;
import com.example.pictrix.retrofit.SearchPhotos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {

    public LiveData<List<Image>> getImages(String param){
        final String PROFILE_IMAGE = "https://img.freepik.com/free-photo/this-is-beautiful-landscape-emerald-lake-canada-s-youhe-national-park_361746-26.jpg?size=626&ext=jpg";
        Images images = Images.create();

        MutableLiveData<List<Image>> mutableLiveData = new MutableLiveData<>();

        images.searchImage(param).enqueue(new Callback<SearchPhotos>() {
            @Override
            public void onResponse(Call<SearchPhotos> call, Response<SearchPhotos> response) {
                SearchPhotos body = response.body();
                List<Image> imageList = new ArrayList<>();
                if (body != null) {
                    List<Photo> photos = body.getPhotos();
                    for (Photo photo : photos) {
                        int id = photo.getId();
                        String profileName = photo.getPhotographer();
                        String image = photo.getSrc().getMediumUrl();
                        String littleImageUrl = photo.getSrc().getSmallUrl();
                        imageList.add(new Image(
                                id,
                                PROFILE_IMAGE,
                                profileName,
                                image,
                                littleImageUrl)
                        );
                    }
                    mutableLiveData.setValue(imageList);
                }
            }
            @Override
            public void onFailure(Call<SearchPhotos> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }
}
