package com.example.dreawer_scratch;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("tSkIfAcW0WJChUTI3kt3ZjV7hBQtDLyZhDvGSi1i")
                // if defined
                .clientKey("FNvyX5YdcCTXu6Eda4t7e0j3CtzgndkU6bfx8W2p")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}