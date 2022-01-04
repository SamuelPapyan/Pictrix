package com.example.pictrix.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.pictrix.room.AppDatabase;
import com.example.pictrix.room.ImageDao;
import com.example.pictrix.services.InternetService;

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
    RecyclerView rcView;
    Group noPostGroup;
    SwipeRefreshLayout swipeRefreshLayout;
    private final String profileImage = "https://img.freepik.com/free-photo/this-is-beautiful-landscape-emerald-lake-canada-s-youhe-national-park_361746-26.jpg?size=626&ext=jpg";

    private String profileQualifier;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_gallery_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noPostGroup = view.findViewById(R.id.noDataGroup);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        initRecyclerView(view);
        swipeRefreshLayout.setOnRefreshListener(()->{
            loadImages();
        });
        loadImages();
    }
    public void setProfileQualifier(String profileQualifier){
        this.profileQualifier = profileQualifier;
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
    private void initRecyclerView(View view){
        rcView = view.findViewById(R.id.imagesRCView);
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
    private void loadImages(){
        if(InternetService.isConnectedToInternet(getContext())) {
            Images images = Images.create();
            Call<SearchPhotos> landscape = images.searchImage("landscape");
            landscape.enqueue(new Callback<SearchPhotos>() {
                @Override
                public void onResponse(Call<SearchPhotos> call, Response<SearchPhotos> response) {
                    SearchPhotos body = response.body();
                    if (body != null) {
                        List<Photo> photos = body.getPhotos();
                        for (Photo photo : photos) {
                            String profileName = photo.getPhotographer();
                            String image = photo.getSrc().getSmallUrl();
                            imageList.add(new Image(profileImage, profileName, image));
                        }
                        rcAdapter.setList(imageList);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                @Override
                public void onFailure(Call<SearchPhotos> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        else{
            List<com.example.pictrix.room.Images> data = getImagesFromRoom();
            if(data.size() > 0){
                rcView.setVisibility(View.VISIBLE);
                noPostGroup.setVisibility(View.GONE);
                imageList = new ArrayList<>();
                for(com.example.pictrix.room.Images image : data){
                    imageList.add(new Image(profileImage,image.getPhotographer(),image.getImageUrl()));
                }
                rcAdapter.setList(imageList);
                rcView.setAdapter(rcAdapter);
            }
            else {
                rcView.setVisibility(View.GONE);
                noPostGroup.setVisibility(View.VISIBLE);
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    private List<com.example.pictrix.room.Images> getImagesFromRoom(){
        AppDatabase db = AppDatabase.getInstance(getContext());
        ImageDao imageDao = db.getImageDao();
        return imageDao.getImages();
    }
}
