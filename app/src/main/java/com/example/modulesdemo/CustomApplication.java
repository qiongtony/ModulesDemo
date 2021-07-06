package com.example.modulesdemo;

import android.app.Application;
import android.util.Log;

import com.example.common.Common_Application;
import com.example.common.Common_MainActivity;
import com.example.order.Order_MainActivity;

import java.util.ArrayList;
import java.util.List;

import example.BaseAppInit;
import example.library.router.PathRecordManager;

public class CustomApplication extends Application {
    private List<Class<? extends BaseAppInit>> mAppInitClazzList = new ArrayList<>();
    private List<BaseAppInit> mAppInitList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initClazzList();

        try {
            init();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.e(getClass().getSimpleName(), "WWS app init");
        PathRecordManager.joinGroup("main", "MainActivity", MainActivity.class);
        PathRecordManager.joinGroup("order", "Order_MainActivity", Order_MainActivity.class);
        PathRecordManager.joinGroup("common", "Common_MainActivity", Common_MainActivity.class);
    }

    private void init() throws InstantiationException, IllegalAccessException {
        for (Class<? extends BaseAppInit> clazz : mAppInitClazzList){
            BaseAppInit appInit = clazz.newInstance();
            mAppInitList.add(appInit);
        }
        for (BaseAppInit appInit : mAppInitList){
            appInit.onInitHighPriority(this);
        }
        for (BaseAppInit appInit : mAppInitList){
            appInit.onInitLowPriority(this);
        }
    }

    private void initClazzList(){
        mAppInitClazzList.add(Common_Application.class);
        mAppInitClazzList.add(com.example.order.CustomApplication.class);

    }
}
