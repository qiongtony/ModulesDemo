package com.example.modulesdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取buildConfig类的属性
        String type = BuildConfig.host;
    }

    public void jumpToCommon(View view){
        try {
            Class<?> clazz = Class.forName("com.example.common.Common_MainActivity");
            openPage(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void jumpToOrder(View view){
        try {
            Class<?> clazz = Class.forName("com.example.order.Order_MainActivity");
            openPage(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openPage(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}