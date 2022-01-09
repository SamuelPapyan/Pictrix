package com.example.pictrix.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pictrix.classes.Image;
import com.example.pictrix.repositories.ImageRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final ImageRepository imageRepository = new ImageRepository();



    public LiveData<List<Image>> getImages(String param){
        LiveData<List<Image>> images = imageRepository.getImages(param);
        return images;
    }
}
