package com.example.pictrix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pictrix.MainActivity;
import com.example.pictrix.R;
import com.example.pictrix.adapters.HomeGalleryAdapter;
import com.example.pictrix.adapters.ProfileImageAdapter;
import com.example.pictrix.classes.Image;
import com.example.pictrix.interfaces.ItemClick;
import com.example.pictrix.retrofit.Images;
import com.example.pictrix.retrofit.Photo;
import com.example.pictrix.retrofit.RetrofitSetup;
import com.example.pictrix.retrofit.SearchPhotos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileGalleryFragment extends Fragment {
    ProfileImageAdapter rcAdapter = new ProfileImageAdapter();
    ArrayList<Image> imageList = new ArrayList<>();
    private String profileQualifier;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_gallery_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setRecyclerView(view);
        setProfileImages();
    }

    public void setProfileQualifier(String profileQualifier){
        this.profileQualifier = profileQualifier;
    }
    private void setRecyclerView(View view){
        RecyclerView rcView = view.findViewById(R.id.imagesRCView);
        GridLayoutManager glm = new GridLayoutManager(view.getContext(),3,RecyclerView.VERTICAL,false);
        rcView.setLayoutManager(glm);
        rcAdapter.setItemClick(new ItemClick() {
            @Override
            public void onImageClick(String src) {
                getImageClick(src);
            }
        });
        rcView.setAdapter(rcAdapter);
    }
    private void getImageClick(String src){
        FullImageFragment secondFragment = new FullImageFragment();
        Bundle args = new Bundle();
        args.putString("imageSrc",src);
        secondFragment.setArguments(args);
        MainActivity activity = (MainActivity)getActivity();
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,secondFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    private void setProfileImages(){
        HomeGalleryAdapter rcAdapter = new HomeGalleryAdapter();
        RetrofitSetup retrofitSetup = new RetrofitSetup();
        Images images = retrofitSetup.initRetrofit();
        Call<SearchPhotos> landscape = images.searchImage("landscape");
        landscape.enqueue(new Callback<SearchPhotos>() {
            @Override
            public void onResponse(Call<SearchPhotos> call, Response<SearchPhotos> response) {
                SearchPhotos body = response.body();
                if (body != null) {
                    List<Photo> photos = body.getPhotos();
                    for (Photo photo : photos) {
                        String profileName = photo.getPhotographer();
                        String image = photo.getSrc().getOriginal();
                        String profileImage = "https://img.freepik.com/free-photo/this-is-beautiful-landscape-emerald-lake-canada-s-youhe-national-park_361746-26.jpg?size=626&ext=jpg";
                        imageList.add(new Image(profileImage,profileName, image));
                    }
                    rcAdapter.setList(imageList);
                }
            }

            @Override
            public void onFailure(Call<SearchPhotos> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
}
