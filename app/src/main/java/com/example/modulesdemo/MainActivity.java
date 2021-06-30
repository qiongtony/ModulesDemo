package com.example.modulesdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import example.library.router.PathRecordManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取buildConfig类的属性
        String type = BuildConfig.host;
    }

    public void jumpToCommon(View view){
        //            Class<?> clazz = Class.forName("com.example.common.Common_MainActivity");
        Class<?> clazz = PathRecordManager.getClass("common", "Common_MainActivity");
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