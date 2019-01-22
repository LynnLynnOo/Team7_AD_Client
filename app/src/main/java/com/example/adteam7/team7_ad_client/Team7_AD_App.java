package com.example.adteam7.team7_ad_client;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by dodo
 **/
public class Team7_AD_App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        Team7_AD_App.context = getApplicationContext();
    }

    public static Context getContext() {
        return Team7_AD_App.context;
    }

}
