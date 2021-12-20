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
import com.example.pictrix.adapters.ProfileVideosAdapter;
import com.example.pictrix.classes.Image;
import com.example.pictrix.classes.VideoImage;
import com.example.pictrix.interfaces.ItemClick;
import com.example.pictrix.retrofit.SearchPhotos;
import com.example.pictrix.retrofit.SearchVideos;
import com.example.pictrix.retrofit.Video;
import com.example.pictrix.retrofit.Videos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileVideosFragment extends Fragment {
    ProfileVideosAdapter rcAdapter = new ProfileVideosAdapter();
    ArrayList<VideoImage> imageList = new ArrayList<>();
    private String profileQualifier;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_gallery_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        Videos videos = Videos.create();
        Call<SearchVideos> landscape = videos.searchVideo("landscape");
        landscape.enqueue(new Callback<SearchVideos>() {
            @Override
            public void onResponse(Call<SearchVideos> call, Response<SearchVideos> response) {
                SearchVideos body = response.body();
                if (body != null) {
                    List<Video> videos = body.getVideos();
                    for (Video video : videos) {
                        String videoImage = video.getImage();
                        String videoUrl = video.getUrl();
                        imageList.add(new VideoImage(videoImage,videoUrl));
                    }
                    rcAdapter.setList(imageList);
                }
            }

            @Override
            public void onFailure(Call<SearchVideos> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    public void setProfileQualifier(String profileQualifier){
        this.profileQualifier = profileQualifier;
    }
    private void getImageClick(String src){
        FullSizeVideoFragment secondFragment = new FullSizeVideoFragment();
        Bundle args = new Bundle();
        args.putString("videoUrl",src);
        secondFragment.setArguments(args);
        MainActivity activity = (MainActivity)getActivity();
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,secondFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
