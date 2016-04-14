package com.mac.chris.todoapp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by chris on 4/14/16.
 */
public class TestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
