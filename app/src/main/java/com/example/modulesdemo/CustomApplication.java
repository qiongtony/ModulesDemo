package com.example.modulesdemo;

import android.app.Application;

import com.example.common.Common_MainActivity;
import com.example.order.Order_MainActivity;

import example.library.router.PathRecordManager;

public class CustomApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        PathRecordManager.joinGroup("main", "MainActivity", MainActivity.class);
        PathRecordManager.joinGroup("order", "Order_MainActivity", Order_MainActivity.class);
        PathRecordManager.joinGroup("common", "Common_MainActivity", Common_MainActivity.class);
    }
}
