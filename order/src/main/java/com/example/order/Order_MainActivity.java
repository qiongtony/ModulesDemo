package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Order_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_main);
    }

    public void jumpToMain(View view){
        try {
            Class<?> clazz = Class.forName("com.example.modulesdemo.MainActivity");
            openPage(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void jumpToCommon(View view){
        try {
            Class<?> clazz = Class.forName("com.example.common.Common_MainActivity");
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