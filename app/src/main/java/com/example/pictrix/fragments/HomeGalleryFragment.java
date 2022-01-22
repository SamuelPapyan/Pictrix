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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pictrix.MainActivity;
import com.example.pictrix.R;
import com.example.pictrix.adapters.HomeGalleryAdapter;
import com.example.pictrix.classes.Image;
import com.example.pictrix.databinding.HomeGalleryLayoutBinding;
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
    private final String POST_ID = "postId";
    private final String PROFILE_IMAGE = "https://img.freepik.com/free-photo/this-is-beautiful-landscape-emerald-lake-canada-s-youhe-national-park_361746-26.jpg?size=626&ext=jpg";

    private HomeGalleryLayoutBinding viewBinding = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = HomeGalleryLayoutBinding.inflate(inflater,container,false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setRecyclerView(view);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show();
        if(getActivity() != null){
            getActivity().findViewById(R.id.back_button).setVisibility(View.GONE);
        }
        AppCompatTextView appBarTitle = getActivity().findViewById(R.id.appBarHeading);
        appBarTitle.setText("Home");
        viewBinding.swipeRefresh.setOnRefreshListener(()->{
            loadImages();
        });
        loadImages();
    }

    private void setRecyclerView(View view){
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        viewBinding.mainGalleryRcView.setLayoutManager(llm);
        rcAdapter.setItemClick(new ItemClick() {
            @Override
            public void onImageClick(View view, String src) {
                getImageClick(view, src);
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
            public void onProfileClick(View view, String profileName) {
                getProfileClick(view, profileName);
            }
        });
    }

    private void getProfileClick(View view, String profileName){
        HomeGalleryFragmentDirections.ActionHomeGalleryFragmentToProfileFragment action =
                HomeGalleryFragmentDirections.actionHomeGalleryFragmentToProfileFragment();
        action.setProfileName(profileName);
        Navigation.findNavController(view).navigate(action);
    }
    private void getImageClick(View view, String src){
        HomeGalleryFragmentDirections.ActionHomeGalleryFragmentToFullImageFragment action =
                HomeGalleryFragmentDirections.actionHomeGalleryFragmentToFullImageFragment();
        action.setImageUrl(src);
        Navigation.findNavController(view).navigate(action);
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
                    viewBinding.mainGalleryRcView.setAdapter(rcAdapter);
                    saveToDb(imageList);
                }
            });
            viewBinding.swipeRefresh.setRefreshing(false);
        }else{
            List<com.example.pictrix.room.Images> data = getImagesFromRoom();
            if(data.size() > 0){
                viewBinding.mainGalleryRcView.setVisibility(View.VISIBLE);
                viewBinding.noDataGroup.setVisibility(View.GONE);
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
                viewBinding.mainGalleryRcView.setAdapter(rcAdapter);
            }else{
                viewBinding.mainGalleryRcView.setVisibility(View.GONE);
                viewBinding.noDataGroup.setVisibility(View.VISIBLE);
            }
            viewBinding.swipeRefresh.setRefreshing(false);
        }

    }
}
