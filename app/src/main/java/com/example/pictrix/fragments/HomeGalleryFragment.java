package com.example.pictrix.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.pictrix.room.AppDatabase;
import com.example.pictrix.room.ImageDao;
import com.example.pictrix.services.InternetService;
import com.example.pictrix.viewModels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeGalleryFragment extends Fragment {
    private HomeGalleryAdapter rcAdapter = new HomeGalleryAdapter();
    private ArrayList<Image> imageList = new ArrayList<>();
    private RecyclerView rcView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Group noPostsGroup;
    private final String IMAGE_SRC = "imageSrc";
    private final String PROFILE_NAME = "profile";
    private final String POST_ID = "postId";
    private final String PROFILE_IMAGE = "https://img.freepik.com/free-photo/this-is-beautiful-landscape-emerald-lake-canada-s-youhe-national-park_361746-26.jpg?size=626&ext=jpg";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_gallery_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setRecyclerView(view);
        noPostsGroup = view.findViewById(R.id.noDataGroup);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show();
        if(getActivity() != null){
            getActivity().findViewById(R.id.back_button).setVisibility(View.GONE);
        }
        AppCompatTextView appBarTitle = getActivity().findViewById(R.id.appBarHeading);
        appBarTitle.setText("Home");
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(()->{
            loadImages();
        });
        loadImages();
    }

    private void setRecyclerView(View view){
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
            public void openBottomSheet(int postId) {
                CommentsBottomSheetFragmentDialog bottomSheet = new CommentsBottomSheetFragmentDialog();
                Bundle args = new Bundle();
                args.putInt(POST_ID,postId);
                bottomSheet.setArguments(args);
                bottomSheet.show(getChildFragmentManager(),null);
            }
        });
        rcAdapter.setProfileClick(new ProfileClick() {
            @Override
            public void onProfileClick(String profileName) {
                getProfileClick(profileName);
            }
        });
    }

    private void getProfileClick(String profileName){
        ProfileFragment secondFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(PROFILE_NAME,profileName);
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
        args.putString(IMAGE_SRC,src);
        secondFragment.setArguments(args);
        MainActivity activity = (MainActivity)getActivity();
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,secondFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    private void saveToDb(ArrayList<Image> images){
        AppDatabase db = AppDatabase.getInstance(getContext());
        ImageDao imageDao = db.getImageDao();
        List<com.example.pictrix.room.Images> entity = new ArrayList<>();
        for(Image image : images){
            com.example.pictrix.room.Images item = new com.example.pictrix.room.Images();
            item.setImageUrl(image.getImageSrc());
            item.setPhotographer(image.getProfileName());
            entity.add(item);
        }
        imageDao.deleteAll();
        imageDao.insertAll(entity);
    }
    private List<com.example.pictrix.room.Images> getImagesFromRoom(){
        AppDatabase db = AppDatabase.getInstance(getContext());
        ImageDao imageDao = db.getImageDao();
        return imageDao.getImages();
    }
    private void loadImages(){
        if(InternetService.isConnectedToInternet(getContext())){
            HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
            homeViewModel.getImages("landscape").observe(getViewLifecycleOwner(), images-> {
                if(images != null) {
                    imageList.addAll(images);
                    rcAdapter.setList(imageList);
                    rcView.setAdapter(rcAdapter);
                    saveToDb(imageList);
                }
            });
            swipeRefreshLayout.setRefreshing(false);
        }else{
            List<com.example.pictrix.room.Images> data = getImagesFromRoom();
            if(data.size() > 0){
                rcView.setVisibility(View.VISIBLE);
                noPostsGroup.setVisibility(View.GONE);
                imageList = new ArrayList<>();
                for(com.example.pictrix.room.Images image : data){
                    imageList.add(new Image(
                            image.getId(),
                            PROFILE_IMAGE,
                            image.getPhotographer(),
                            image.getImageUrl(),
                            image.getLittleImageUrl()
                    ));
                }
                rcAdapter.setList(imageList);
                rcView.setAdapter(rcAdapter);
            }else{
                rcView.setVisibility(View.GONE);
                noPostsGroup.setVisibility(View.VISIBLE);
            }
            swipeRefreshLayout.setRefreshing(false);
        }

    }
}
