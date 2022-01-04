package com.example.pictrix.services;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetService {
    public static boolean isConnectedToInternet(Context context){
        if(context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        }
        else
            return false;
    }
}
