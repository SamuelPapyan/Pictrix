package com.example.pictrix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pictrix.MainActivity;
import com.example.pictrix.R;
import com.example.pictrix.adapters.HomeGalleryAdapter;
import com.example.pictrix.classes.Image;
import com.example.pictrix.interfaces.CommentClick;
import com.example.pictrix.interfaces.ItemClick;
import com.example.pictrix.interfaces.ProfileClick;
import com.example.pictrix.retrofit.Images;
import com.example.pictrix.retrofit.Photo;
import com.example.pictrix.retrofit.RetrofitSetup;
import com.example.pictrix.retrofit.SearchPhotos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeGalleryFragment extends Fragment {
    HomeGalleryAdapter rcAdapter = new HomeGalleryAdapter();
    ArrayList<Image> imageList = new ArrayList<>();
    RecyclerView rcView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_gallery_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rcView = view.findViewById(R.id.mainGalleryRcView);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        rcView.setLayoutManager(llm);
        rcAdapter.setItemClick(new ItemClick() {
            @Override
            public void onImageClick(String src) {
                getImageClick(src);
            }
        });
        rcAdapter.setCommentClick(new CommentClick() {
            @Override
            public void openBottomSheet() {
                CommentsBottomSheetFragmentDialog bottomSheet = new CommentsBottomSheetFragmentDialog();
                bottomSheet.show(getChildFragmentManager(),null);
            }
        });
        rcAdapter.setProfileClick(new ProfileClick() {
            @Override
            public void onProfileClick(String profileName) {
                getProfileClick(profileName);
            }
        });
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
                        String image = photo.getSrc().getMediumUrl();
                        String profileImage = "https://img.freepik.com/free-photo/this-is-beautiful-landscape-emerald-lake-canada-s-youhe-national-park_361746-26.jpg?size=626&ext=jpg";
                        imageList.add(new Image(profileImage,profileName,image));
                    }
                    rcAdapter.setList(imageList);
                    rcView.setAdapter(rcAdapter);
                }
            }
            @Override
            public void onFailure(Call<SearchPhotos> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    private void getProfileClick(String profileName){
        ProfileFragment secondFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("profile",profileName);
        secondFragment.setArguments(args);
        MainActivity activity = (MainActivity)getActivity();
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,secondFragment);
        ft.addToBackStack(null);
        ft.commit();
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

}
