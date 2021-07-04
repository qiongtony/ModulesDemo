package com.example.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.annotation.ARouter;

import example.library.router.PathRecordManager;

@ARouter(group = "common", path = "common/Common_MainActivity")
public class Common_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_main);
    }

    public void jumpToMain(View view){
        //            Class<?> clazz = Class.forName("com.example.modulesdemo.MainActivity");
        Class<?> clazz = PathRecordManager.getClass("main", "MainActivity");
        openPage(clazz);
    }

    public void jumpToOrder(View view){
        //            Class<?> clazz = Class.forName("com.example.order.Order_MainActivity");
        Class<?> clazz = PathRecordManager.getClass("order", "Order_MainActivity");
        openPage(clazz);
    }

    private void openPage(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}