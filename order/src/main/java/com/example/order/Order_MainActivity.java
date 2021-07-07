package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.annotation.ARouter;
import com.example.annotation.Parameter;

import example.library.router.ParameterManager;
import example.library.router.PathRecordManager;

@ARouter(group = "order", path = "order/Order_MainActivity")
public class Order_MainActivity extends AppCompatActivity {

    @Parameter()
    String orderId;

    @Parameter()
    long value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterManager.getInstance().loadParameter(this);
//        new Order_MainActivity$$Parameter().loadParameter(this);

        Log.e(getClass().getSimpleName() , "WWS onCreate orderId = " + orderId + " value = " + value);
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