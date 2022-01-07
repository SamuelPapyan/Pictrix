package com.example.pictrix;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.pictrix.adapters.HomeGalleryAdapter;
import com.example.pictrix.broadcasts.AirPlaneModeReceiver;
import com.example.pictrix.fragments.HomeGalleryFragment;

public class MainActivity extends AppCompatActivity {

    AirPlaneModeReceiver airPlaneModeReceiver;
    AppCompatImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Startup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Fragments
        HomeGalleryFragment fragment1 = new HomeGalleryFragment();
        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(v->{
            getSupportFragmentManager().popBackStackImmediate();
        });

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment1);
        ft.commit();
        airPlaneModeReceiver = new AirPlaneModeReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airPlaneModeReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airPlaneModeReceiver);
    }
}