package com.example.common;

import android.app.Application;
import android.util.Log;

import example.BaseAppInit;

public class Common_Application extends Application implements BaseAppInit {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public boolean onInitHighPriority(Application application) {
        Log.e(getClass().getSimpleName(), "WWS common init");
        return true;
    }

    @Override
    public boolean onInitLowPriority(Application application) {
        return false;
    }
}
