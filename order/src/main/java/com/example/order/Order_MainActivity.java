package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.annotation.ARouter;

import example.library.router.PathRecordManager;

@ARouter(group = "order", path = "order/Order_MainActivity")
public class Order_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getSimpleName() , "WWS onCreate");
        setContentView(R.layout.order_activity_main);
    }

    public void jumpToMain(View view){
        //            Class<?> clazz = Class.forName("com.example.modulesdemo.MainActivity");
        Class<?> clazz = PathRecordManager.getClass("main", "MainActivity");

        openPage(clazz);
    }

    public void jumpToCommon(View view){
        //            Class<?> clazz = Class.forName("com.example.common.Common_MainActivity");
        Class<?> clazz = PathRecordManager.getClass("common", "Common_MainActivity");
        openPage(clazz);
    }

    private void openPage(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}