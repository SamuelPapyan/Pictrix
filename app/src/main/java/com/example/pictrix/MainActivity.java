package com.example.pictrix;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.pictrix.adapters.HomeGalleryAdapter;
import com.example.pictrix.broadcasts.AirPlaneModeReceiver;
import com.example.pictrix.fragments.HomeGalleryFragment;
import com.example.pictrix.fragments.HomeGalleryFragmentDirections;
import com.example.pictrix.fragments.ProfileFragment;
import com.example.pictrix.fragments.ProfileFragmentDirections;

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

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        NavController navController = navHostFragment.getNavController();

        backButton.setOnClickListener(v->{
            //NavDirections action = ProfileFragmentDirections.actionProfileFragmentToHomeGalleryFragment();
            navController.popBackStack();
        });

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