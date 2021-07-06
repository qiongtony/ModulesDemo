package com.example.order;

import android.app.Application;
import android.util.Log;

import example.BaseAppInit;

public class CustomApplication extends Application implements BaseAppInit {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public boolean onInitHighPriority(Application application) {
        Log.e(getClass().getSimpleName(), "WWS order init");
        return true;
    }

    @Override
    public boolean onInitLowPriority(Application application) {
        return false;
    }
}
